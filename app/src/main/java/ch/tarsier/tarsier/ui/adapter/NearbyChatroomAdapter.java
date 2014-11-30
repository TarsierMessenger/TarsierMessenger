package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.util.DateUtil;

/**
 * Created by Marin on 23.11.2014.
 */
public class NearbyChatroomAdapter extends ArrayAdapter<Chat> {
    private List<Chat> mChatrooms;
    private int mLayoutResourceId;
    private Context mContext;

    public NearbyChatroomAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mChatrooms = new ArrayList<Chat>();
    }

    public void clear() {
        mChatrooms.clear();
    }

    public void setChatList(List<Chat> newListChat) {
        mChatrooms = newListChat;
    }

    @Override
    public Chat getItem(int position) {
        return mChatrooms.get(position);
    }

    @Override
    public int getCount() {
        return mChatrooms.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        NearbyChatroomHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new NearbyChatroomHolder();
            //TODO check if row.getId() is the good id
            holder.mName = (TextView) row.findViewById(R.id.nearbyChatRoomName);
            holder.mLastTimestamp = (TextView) row.findViewById(R.id.nearbyChatRoomLastTimestamp);
            holder.mNbPeers = (TextView) row.findViewById(R.id.nearbyChatRoomNbPeers);

            row.setTag(holder);
        } else {
            holder = (NearbyChatroomHolder) row.getTag();
        }

        Chat nearbyChatroom = getItem(position);

        MessageRepository messageRepository = Tarsier.app().getMessageRepository();
        Message lastMessage = null;
        //TODO enable this when changes from @gluthier are merged.
//        try {
//            lastMessage = messageRepository.getLastMessageOf(nearbyChatroom);
//        } catch (NoSuchModelException nsme) {
//            nsme.printStackTrace();
//        } catch (InvalidModelException ime) {
//            ime.printStackTrace();
//        }

        holder.mName.setText(nearbyChatroom.getTitle());
        //FIXME Remove this when the TODO above is done.
        if (lastMessage == null) {
             holder.mLastTimestamp.setText(DateUtil.computeDateSeparator(DateUtil.getNowTimestamp()));
        } else {
            holder.mLastTimestamp.setText(DateUtil.computeDateSeparator(lastMessage.getDateTime()));
        }//holder.mNbPeers.setText("Participants : " + nearbyChatroom.getNbPeers());

        return row;
    }

    /**
     * ChatRoomSummaryHolder is the class containing the chatRoom's information
     */
    private class NearbyChatroomHolder {
        private TextView mName;
        private TextView mLastTimestamp;
        private TextView mNbPeers;
    }
}
