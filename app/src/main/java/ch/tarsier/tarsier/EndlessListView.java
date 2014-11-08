package ch.tarsier.tarsier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

/**
 * @author xawill
 */
public class EndlessListView extends ListView implements AbsListView.OnScrollListener {
    private EndlessListener mEndlessListener;
    private BubbleAdapter mBubbleAdapter;
    private View mHeader;
    private boolean isLoading;
    private boolean allMessagesLoaded;

    public EndlessListView(Context context){
        super(context);
        this.setOnScrollListener(this);
        allMessagesLoaded = false;
    }

    public void addNewData(List<MessageViewModel> data) {
        this.removeHeaderView(mHeader);

        mBubbleAdapter.addAll(data);
        mBubbleAdapter.notifyDataSetChanged();
        isLoading = false;
    }

    public void addNewData(MessageViewModel data) {
        mBubbleAdapter.add(data);
        mBubbleAdapter.notifyDataSetChanged();
    }

    public void setLoadingView(int resId) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHeader = (View) inflater.inflate(resId, null);
        this.addHeaderView(mHeader);
    }

    public void setEndlessListener(EndlessListener endlessListener){
        this.mEndlessListener = endlessListener;
    }

    public void setBubbleAdapter(BubbleAdapter bubbleAdapter) {
        super.setAdapter(bubbleAdapter);
        this.mBubbleAdapter = bubbleAdapter;
    }

    public void setAllMessagesLoaded(boolean allMessagesLoaded) {
        this.allMessagesLoaded = allMessagesLoaded;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem + visibleItemCount >= totalItemCount){
            if(!allMessagesLoaded) {
                this.addHeaderView(mHeader);
                isLoading = true;
                mEndlessListener.loadData();
            } else {
                //All messages fetched => we are at the top of the list.
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) { }
}