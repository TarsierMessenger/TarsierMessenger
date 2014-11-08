package ch.tarsier.tarsier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.storage.Message;

/**
 * @author marinnicolini and xawill (extreme programming)
 *
 * This activity is responsible to display the messages of the current discussion.
 * This discussion can either be a private one, or a chat room (multiple participants).
 *
 * Bubble's layout is inspired from https://github.com/AdilSoomro/Android-Speech-Bubble
 */
public class ConversationActivity extends Activity implements EndlessListener {
    private static final int NUMBER_OF_MESSAGES_TO_FETCH_AT_ONCE = 10;
    private static Point windowSize;
    private String mDiscussionId;
    private BubbleAdapter mListViewAdapter;
    private EndlessListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        /*Display display = getWindowManager().getDefaultDisplay();
        this.windowSize = new Point();
        display.getSize(this.windowSize);*/

        Intent startingIntent = getIntent();
        this.mDiscussionId = startingIntent.getStringExtra(DiscussionsActivity.class.getID());

        mListView = findViewById(R.id.list);
        mListView.setLoadingView(R.layout.loading_layout);

        DatabaseLoader dbl = new DatabaseLoader();
        List<MessageViewModel> firstMessages = dbl.doInBackground();

        mListViewAdapter = new BubbleAdapter(this, R.layout.messageRow, firstMessages);
        mListView.setBubbleAdapter(mListViewAdapter);
        mListView.setEndlessListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.private_discussion, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class DatabaseLoader extends AsyncTask<Void, Void, List<MessageViewModel>> {

        @Override
        protected List<MessageViewModel> doInBackground(Void... params) {
            long lastMessageTimestamp = mListViewAdapter.getLastMessageTimestamp();

            List<MessageViewModel> newMessages = StorageAccess.getMessages(NUMBER_OF_MESSAGES_TO_FETCH_AT_ONCE, lastMessageTimestamp);

            return newMessages;
        }

        @Override
        protected void onPostExecute(List<MessageViewModel> result) {
            super.onPostExecute(result);
            mListView.addNewData(result);
        }
    }

    @Override
    public void loadData() {
        DatabaseLoader dbl = new DatabaseLoader();
        dbl.execute();
    }
}
