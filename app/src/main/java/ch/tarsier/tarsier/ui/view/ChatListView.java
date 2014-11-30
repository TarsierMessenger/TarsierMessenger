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
 */

public class ChatListView extends ListView {

    private View mFooter;
    private EndlessListener mEndlessListener;
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
        this.removeFooterView(mFooter);

        if (data != null) {
            mChatListAdapter.addAllChats(data);
            mChatListAdapter.notifyDataSetChanged();
        } else {
            //TODO show message to start a new private chat or chatroom
        }
    }
}
