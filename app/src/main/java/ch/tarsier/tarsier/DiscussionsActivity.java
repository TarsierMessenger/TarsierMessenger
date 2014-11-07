package ch.tarsier.tarsier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by gluthier
 */
public class DiscussionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussions);

        DiscussionSummary[] discussionsArray = {
            new DiscussionSummary("placeholder", "1", "SwEng", "A fond, mais je bosse dur, aussi!",
                    "Just now", "37", DiscussionSummary.TypeConversation.PRIVATE_CHAT),
            new DiscussionSummary("placeholder", "3", "Romain Ruetschi", "Typing...",
                    "13:10", "1",  DiscussionSummary.TypeConversation.PUBLIC_ROOM),
            new DiscussionSummary("placeholder", "0", "Yann Mahmoudi", "That's because C just has no class!",
                    "Yesterday", "1", DiscussionSummary.TypeConversation.PUBLIC_ROOM),
            new DiscussionSummary("placeholder", "0", "Marin-Jerry Nicolini", "Ouais, pas de problème pour vendredi.",
                    "Sunday", "1", DiscussionSummary.TypeConversation.PUBLIC_ROOM),
            new DiscussionSummary("placeholder", "0", "Hong Kong's umbrella movement", "Everybody to Civic Square! Take umb...",
                    "Friday", "1254", DiscussionSummary.TypeConversation.PRIVATE_CHAT),
            new DiscussionSummary("placeholder", "0", "Benjamin Paccaud", "Oui, tous les tests passent sans problème.",
                    "Friday", "1", DiscussionSummary.TypeConversation.PUBLIC_ROOM),
            new DiscussionSummary("placeholder", "0", "TA meeting 1", "Non, Romain n'a toujours pas fiat le git work...",
                    "Wednesday", "8", DiscussionSummary.TypeConversation.PRIVATE_CHAT)
        };

        final ListView discussionsList = (ListView) findViewById(R.id.list_discussions);
        DiscussionsAdapter adapter = new DiscussionsAdapter(this, R.layout.row_discussion, discussionsArray);

        discussionsList.setAdapter(adapter);

        discussionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DiscussionSummary discussion = (DiscussionSummary) discussionsList.getItemAtPosition(i);
                // TODO check
                Intent discussionIdIntent = new Intent(getApplicationContext(), ChatRoom.class);
                // TODO good values
                int value = 1;
                discussionIdIntent.putExtra("id", value);
                String text = discussion.getName() + ", TODO: send intent";

                Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
            }
        });

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
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettings() {
        // TODO
    }
}