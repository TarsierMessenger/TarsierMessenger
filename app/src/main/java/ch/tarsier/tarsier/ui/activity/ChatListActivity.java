package ch.tarsier.tarsier.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.ui.adapter.ChatListAdapter;
import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.ui.view.ChatListView;
import ch.tarsier.tarsier.ui.view.EndlessListener;
import ch.tarsier.tarsier.util.ChatLastMessageDateSorter;

/**
 * @author gluthier
 */
public class ChatListActivity extends Activity implements EndlessListener {

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

        this.loadData();

        mChatListView.setChatListAdapter(mChatListAdapter);

        mChatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Chat chat = mChatListAdapter.getItem(position);
                displayChatActivity(chat);
            }
        });

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    private void displayChatActivity(Chat chat) {
        Intent chatIntent = new Intent(getApplicationContext(), ChatActivity.class);
        chatIntent.putExtra(ChatActivity.EXTRA_CHAT_MESSAGE_KEY, chat);
        startActivity(chatIntent);
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
            case R.id.create_chat_from_chat_list_activity:
                createNewChat();
                return true;
            case R.id.goto_profile_activity:
                openProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNewChat() {
        Intent newPrivateChatIntent = new Intent(this, NearbyListActivity.class);
        startActivity(newPrivateChatIntent);
    }

    private void openProfile() {
        Intent openProfileIntent = new Intent(this, ProfileActivity.class);
        startActivity(openProfileIntent);
    }


    private class ChatLoader extends AsyncTask<Void, Void, List<Chat>> {

        @Override
        protected List<Chat> doInBackground(Void... voids) {
            while (!Tarsier.app().getDatabase().isReady()) { }

            ChatRepository chatRepository = Tarsier.app().getChatRepository();

            try {
                List<Chat> sortedChats = chatRepository.findAll();
                Collections.sort(sortedChats, new ChatLastMessageDateSorter());

                //return the sorted list of all chats
                return sortedChats;

            } catch (NoSuchModelException e) {
                e.printStackTrace();

                //return an empty list if the database is empty
                return new ArrayList<>();
            }
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