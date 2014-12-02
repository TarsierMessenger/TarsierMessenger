package ch.tarsier.tarsier.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.util.DateUtil;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.R;

/**
 * @author marinnicolini and xawill (extreme programming)
 *
 * Custom class to display the bubbles in the ChatActivity ListView
 *
 * Inspired from https://github.com/AdilSoomro/Android-Speech-Bubble
 * and https://github.com/survivingwithandroid/Surviving-with-android/tree/master/EndlessAdapter
 */
public class BubbleAdapter extends ArrayAdapter<Message> {
    private Context mContext;
    private List<Message> mMessages;
    private int mLayoutId;
    private HashMap<String, Peer> mPeers;

    public BubbleAdapter(Context context, int layoutId, List<Message> messages) {
        super(context, layoutId, messages);

        mContext = context;
        mLayoutId = layoutId;
        mMessages = messages;
        mPeers = new HashMap<String, Peer>();
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    /**
     * Return the element at the opposite of the position, since we want the messages in the reverse order.
     * @param position The position of the item to get (from the top)
     * @return The element at the given position (reverse order)
     */
    @Override
    public Message getItem(int position) {
        return mMessages.get(mMessages.size()-1-position);
    }

    @Override
    public long getItemId(int position) {
        //TODO : implement with database ID ?
        return position;
    }

    public long getLastMessageTimestamp() {
        if (mMessages.size() > 0) {
            return mMessages.get(mMessages.size()-1).getDateTime();
        } else {
            return DateUtil.getNowTimestamp();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = this.getItem(position);

        String messageSenderPublicKey = message.getSenderPublicKey().base64Encoded();
        Peer sender;
        if (mPeers.containsKey(messageSenderPublicKey)) {
            sender = mPeers.get(messageSenderPublicKey);
        } else {
            try {
                sender = Tarsier.app().getPeerRepository().findByPublicKey(message.getSenderPublicKey());
            } catch (NoSuchModelException e) {
                e.printStackTrace();
                return convertView;
            }

            mPeers.put(messageSenderPublicKey, sender);
        }

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
            holder.dateSeparator = (TextView) convertView.findViewById(R.id.dateSeparator);
            holder.picture = (ImageView) convertView.findViewById(R.id.picture);
            holder.bubble = (LinearLayout) convertView.findViewById(R.id.bubble);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.hour = (TextView) convertView.findViewById(R.id.hour);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.dateSeparator.setText(DateUtil.computeDateSeparator(message.getDateTime()));
        Bitmap profilePicture = sender.getPicture();
        holder.picture.setImageBitmap(profilePicture);
        holder.name.setText(sender.getUserName());
        holder.message.setText(message.getText());
        holder.hour.setText(DateUtil.computeHour(message.getDateTime()));

        RelativeLayout.LayoutParams pictureLp = (RelativeLayout.LayoutParams) holder.picture.getLayoutParams();
        RelativeLayout.LayoutParams bubbleLp = (RelativeLayout.LayoutParams) holder.bubble.getLayoutParams();
        LinearLayout.LayoutParams messageLp = (LinearLayout.LayoutParams) holder.message.getLayoutParams();

        if (message.isSentByUser()) {
            holder.name.setVisibility(View.GONE);
            holder.bubble.setBackgroundResource(R.drawable.bubble_text_right);
            pictureLp.addRule(RelativeLayout.ALIGN_PARENT_END);
            bubbleLp.addRule(RelativeLayout.LEFT_OF, holder.picture.getId());
            messageLp.gravity = Gravity.RIGHT;
        } else {
            holder.bubble.setBackgroundResource(R.drawable.bubble_text_left);
            bubbleLp.addRule(RelativeLayout.RIGHT_OF, holder.picture.getId());
        }

        holder.picture.setLayoutParams(pictureLp);
        holder.bubble.setLayoutParams(bubbleLp);
        holder.message.setLayoutParams(messageLp);

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    /**
     * Class encapsulating ListView row in a view from the message_row XML
     */
    static class ViewHolder {
        private TextView dateSeparator;
        private ImageView picture;
        private LinearLayout bubble;
        private TextView name;
        private TextView message;
        private TextView hour;
    }
}
