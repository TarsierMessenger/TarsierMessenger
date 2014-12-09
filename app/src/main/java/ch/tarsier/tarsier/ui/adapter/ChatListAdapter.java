package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.util.DateUtil;

/**
 * @author gluthier
 */
public class ChatListAdapter extends ArrayAdapter<Chat> {

    private static final String INTRO_TEXT = ">";

    private Context mContext;
    private int mLayoutResourceId;
    private List<Chat> mChatList;

    public ChatListAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mChatList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mChatList.size();
    }

    @Override
    public Chat getItem(int position) {
        return mChatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mChatList.get(position) != null) {
            return mChatList.get(position).getId();
        }

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChatHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new ChatHolder();

            holder.mAvatar = (ImageView) row.findViewById(R.id.avatar);
            holder.mTitle = (TextView) row.findViewById(R.id.name);
            holder.mLastMessage = (TextView) row.findViewById(R.id.lastMessage);
            holder.mHumanTime = (TextView) row.findViewById(R.id.humanTime);

            row.setTag(holder);
        } else {
            holder = (ChatHolder) row.getTag();
        }

        Chat chat = this.getItem(position);

        MessageRepository messageRepository = Tarsier.app().getMessageRepository();
        Message lastMessage = null;
        try {
            lastMessage = messageRepository.getLastMessageOf(chat);
        } catch (NoSuchModelException | InvalidModelException e) {
            e.printStackTrace();
        }

        if (chat != null) {
            holder.mAvatar.setImageBitmap(chat.getPicture());
            holder.mTitle.setText(chat.getTitle());

            if (lastMessage != null) {
                if (chat.isPrivate()) {
                    holder.mLastMessage.setText(INTRO_TEXT + lastMessage.getText());
                } else {
                    Peer sender = null;
                    try {
                        sender = Tarsier.app().getPeerRepository()
                                .findByPublicKey(lastMessage.getSenderPublicKey());
                    } catch (NoSuchModelException e) {
                        e.printStackTrace();
                    }
                    holder.mLastMessage.setText(INTRO_TEXT + sender.getUserName() + ": " + lastMessage.getText());
                }

                holder.mHumanTime.setText(DateUtil.computeDateSeparator(lastMessage.getDateTime()));
            }
        }

        return row;
    }

    public void addAllChats(List<Chat> chatList) {
        this.clear();
        this.addAll(chatList);
        mChatList = chatList;
        this.setNotifyOnChange(true);
    }

    /**
     * ChatHolder is the class containing the chat's information
     */
    private class ChatHolder {
        private ImageView mAvatar;
        private TextView mTitle;
        private TextView mLastMessage;
        private TextView mHumanTime;
    }
}
