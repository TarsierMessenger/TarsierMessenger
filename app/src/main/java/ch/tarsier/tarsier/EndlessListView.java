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
    private View mFooter;
    private boolean isLoading;

    public EndlessListView(Context context){
        super(context);
        this.setOnScrollListener(this);
    }

    public void addNewData(List<MessageViewModel> data) {
        this.removeFooterView(mFooter);

        mBubbleAdapter.addAll(data);
        mBubbleAdapter.notifyDataSetChanged();
        isLoading = false;
    }

    public void setLoadingView(int resId) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFooter = (View) inflater.inflate(resId, null);
        this.addFooterView(mFooter);
    }

    public void setEndlessListener(EndlessListener endlessListener){
        this.mEndlessListener = endlessListener;
    }

    public void setBubbleAdapter(BubbleAdapter bubbleAdapter) {
        super.setAdapter(bubbleAdapter);
        this.mBubbleAdapter = bubbleAdapter;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem + visibleItemCount >= totalItemCount){
            this.addFooterView(mFooter);
            isLoading = true;
            mEndlessListener.loadData();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) { }
}