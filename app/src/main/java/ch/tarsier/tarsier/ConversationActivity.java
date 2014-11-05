package ch.tarsier.tarsier;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import ch.tarsier.tarsier.storage.Message;

/**
 * @author marinnicolini and xawill (extreme programming)
 *
 * This activity is responsible to display the messages of the current discussion.
 * This discussion can either be a private one, or a chat room (multiple participants).
 *
 * Bubble's layout is inspired from https://github.com/AdilSoomro/Android-Speech-Bubble
 */
public class ConversationActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static Point windowSize;

    private String mDiscussionId;
    private boolean mIsPrivate;
    private ArrayList<Message> mMessages;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Display display = getWindowManager().getDefaultDisplay();
        this.windowSize = new Point();
        display.getSize(this.windowSize);
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

    /**
     *
     * @param name can be null if this is a private discussion or if the message is sent by the user
     * @param message
     * @param date
     * @param sentByUser a boolean to determine if the bubble has to be drawn on the left
     *                   or on the right of the screen (depending on the user sending the message).
     */
    private void drawBubble(String name, String message, String date, boolean sentByUser) {

    }

    // Called when a new Loader needs to be created
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(this, ContactsContract.Data.CONTENT_URI,
                PROJECTION, SELECTION, null, null);
    }

    // Called when a previously created loader has finished loading
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);
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
