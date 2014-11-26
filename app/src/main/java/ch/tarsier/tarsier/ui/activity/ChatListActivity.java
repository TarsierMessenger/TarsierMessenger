package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.database.FillDatabaseWithFictionalData;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.ui.adapter.ChatListAdapter;
import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.ui.view.EndlessChatListView;
import ch.tarsier.tarsier.ui.view.EndlessListener;

/**
 * @author gluthier
 */
public class ChatListActivity extends Activity implements EndlessListener {

    private final static String CHAT_MESSAGE = "ch.tarsier.tarsier.ui.activity.CHAT";

    private EndlessChatListView mEndlessChatListView;
    private ChatListAdapter mChatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        Tarsier.app().reset();
        FillDatabaseWithFictionalData.populate();

        mEndlessChatListView = (EndlessChatListView) findViewById(R.id.chat_list);
        mChatListAdapter = new ChatListAdapter(this, R.layout.row_chat_list);

        mEndlessChatListView.setLoadingView(R.layout.loading_layout);
        mEndlessChatListView.setChatListAdapter(mChatListAdapter);
        mEndlessChatListView.setEndlessListener(this);

        this.loadData();

        mEndlessChatListView.setOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent chatIdIntent = new Intent(getApplicationContext(), ChatActivity.class);
                chatIdIntent.putExtra(CHAT_MESSAGE, mChatListAdapter.getItemId(i));

                startActivity(chatIdIntent);
            }
        });

        // FIXME: Handle potential NullPointerException
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_new_chat:
                createNewChatroom();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNewChatroom() {
        Intent newChatroomIntent = new Intent(this, NewChatRoomActivity.class);
        startActivity(newChatroomIntent);
    }

    private void openSettings() {
        Intent openSettingsIntent = new Intent(this, PreferencesActivity.class);
        startActivity(openSettingsIntent);
    }

    @Override
    public void loadData() {
        ChatLoader chatLoader = new ChatLoader();
        chatLoader.execute();
    }

    private class ChatLoader extends AsyncTask<Void, Void, List<Chat>> {

        @Override
        protected List<Chat> doInBackground(Void... voids) {
            ChatRepository chatRepository = Tarsier.app().getChatRepository();

            List<Chat> chatList = null;
            try {
                chatList = chatRepository.fetchAllChats();
            } catch (InvalidCursorException e) {
                e.printStackTrace();
            }

            return chatList;
        }

        @Override
        protected void onPostExecute(List<Chat> chatList) {
            if (chatList == null) {
                throw new IllegalArgumentException("ChatList is null.");
            }
            super.onPostExecute(chatList);
            mEndlessChatListView.addNewData(chatList);
        }
    }
}