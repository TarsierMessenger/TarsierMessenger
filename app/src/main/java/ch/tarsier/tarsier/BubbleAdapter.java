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
    private ArrayList<MessageLayout> mMessageLayouts;

    public BubbleAdapter(Context context, ArrayList<MessageLayout> messages) {
        super();
        this.mContext = context;
        this.mMessageLayouts = messages;
    }

    @Override
    public int getCount() {
        return mMessageLayouts.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageLayouts.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageLayout messageLayout = (MessageLayout) this.getItem(position);

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


        holder.dateSeparator.setText(computeDateSeparator(messageLayout.getTimeSent()));
        holder.picture.setImageBitmap(messageLayout.getPicture());
        holder.name.setText(messageLayout.getAuthorName());
        holder.message.setText(messageLayout.getText());
        holder.hour.setText(computeHour(messageLayout.getTimeSent()));

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

    private String computeHour(long timestamp) {
        Date sentHour = new Date(timestamp);

        Format format = new SimpleDateFormat("HH:mm");
        return format.format(sentHour);
    }

    private String computeDateSeparator(long timestamp) {
        Calendar calendar = Calendar.getInstance();

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(currentYear, currentMonth, currentDay);//Today at 00:00
        long todayTimestamp = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_MONTH, -1);//Yesterday at 00:00
        long yesterdayTimestamp = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_MONTH, -5);//One week ago at 00:00
        long weekTimestamp = calendar.getTimeInMillis();

        Format format;
        if(timestamp >= todayTimestamp) { // today
            return "Today";
        } else if(timestamp >= yesterdayTimestamp) { // yesterday
            return "Yesterday";
        } else if(timestamp >= weekTimestamp) { // one week ago
            format = new SimpleDateFormat("E");
            return format.format(timestamp);
        } else { // further
            format = new SimpleDateFormat("dd.MM.yyyy");
            return format.format(timestamp);
        }
    }

    static class ViewHolder {
        TextView dateSeparator;
        ImageView picture;
        TextView name;
        TextView message;
        TextView hour;
    }
}
