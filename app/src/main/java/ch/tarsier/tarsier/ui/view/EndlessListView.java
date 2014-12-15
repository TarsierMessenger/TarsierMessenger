package ch.tarsier.tarsier.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.ui.adapter.BubbleAdapter;

/**
 * EndlessListView is the class that controls the view of the ChatActivity.
 *
 * @see ch.tarsier.tarsier.ui.activity.ChatActivity
 * @author marinnicolini and xawill (extreme programming)
 */
public class EndlessListView extends ListView implements AbsListView.OnScrollListener {
    private static final long MILLISECONDS_IN_DAY = 24*60*60*1000;

    private EndlessListener mEndlessListener;
    private BubbleAdapter mBubbleAdapter;
    private View mHeader;
    private boolean isLoading;
    private boolean mAllMessagesLoaded;

    public EndlessListView(Context context) {
        super(context);
        this.setOnScrollListener(this);
        mAllMessagesLoaded = false;
    }

    public EndlessListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
        mAllMessagesLoaded = false;
    }

    public EndlessListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(this);
        mAllMessagesLoaded = false;
    }

    public void fetchOldMessages(List<Message> messages) {
        if (messages.isEmpty()) {
            removeHeaderView(mHeader);
            isLoading = false;
            return;
        }

        mBubbleAdapter.removeOldestSeparator();
        mBubbleAdapter.addAll(addDateSeparators(messages));
        mBubbleAdapter.notifyDataSetChanged();

        removeHeaderView(mHeader);
        isLoading = false;
    }

    public void addNewMessage(Message message) {
        long messageTimeStamp = message.getDateTime();
        if (message.getDateTime() - mBubbleAdapter.getLastMessageTimestamp() > MILLISECONDS_IN_DAY
                || mBubbleAdapter.isEmpty()) {
            DateSeparator dateSeparator = new DateSeparator(messageTimeStamp);
            mBubbleAdapter.insert(dateSeparator, 0);
        }

        mBubbleAdapter.insert(message, 0);
        mBubbleAdapter.notifyDataSetChanged();
    }

    private List<BubbleListViewItem> addDateSeparators(List<Message> data) {
        ArrayList<BubbleListViewItem> newListItems = new ArrayList<BubbleListViewItem>();

        Iterator<Message> i = data.iterator();
        Message nextMessage = i.next(); //We already checked if data is empty
        long currentMessageTimeStamp;
        long nextMessageTimeStamp = nextMessage.getDateTime();
        while (i.hasNext()) { //Iterate over reverse chronological order
            newListItems.add(nextMessage);

            currentMessageTimeStamp = nextMessageTimeStamp;
            nextMessage = i.next();
            nextMessageTimeStamp = nextMessage.getDateTime();
            if (currentMessageTimeStamp - nextMessageTimeStamp > MILLISECONDS_IN_DAY) {
                DateSeparator dateSeparator = new DateSeparator(currentMessageTimeStamp);
                newListItems.add(dateSeparator);
            }
        }
        newListItems.add(nextMessage);

        if (nextMessageTimeStamp - mBubbleAdapter.getLastMessageTimestamp() > MILLISECONDS_IN_DAY
                || mBubbleAdapter.isEmpty() || mAllMessagesLoaded) {
            DateSeparator dateSeparator = new DateSeparator(nextMessage.getDateTime());
            newListItems.add(dateSeparator);
        }

        return newListItems;
    }

    public void setLoadingView(int resId) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHeader = inflater.inflate(resId, null);
    }

    public void setEndlessListener(EndlessListener endlessListener) {
        this.mEndlessListener = endlessListener;
    }

    public void setBubbleAdapter(BubbleAdapter bubbleAdapter) {
        super.setAdapter(bubbleAdapter);
        this.mBubbleAdapter = bubbleAdapter;
    }

    public boolean allMessagesLoaded() {
        return mAllMessagesLoaded ;
    }

    public void setAllMessagesLoaded(boolean allMessagesLoaded) {
        mAllMessagesLoaded = allMessagesLoaded;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) { }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && getFirstVisiblePosition() == 0 && !isLoading
                && !mAllMessagesLoaded && mEndlessListener != null) {
            addHeaderView(mHeader);
            isLoading = true;
            mEndlessListener.loadData();
        }
    }
}