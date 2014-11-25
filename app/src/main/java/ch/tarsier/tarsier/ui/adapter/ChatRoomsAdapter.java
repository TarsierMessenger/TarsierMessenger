package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.ChatRoomSummary;
import ch.tarsier.tarsier.domain.model.DiscussionSummary;

/**
 * Created by Marin on 23.11.2014.
 */
public class ChatRoomsAdapter extends ArrayAdapter<ChatRoomSummary> {
    private ChatRoomSummary[] mChatrooms;
    private int mLayoutResourceId;
    private Context mContext;

    public ChatRoomsAdapter(Context context, int layoutResourceId, ChatRoomSummary[] chatrooms) {
        super(context, layoutResourceId, chatrooms);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mChatrooms = chatrooms;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChatRoomSummaryHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new ChatRoomSummaryHolder();
            //TODO check if row.getId() is the good id
            holder.mId = row.getId();
            holder.mName = (TextView) row.findViewById(R.id.nearbyChatRoomName);
            holder.mLastMessage = (TextView) row.findViewById(R.id.nearbyChatRoomLastMessage);
            holder.mNbPeers = (TextView) row.findViewById(R.id.nearbyChatRoomNbPeers);

            row.setTag(holder);
        } else {
            holder = (ChatRoomSummaryHolder) row.getTag();
        }

        ChatRoomSummary chatRoomSummary = mChatrooms[position];
        holder.mId = chatRoomSummary.getId();
        holder.mName.setText(chatRoomSummary.getChatRoomName());
        holder.mLastMessage.setText(chatRoomSummary.getChatRoomLastMessage());
        holder.mNbPeers.setText("Participants : " + chatRoomSummary.getNbPeers());

        return row;
    }

    /**
     * ChatRoomSummaryHolder is the class containing the chatRoom's information
     */
    private class ChatRoomSummaryHolder {
        private int mId;
        private TextView mName;
        private TextView mLastMessage;
        private TextView mNbPeers;
    }
}
