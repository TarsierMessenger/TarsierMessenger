package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.NearbyChatroomSummary;

/**
 * Created by Marin on 23.11.2014.
 */
public class NearbyChatroomAdapter extends ArrayAdapter<NearbyChatroomSummary> {
    private NearbyChatroomSummary[] mChatrooms;
    private int mLayoutResourceId;
    private Context mContext;

    public NearbyChatroomAdapter(Context context, int layoutResourceId, NearbyChatroomSummary[] chatrooms) {
        super(context, layoutResourceId, chatrooms);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mChatrooms = chatrooms;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        NearbyChatroomSummaryHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new NearbyChatroomSummaryHolder();
            //TODO check if row.getId() is the good id
            holder.mId = row.getId();
            holder.mName = (TextView) row.findViewById(R.id.nearbyChatRoomName);
            holder.mLastMessage = (TextView) row.findViewById(R.id.nearbyChatRoomLastMessage);
            holder.mNbPeers = (TextView) row.findViewById(R.id.nearbyChatRoomNbPeers);

            row.setTag(holder);
        } else {
            holder = (NearbyChatroomSummaryHolder) row.getTag();
        }

        NearbyChatroomSummary nearbyChatroomSummary = mChatrooms[position];
        holder.mId = nearbyChatroomSummary.getId();
        holder.mName.setText(nearbyChatroomSummary.getChatroomName());
        holder.mLastMessage.setText(nearbyChatroomSummary.getChatroomLastMessage());
        holder.mNbPeers.setText("Participants : " + nearbyChatroomSummary.getNbPeers());

        return row;
    }

    /**
     * ChatRoomSummaryHolder is the class containing the chatRoom's information
     */
    private class NearbyChatroomSummaryHolder {
        private int mId;
        private TextView mName;
        private TextView mLastMessage;
        private TextView mNbPeers;
    }
}
