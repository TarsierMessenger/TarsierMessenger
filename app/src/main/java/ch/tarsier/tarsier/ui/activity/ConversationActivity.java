package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.ui.adapter.BubbleAdapter;
import ch.tarsier.tarsier.util.DateUtil;
import ch.tarsier.tarsier.ui.view.EndlessListView;
import ch.tarsier.tarsier.ui.view.EndlessListener;
import ch.tarsier.tarsier.storage.MessageViewModel;
import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.storage.Message;
import ch.tarsier.tarsier.storage.StorageAccess;

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

    // TODO: Store those IDs in their own class, so that they can be shared between classes
    //       while reducing the coupling a little.
    private static final String EXTRA_DISCUSSION_ID = "discussionId";

    private static Point windowSize;
    private int mDiscussionId;
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
        mDiscussionId = startingIntent.getIntExtra(EXTRA_DISCUSSION_ID, -1);

        if (mDiscussionId == -1) {
            // FIXME: Handle this
        }

        mListView = (EndlessListView) findViewById(R.id.list);
        mListView.setLoadingView(R.layout.loading_layout);

        DatabaseLoader dbl = new DatabaseLoader();
        List<MessageViewModel> firstMessages = dbl.doInBackground();
        dbl.onPostExecute(firstMessages);

        mListViewAdapter = new BubbleAdapter(this, R.layout.message_row, firstMessages);
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

    /**
     * Async Task for the loading of the messages from the database on another thread.
     */
    private class DatabaseLoader extends AsyncTask<Void, Void, List<MessageViewModel>> {

        @Override
        protected List<MessageViewModel> doInBackground(Void... params) {
            long lastMessageTimestamp = mListViewAdapter.getLastMessageTimestamp();

            StorageAccess storage = Tarsier.app().getStorage();
            List<Message> newMessages = storage.getMessages(
                mDiscussionId,
                NUMBER_OF_MESSAGES_TO_FETCH_AT_ONCE,
                lastMessageTimestamp
            );

            //Encapsulate messages into messageViewModels
            ArrayList<MessageViewModel> newMessageViewModels = new ArrayList<MessageViewModel>();
            for (Message message: newMessages) {
                newMessageViewModels.add(new MessageViewModel(message));
            }

            return newMessageViewModels;
        }

        @Override
        protected void onPostExecute(List<MessageViewModel> result) {
            super.onPostExecute(result);
            mListView.addNewData(result);

            // Tell the ListView to stop retrieving messages since there all loaded in it.
            if (result.size() < NUMBER_OF_MESSAGES_TO_FETCH_AT_ONCE) {
                mListView.setAllMessagesLoaded(true);
            }
        }
    }

    public void sendMessage(View view) {
        String messageText = ((TextView) findViewById(R.id.message_to_send)).getText().toString();
        Message sentMessage = new Message(mDiscussionId, messageText, DateUtil.getNowTimestamp());

        //Add the message to the ListView
        MessageViewModel messageViewModel = new MessageViewModel(sentMessage);
        mListView.addNewData(messageViewModel);

        //Add the message to the database
        Tarsier.app().getStorage().addMessage(sentMessage);
    }

    @Override
    public void loadData() {
        DatabaseLoader dbl = new DatabaseLoader();
        dbl.execute();
    }
}
