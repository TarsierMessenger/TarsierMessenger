package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ch.tarsier.tarsier.domain.model.ChatSummary;
import ch.tarsier.tarsier.ui.adapter.ChatListAdapter;
import ch.tarsier.tarsier.R;

/**
 * @author gluthier
 */
public class ChatListActivity extends Activity {
    private final static String ID_CHAT_MESSAGE = "ch.tarsier.tarsier.ui.activity.ID_CHAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);


        final ListView discussionsList = (ListView) findViewById(R.id.chat_list);
        ChatListAdapter adapter = new ChatListAdapter(this, R.layout.row_chat_list, discussionsArray);

        discussionsList.setAdapter(adapter);

        discussionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChatSummary discussion = (ChatSummary) discussionsList.getItemAtPosition(i);
                // TODO check if getApplicationContext() is right
                Intent chatIdIntent = new Intent(getApplicationContext(), ChatActivity.class);
                // FIXME discussion.getId() is just a filler for now, you can expect to get the id of the Chat clicked
                chatIdIntent.putExtra(ID_CHAT_MESSAGE, discussion.getId());
                startActivity(chatIdIntent);
            }
        });

        // FIXME: Handle potential NullPointerException
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
}