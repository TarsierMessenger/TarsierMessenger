package ch.tarsier.tarsier.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.ui.adapter.ChatListAdapter;

/**
 * @author gluthier
 * cf http://www.survivingwithandroid.com/2013/10/android-listview-endless-adapter.html
 */
public class EndlessChatListView extends ListView implements AbsListView.OnScrollListener {

    private View mFooter;
    private boolean mIsLoading;
    private EndlessListener mEndlessListener;
    private ChatListAdapter mChatListAdapter;

    public EndlessChatListView(Context context) {
        super(context);
        this.setOnScrollListener(this);
    }

    public EndlessChatListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setOnScrollListener(this);
    }

    public EndlessChatListView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        this.setOnScrollListener(this);
    }

    public void setEndlessListener(EndlessListener listener) {
        mEndlessListener = listener;
    }

    public void setChatListAdapter(ChatListAdapter chatListAdapter) {
        super.setAdapter(chatListAdapter);
        mChatListAdapter = chatListAdapter;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
/*
        if (getAdapter() ==  null) {
            return;
        }

        if (getAdapter().getCount() == 0) {
            return;
        }

        int l = visibleItemCount + firstVisibleItem;
        if (l >= totalItemCount && !mIsLoading) {
            this.addFooterView(mFooter);
            mIsLoading = true;
            mEndlessListener.loadData();
        }
*/
    }

    public void setLoadingView(int resId) {
/*
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFooter = (View) inflater.inflate(resId, null);
        this.addFooterView(mFooter);
*/
    }

    public void addNewData(List<Chat> data) {
        mIsLoading = false;
        this.removeFooterView(mFooter);

        if (data != null) {
            mChatListAdapter.addAll(data);
            mChatListAdapter.notifyDataSetChanged();
        }
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
    }
}
