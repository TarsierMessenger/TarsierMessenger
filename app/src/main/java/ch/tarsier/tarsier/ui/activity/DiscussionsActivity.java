package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ch.tarsier.tarsier.storage.DiscussionSummary;
import ch.tarsier.tarsier.ui.adapter.DiscussionsAdapter;
import ch.tarsier.tarsier.R;

/**
 * Created by gluthier
 */
public class DiscussionsActivity extends Activity {
    public final static String ID_DISCUSSION_MESSAGE = "ch.tarsier.tarsier.ID_DISCUSSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussions);

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

        final ListView discussionsList = (ListView) findViewById(R.id.list_discussions);
        DiscussionsAdapter adapter = new DiscussionsAdapter(this, R.layout.row_discussion, discussionsArray);

        discussionsList.setAdapter(adapter);

        discussionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DiscussionSummary discussion = (DiscussionSummary) discussionsList.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(), Integer.toString(discussion.getId()), Toast.LENGTH_SHORT).show();
                // TODO: check if getApplicationContext() is right
                /*Intent discussionIdIntent = new Intent(getApplicationContext(), ???.class);
                discussionIdIntent.putExtra(ID_DISCUSSION_MESSAGE, discussion.getId());

                startActivity(discussionIdIntent);*/
            }
        });

        // FIXME: Handle potential NullPointerException
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.discussions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.create_new_discussion:
                openNewDiscussion();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openNewDiscussion() {
        //TODO
        Toast.makeText(this, "create new discussion", Toast.LENGTH_SHORT).show();
    }
    private void openSettings() {
        //TODO
        Toast.makeText(this, "open settings", Toast.LENGTH_SHORT).show();
    }
}