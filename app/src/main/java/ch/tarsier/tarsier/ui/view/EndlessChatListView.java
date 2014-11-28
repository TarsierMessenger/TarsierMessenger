package ch.tarsier.tarsier.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.ui.adapter.ChatListAdapter;

/*
 * @author gluthier
 * cf http://www.survivingwithandroid.com/2013/10/android-listview-endless-adapter.html
 */

public class EndlessChatListView extends ListView {

    private View mFooter;
    private boolean mIsLoading;
    private EndlessListener mEndlessListener;
    private ChatListAdapter mChatListAdapter;

    public EndlessChatListView(Context context) {
        super(context);
    }

    public EndlessChatListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EndlessChatListView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public void setEndlessListener(EndlessListener listener) {
        mEndlessListener = listener;
    }

    public void setChatListAdapter(ChatListAdapter chatListAdapter) {
        super.setAdapter(chatListAdapter);
        mChatListAdapter = chatListAdapter;
    }

    public void setLoadingView(int resId) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFooter = inflater.inflate(resId, null);
        this.addFooterView(mFooter);

    }

    public void addNewData(List<Chat> data) {
        mIsLoading = false;
        this.removeFooterView(mFooter);

        if (data != null) {
            mChatListAdapter.addAllChats(data);
            mChatListAdapter.notifyDataSetChanged();
        }
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
    }
}
