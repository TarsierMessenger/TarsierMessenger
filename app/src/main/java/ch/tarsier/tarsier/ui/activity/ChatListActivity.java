package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ch.tarsier.tarsier.domain.model.DiscussionSummary;
import ch.tarsier.tarsier.ui.adapter.DiscussionsAdapter;
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

        DiscussionSummary[] discussionsArray = {
                new DiscussionSummary(7, "placeholder", "1", "SwEng", "A fond, mais je bosse dur, aussi!",
                        "Just now", "37", DiscussionSummary.TypeConversation.PRIVATE_CHAT),
                new DiscussionSummary(6, "placeholder", "3", "Romain Ruetschi", "Typing...",
                        "13:10", "1",  DiscussionSummary.TypeConversation.PUBLIC_ROOM),
                new DiscussionSummary(5, "placeholder", "0", "Yann Mahmoudi", "That's because C just has no class!",
                        "Yesterday", "1", DiscussionSummary.TypeConversation.PUBLIC_ROOM),
                new DiscussionSummary(4, "placeholder", "0", "Marin-Jerry Nicolini", "Ouais, pas de problème pour vendredi.",
                        "Sunday", "1", DiscussionSummary.TypeConversation.PUBLIC_ROOM),
                new DiscussionSummary(3, "placeholder", "0", "Hong Kong's umbrella movement", "Everybody to Civic Square! Ta...",
                        "Friday", "1254", DiscussionSummary.TypeConversation.PRIVATE_CHAT),
                new DiscussionSummary(2 ,"placeholder", "9", "Benjamin Paccaud", "Oui, tous les tests passent sans problème.",
                        "Friday", "1", DiscussionSummary.TypeConversation.PUBLIC_ROOM),
                new DiscussionSummary(1 ,"placeholder", "10", "TA meeting 1", "Non, Romain n'a toujours pas fait...",
                        "Wednesday", "8", DiscussionSummary.TypeConversation.PRIVATE_CHAT)
        };

        final ListView discussionsList = (ListView) findViewById(R.id.chat_list);
        DiscussionsAdapter adapter = new DiscussionsAdapter(this, R.layout.row_chat_list, discussionsArray);

        discussionsList.setAdapter(adapter);

        discussionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DiscussionSummary discussion = (DiscussionSummary) discussionsList.getItemAtPosition(i);
                // TODO: check if getApplicationContext() is right
                Intent chatIdIntent = new Intent(getApplicationContext(), ConversationActivity.class);
                // discussion.getId() is just a filler for now, you can expect to get the id of the Chat clicked
                chatIdIntent.putExtra(ID_CHAT_MESSAGE, discussion.getId());
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
                openNewChatroom();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openNewChatroom() {
        Intent newChatroomIntent = new Intent(this, NewChatroomActivity.class);
        startActivity(newChatroomIntent);
    }

    private void openSettings() {
        Intent openSettingsIntent = new Intent(this, PreferencesActivity.class);
        startActivity(openSettingsIntent);
    }
}