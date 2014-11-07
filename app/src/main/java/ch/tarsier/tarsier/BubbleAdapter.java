package ch.tarsier.tarsier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author marinnicolini and xawill (extreme programming)
 *
 * Custom class to display the bubbles in the ConversationActivity ListView
 *
 * Inspired from https://github.com/AdilSoomro/Android-Speech-Bubble
 */
public class BubbleAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<MessageViewModel> mMessageViewModels;
    private int mNumberOfMessages; //TODO : must be adapted when new messages are sent
    private static final int NUMBER_OF_MESSAGES_TO_FETCH = 10;

    public BubbleAdapter(Context context) {
        super();
        this.mContext = context;
        this.mMessageViewModels = StorageAccess.getMessages(NUMBER_OF_MESSAGES_TO_FETCH, DateUtil.getNowTimestamp());
        this.mNumberOfMessages = StorageAccess.getMessageCount();
    }

    @Override
    public int getCount() {
        return mNumberOfMessages;
    }

    @Override
    public Object getItem(int position) {
        if(position < mMessageViewModels.size()) {
            return mMessageViewModels.get(position);
        } else {
            long lastMessageTimestamp = mMessageViewModels.get(mMessageViewModels.size()-1).getTimeSent();
            mMessageViewModels.add(StorageAccess.getMessages(NUMBER_OF_MESSAGES_TO_FETCH, lastMessageTimestamp));
            return mMessageViewModels.get(position);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageViewModel messageLayout = (MessageViewModel) this.getItem(position);

        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.messageRow, parent, false);
            holder.dateSeparator = (TextView) convertView.findViewById(R.id.dateSeparator);
            holder.picture = (ImageView) convertView.findViewById(R.id.picture);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.hour = (TextView) convertView.findViewById(R.id.hour);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.dateSeparator.setText(DateUtil.computeDateSeparator(messageLayout.getTimeSent()));
        holder.picture.setImageBitmap(messageLayout.getPicture());
        holder.name.setText(messageLayout.getAuthorName());
        holder.message.setText(messageLayout.getText());
        holder.hour.setText(DateUtil.computeHour(messageLayout.getTimeSent()));

        /**
        LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();
        //check if it is a status message then remove background, and change text color.
        if(message.isStatusMessage())
        {
            holder.message.setBackgroundDrawable(null);
            lp.gravity = Gravity.LEFT;
            holder.message.setTextColor(R.color.textFieldColor);
        }
        else
        {
            //Check whether message is mine to show green background and align to right
            if(message.isMine())
            {
                holder.message.setBackgroundResource(R.drawable.speech_bubble_green);
                lp.gravity = Gravity.RIGHT;
            }
            //If not mine then it is from sender to show orange background and align to left
            else
            {
                holder.message.setBackgroundResource(R.drawable.speech_bubble_orange);
                lp.gravity = Gravity.LEFT;
            }
            holder.message.setLayoutParams(lp);
            holder.message.setTextColor(R.color.textColor);
        }
         **/
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView dateSeparator;
        ImageView picture;
        TextView name;
        TextView message;
        TextView hour;
    }
}
