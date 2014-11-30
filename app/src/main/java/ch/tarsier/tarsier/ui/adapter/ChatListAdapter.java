package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.ChatSummary;
import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.util.DateUtil;

/**
 * @author gluthier
 */
public class ChatListAdapter extends ArrayAdapter<Chat> {

    private List<Chat> mChatList;
    private Context mContext;
    private int mLayoutResourceId;

    public ChatListAdapter(Context context, int layoutResourceId, List<Chat> chatList) {
        super(context, layoutResourceId, chatList);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mChatList = chatList;
    }

    @Override
    public int getCount() {
        return mChatList.size();
    }

    @Override
    public long getItemId(int position) {
        return mChatList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChatHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new ChatHolder();

            holder.mAvatarSrc = (ImageView) row.findViewById(R.id.avatar);
            holder.mTitle = (TextView) row.findViewById(R.id.name);
            holder.mLastMessage = (TextView) row.findViewById(R.id.lastMessage);
            holder.mHumanTime = (TextView) row.findViewById(R.id.humanTime);

            row.setTag(holder);
        } else {
            holder = (ChatHolder) row.getTag();
        }

        Chat chat = mChatList.get(position);

        MessageRepository messageRepository = Tarsier.app().getMessageRepository();
        Message lastMessage = null;
        try {
            lastMessage = messageRepository.getLastMessageOf(chat);
        } catch (NoSuchModelException e) {
            e.printStackTrace();
        } catch (InvalidCursorException e) {
            e.printStackTrace();
        }

        holder.mId = chat.getId();
        holder.mAvatarSrc.setImageResource(chat.getAvatarRessourceId());
        holder.mTitle.setText(chat.getTitle());
        holder.mLastMessage.setText(lastMessage.getText());
        holder.mHumanTime.setText(DateUtil.computeDateSeparator(lastMessage.getDateTime()));

        return row;
    }

    /**
     * DiscussionSummaryHolder is the class containing the discussion's information
     */
    private class ChatHolder {
        private long mId;
        private ImageView mAvatarSrc;
        private TextView mTitle;
        private TextView mLastMessage;
        private TextView mHumanTime;
    }

}
