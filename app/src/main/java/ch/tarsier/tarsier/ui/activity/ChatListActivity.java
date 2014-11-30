package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.database.FillDatabaseWithFictionalData;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.ui.adapter.ChatListAdapter;
import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.ui.view.ChatListView;
import ch.tarsier.tarsier.ui.view.EndlessListener;

/**
 * @author gluthier
 */
public class ChatListActivity extends Activity implements EndlessListener {

    private final static String CHAT_MESSAGE = "ch.tarsier.tarsier.ui.activity.CHAT";

    private ChatListView mChatListView;
    private ChatListAdapter mChatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        mChatListView = (ChatListView) findViewById(R.id.chat_list);
        mChatListAdapter = new ChatListAdapter(this, R.layout.row_chat_list);

        mChatListView.setLoadingView(R.layout.loading_layout);
        mChatListView.setChatListAdapter(mChatListAdapter);
        mChatListView.setEndlessListener(this);

        this.loadData();

        mChatListView.setChatListAdapter(mChatListAdapter);

        mChatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent chatIdIntent = new Intent(getApplicationContext(), ChatActivity.class);
                chatIdIntent.putExtra(CHAT_MESSAGE, mChatListAdapter.getItemId(position));

                Toast.makeText(getBaseContext(), "Chat id: " + mChatListAdapter.getItemId(position), Toast.LENGTH_SHORT).show();
                //startActivity(chatIdIntent);
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
    public void loadData() {
        ChatLoader chatLoader = new ChatLoader();
        chatLoader.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button_chatlist:
                search();
                return true;
            case R.id.create_private_chat:
                createNewPrivateChat();
                return true;
            case R.id.create_new_chatroom:
                createNewChatroom();
                return true;
            case R.id.goto_profile_activity:
                openProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void search() {

    }

    private void createNewPrivateChat() {
        //TODO uncomment once NearbyListActivity is implemented
        //Intent newPrivateChatIntent = new Intent(this, NearbyListActivity.class);
        //startActivity(newPrivateChatIntent);
    }

    private void createNewChatroom() {
        Intent newChatroomIntent = new Intent(this, NewChatRoomActivity.class);
        startActivity(newChatroomIntent);
    }

    private void openProfile() {
        Intent openProfileIntent = new Intent(this, ProfileActivity.class);
        startActivity(openProfileIntent);
    }

    private class ChatLoader extends AsyncTask<Void, Void, List<Chat>> {

        @Override
        protected List<Chat> doInBackground(Void... voids) {
            //TODO delete for develop
            Tarsier.app().reset();

            while (!Tarsier.app().getDatabase().isReady()) { }

            //TODO delete for develop
            FillDatabaseWithFictionalData.populate();

            ChatRepository chatRepository = Tarsier.app().getChatRepository();

            List<Chat> chatList = null;
            try {
                chatList = chatRepository.fetchAllChatsDescending();
            } catch (InvalidCursorException e) {
                e.printStackTrace();
            }

            return chatList;
        }

        @Override
        protected void onPostExecute(List<Chat> chatList) {
            super.onPostExecute(chatList);

            if (chatList != null) {
                mChatListView.addNewData(chatList);
            }
        }
    }
}