package ch.tarsier.tarsier.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.ui.adapter.ChatListAdapter;

/**
 * ChatListView is the class that controls the view of the ChatListActivity.
 *
 * @see ch.tarsier.tarsier.ui.activity.ChatListActivity
 * @author gluthier
 */
public class ChatListView extends ListView {

    private View mFooter;
    private ChatListAdapter mChatListAdapter;

    public ChatListView(Context context) {
        super(context);
    }

    public ChatListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ChatListView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public ChatListAdapter getChatListAdapter() {
        return mChatListAdapter;
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
        this.removeFooterView(mFooter);

        if (data != null) {
            mChatListAdapter.addAllChats(data);
            mChatListAdapter.notifyDataSetChanged();
        }
    }
}
