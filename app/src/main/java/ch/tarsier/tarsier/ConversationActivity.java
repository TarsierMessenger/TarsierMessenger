package ch.tarsier.tarsier;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * @author marinnicolini and xawill (extrem programming)
 *
 * This activity is responsible to display the messages of the current discussion.
 * This discussion can either be a private one, or a chat room (multiple participants).
 *
 * The code for the display of the bubbles in a ListView is mainly inspired from this repository :
 * https://github.com/AdilSoomro/Android-Speech-Bubble.
 */
public class ConversationActivity extends Activity {
    private String mDiscussionId;
    private boolean mIsPrivate;
    private ArrayList<Message> mMessages;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_discussion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.private_discussion, menu);
        return super.onCreateOptionsMenu(menu);

        Intent startingIntent = getIntent();

        this.mDiscussionId = startingIntent.getStringExtra(DiscussionsActivity.class.getID());
        this.mMessages = StorageManager.getMessages(this.mDiscussionId);
        this.mTitle = StorageManager.getChat(this.mDiscussionId).getTitle();
        this.mIsPrivate = StorageManager.getChat(this.mDiscussionId).isPrivate();

        displayMessages();
    }

    private void displayMessages() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
