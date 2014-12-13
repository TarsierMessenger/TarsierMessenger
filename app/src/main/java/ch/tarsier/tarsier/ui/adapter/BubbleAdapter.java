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
import ch.tarsier.tarsier.domain.repository.UserRepository;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.ui.view.BubbleListViewItem;
import ch.tarsier.tarsier.ui.view.DateSeparator;
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
public class BubbleAdapter extends ArrayAdapter<BubbleListViewItem> {

    private UserRepository mUserRepository;

    /**
     * Types of items in the EndlessListView
     */
    public enum EndlessListViewType {
        BUBBLE_LEFT, BUBBLE_RIGHT, DATE_SEPARATOR
    }
    private static final int TYPE_MAX_COUNT = EndlessListViewType.values().length;

    private Context mContext;
    private List<BubbleListViewItem> mMessages;
    private HashMap<String, Peer> mPeers;

    public BubbleAdapter(Context context, List<BubbleListViewItem> messages) {
        super(context, 0, messages);

        mContext = context;
        mMessages = messages;
        mPeers = new HashMap<>();
        mUserRepository = Tarsier.app().getUserRepository();
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
    public BubbleListViewItem getItem(int position) {
        return super.getItem(getCount() - 1 - position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(getCount() - 1 - position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        BubbleListViewItem listViewItem = getItem(position);
        return listViewItem.getEndlessListViewType().ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BubbleListViewItem listViewItem = this.getItem(position);

        EndlessListViewType itemType = EndlessListViewType.values()[getItemViewType(position)];
        switch (itemType) {
            case BUBBLE_LEFT:
            case BUBBLE_RIGHT:
                BubbleViewHolder bubbleViewHolder;
                if (convertView == null) {
                    bubbleViewHolder = new BubbleViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.bubble, parent, false);
                    bubbleViewHolder.picture = (ImageView) convertView.findViewById(R.id.picture);
                    bubbleViewHolder.bubble = (LinearLayout) convertView.findViewById(R.id.bubble);
                    bubbleViewHolder.name = (TextView) convertView.findViewById(R.id.name);
                    bubbleViewHolder.message = (TextView) convertView.findViewById(R.id.message);
                    bubbleViewHolder.hour = (TextView) convertView.findViewById(R.id.hour);
                    convertView.setTag(bubbleViewHolder);
                } else {
                    bubbleViewHolder = (BubbleViewHolder) convertView.getTag();
                }

                try {
                    inflateMessageRow(bubbleViewHolder, (Message) listViewItem, itemType);
                } catch (NoSuchModelException e) {
                    e.printStackTrace();
                    return convertView;
                }
                break;
            case DATE_SEPARATOR:
                DateSeparatorViewHolder dateSeparatorViewHolder;
                if (convertView == null) {
                    dateSeparatorViewHolder = new DateSeparatorViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.date_separator, parent, false);
                    dateSeparatorViewHolder.date = (TextView) convertView.findViewById(R.id.date);
                    convertView.setTag(dateSeparatorViewHolder);
                } else {
                    dateSeparatorViewHolder = (DateSeparatorViewHolder) convertView.getTag();
                }

                //TODO /!\ Attention, vérifier que le computeDateSeparator
                //TODO est bien modifié tous les jours pour chaque date separator
                inflateDateSeparator(dateSeparatorViewHolder, (DateSeparator) listViewItem);
                break;
            default:
                break;
        }

        return convertView;
    }

    private BubbleViewHolder inflateMessageRow(BubbleViewHolder viewHolder, Message message,
            EndlessListViewType type) throws NoSuchModelException {
        String messageSenderPublicKey = message.getSenderPublicKey().base64Encoded();
        Peer sender;
        if (message.isSentByUser()) {
            sender = mUserRepository.getUser();
        } else if (mPeers.containsKey(messageSenderPublicKey)) {
            sender = mPeers.get(messageSenderPublicKey);
        } else {
            sender = Tarsier.app().getPeerRepository().findByPublicKey(message.getSenderPublicKey());
            mPeers.put(messageSenderPublicKey, sender);
        }

        Bitmap profilePicture = sender.getPicture();
        viewHolder.picture.setImageBitmap(profilePicture);
        viewHolder.name.setText(sender.getUserName());
        viewHolder.message.setText(message.getText());
        viewHolder.hour.setText(DateUtil.computeHour(message.getDateTime()));

        RelativeLayout.LayoutParams pictureLp = (RelativeLayout.LayoutParams) viewHolder.picture.getLayoutParams();
        RelativeLayout.LayoutParams bubbleLp = (RelativeLayout.LayoutParams) viewHolder.bubble.getLayoutParams();
        LinearLayout.LayoutParams messageLp = (LinearLayout.LayoutParams) viewHolder.message.getLayoutParams();

        switch (type) {
            case BUBBLE_LEFT:
                viewHolder.bubble.setBackgroundResource(R.drawable.bubble_text_left);
                bubbleLp.addRule(RelativeLayout.RIGHT_OF, viewHolder.picture.getId());
                break;
            case BUBBLE_RIGHT:
                viewHolder.name.setVisibility(View.GONE);
                viewHolder.bubble.setBackgroundResource(R.drawable.bubble_text_right);
                pictureLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                bubbleLp.addRule(RelativeLayout.LEFT_OF, viewHolder.picture.getId());
                messageLp.gravity = Gravity.END;
                break;
            default:
                break;
        }

        viewHolder.picture.setLayoutParams(pictureLp);
        viewHolder.bubble.setLayoutParams(bubbleLp);
        viewHolder.message.setLayoutParams(messageLp);

        return viewHolder;
    }

    private DateSeparatorViewHolder inflateDateSeparator(DateSeparatorViewHolder viewHolder,
                                                         DateSeparator dateSeparator) {
        viewHolder.date.setText(DateUtil.computeDateSeparator(dateSeparator.getTimeStamp()));
        return viewHolder;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    /**
     *
     * @return if the removal has been successful
     */
    public boolean removeOldestSeparator() {
        if (!isEmpty()) {
            BubbleListViewItem oldestItem = mMessages.get(getCount()-1);

            if (oldestItem.getEndlessListViewType() == EndlessListViewType.DATE_SEPARATOR) {
                remove(oldestItem);
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * Wrapper of bubbles list item
     */
    static class BubbleViewHolder {
        private ImageView picture;
        private LinearLayout bubble;
        private TextView name;
        private TextView message;
        private TextView hour;
    }

    /**
     * Wrapper of date separators list item
     */
    static class DateSeparatorViewHolder {
        private TextView date;
    }
}
