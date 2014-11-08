package ch.tarsier.tarsier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;

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
public class ConversationActivity extends Activity implements EndlessListener {
    private static Point windowSize;

    private String mDiscussionId;
    private boolean mIsPrivate;
    private ArrayList<Message> mMessages;
    private String mTitle;
    private BubbleAdapter mListViewAdapter;
    private EndlessListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Display display = getWindowManager().getDefaultDisplay();
        this.windowSize = new Point();
        display.getSize(this.windowSize);

        Intent startingIntent = getIntent();

        this.mDiscussionId = startingIntent.getStringExtra(DiscussionsActivity.class.getID());
        this.mMessages = StorageManager.getMessages(this.mDiscussionId);
        this.mTitle = StorageManager.getChat(this.mDiscussionId).getTitle();
        this.mIsPrivate = StorageManager.getChat(this.mDiscussionId).isPrivate();

        mListView = findViewById(R.id.list);
        mListView.setLoadingView(R.layout.loading_layout);

        mListViewAdapter = new BubbleAdapter(this);
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

    @Override
    public void loadData() {

    }
}
