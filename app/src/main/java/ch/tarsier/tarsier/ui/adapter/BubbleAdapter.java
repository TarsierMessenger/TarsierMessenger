package ch.tarsier.tarsier.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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
 * @author xawill
 *
 * Custom class to display the bubbles in the ChatActivity ListView
 *
 * Inspired from https://github.com/AdilSoomro/Android-Speech-Bubble
 * and https://github.com/survivingwithandroid/Surviving-with-android/tree/master/EndlessAdapter
 */
public class BubbleAdapter extends ArrayAdapter<Message> {
    private static final String TAG = "BubbleAdapter";

    private static final int TYPE_BUBBLE_LEFT = 0;
    private static final int TYPE_BUBBLE_RIGHT = 1;
    //private static final int TYPE_DATE_SEPARATOR = 2;
    private static final int TYPE_MAX_COUNT = 2;

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

    public long getLastMessageTimestamp() {
        if (mMessages.size() > 0) {
            return mMessages.get(getCount() - 1).getDateTime();
        } else {
            return DateUtil.getNowTimestamp();
        }
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
        return super.getItem(getCount() - 1 - position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(getCount() - 1 - position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isSentByUser() ? TYPE_BUBBLE_RIGHT : TYPE_BUBBLE_LEFT;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView " + position + " " + convertView);

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
            holder.picture = (ImageView) convertView.findViewById(R.id.picture);
            holder.bubble = (LinearLayout) convertView.findViewById(R.id.bubble);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.hour = (TextView) convertView.findViewById(R.id.hour);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bitmap profilePicture = sender.getPicture();
        holder.picture.setImageBitmap(profilePicture);
        holder.name.setText(sender.getUserName());
        holder.message.setText(message.getText());
        holder.hour.setText(DateUtil.computeHour(message.getDateTime()));

        RelativeLayout.LayoutParams pictureLp = (RelativeLayout.LayoutParams) holder.picture.getLayoutParams();
        RelativeLayout.LayoutParams bubbleLp = (RelativeLayout.LayoutParams) holder.bubble.getLayoutParams();
        LinearLayout.LayoutParams messageLp = (LinearLayout.LayoutParams) holder.message.getLayoutParams();

        int type = getItemViewType(position);
        switch (type) {
            case TYPE_BUBBLE_LEFT :
                holder.bubble.setBackgroundResource(R.drawable.bubble_text_left);
                bubbleLp.addRule(RelativeLayout.RIGHT_OF, holder.picture.getId());
                break;
            case TYPE_BUBBLE_RIGHT :
                holder.name.setVisibility(View.GONE);
                holder.bubble.setBackgroundResource(R.drawable.bubble_text_right);
                pictureLp.addRule(RelativeLayout.ALIGN_PARENT_END);
                bubbleLp.addRule(RelativeLayout.LEFT_OF, holder.picture.getId());
                messageLp.gravity = Gravity.END;
                break;
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
        private ImageView picture;
        private LinearLayout bubble;
        private TextView name;
        private TextView message;
        private TextView hour;
    }
}
