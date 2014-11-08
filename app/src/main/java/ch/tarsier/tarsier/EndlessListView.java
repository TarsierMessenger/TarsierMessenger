package ch.tarsier.tarsier;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * @author xawill
 */
public class EndlessListView extends ListView implements AbsListView.OnScrollListener {
    private EndlessListener mEndlessListener;
    private BubbleAdapter mBubbleAdapter;

    public EndlessListView(Context context){
        super(context);
        this.setOnScrollListener(this);
    }

    public void setEndlessListener(EndlessListener endlessListener){
        this.mEndlessListener = endlessListener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem+visibleItemCount>=totalItemCount){
            mEndlessListener.loadData();
        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) { }

    public void setAdapter(BubbleAdapter bubbleAdapter) {
        super.setAdapter(bubbleAdapter);
        this.mBubbleAdapter = bubbleAdapter;
    }

}