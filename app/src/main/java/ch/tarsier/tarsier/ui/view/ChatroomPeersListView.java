package ch.tarsier.tarsier.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.ui.adapter.ChatroomPeersAdapter;

/**
 * ChatroomPeersListView is the class that controls the view of the ChatroomPeersActivity.
 *
 * @see ch.tarsier.tarsier.ui.activity.ChatroomPeersActivity
 * @author gluthier
 */
public class ChatroomPeersListView extends ListView {

    private View mFooter;
    private ChatroomPeersAdapter mChatroomPeersAdapter;

    public ChatroomPeersListView(Context context) {
        super(context);
    }

    public ChatroomPeersListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ChatroomPeersListView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public void setChatroomPeersAdapter(ChatroomPeersAdapter chatroomPeersAdapter) {
        super.setAdapter(chatroomPeersAdapter);
        mChatroomPeersAdapter = chatroomPeersAdapter;
    }

    public ChatroomPeersAdapter getChatroomPeersAdapter() {
        return mChatroomPeersAdapter;
    }

    public void setLoadingView(int resId) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFooter = inflater.inflate(resId, null);
        addFooterView(mFooter);
    }

    public void addNewData(List<Peer> data) {
        removeFooterView(mFooter);

        if (data != null) {
            mChatroomPeersAdapter.addAllPeers(data);
            mChatroomPeersAdapter.notifyDataSetChanged();
        }
    }
}
