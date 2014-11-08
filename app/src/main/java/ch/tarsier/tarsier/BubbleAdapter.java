package ch.tarsier.tarsier;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.List;

/**
 * @author marinnicolini and xawill (extreme programming)
 *
 * Custom class to display the bubbles in the ConversationActivity ListView
 *
 * Inspired from https://github.com/AdilSoomro/Android-Speech-Bubble
 * and https://github.com/survivingwithandroid/Surviving-with-android/tree/master/EndlessAdapter
 */
public class BubbleAdapter extends ArrayAdapter<MessageViewModel> {
    private Context mContext;
    private List<MessageViewModel> mMessageViewModels;
    private int mLayoutId;

    public BubbleAdapter(Context context, int layoutId, List<MessageViewModel> messageViewModel) {
        super(context, layoutId, messageViewModel);

        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mMessageViewModels = messageViewModel;
    }

    @Override
    public int getCount() {
        return mMessageViewModels.size();
    }

    @Override
    public MessageViewModel getItem(int position) {
        return mMessageViewModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        //TODO : implement with database ID ?
        return position;
    }

    public long getLastMessageTimestamp() {
        if(mMessageViewModels.size() > 0) {
            return mMessageViewModels.get(mMessageViewModels.size()-1).getTimeSent();
        } else {
            return DateUtil.getNowTimestamp();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageViewModel messageViewModel = (MessageViewModel) this.getItem(position);

        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
            holder.dateSeparator = (TextView) convertView.findViewById(R.id.dateSeparator);
            holder.picture = (ImageView) convertView.findViewById(R.id.picture);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.hour = (TextView) convertView.findViewById(R.id.hour);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.dateSeparator.setText(DateUtil.computeDateSeparator(messageViewModel.getTimeSent()));
        holder.picture.setImageBitmap(messageViewModel.getPicture());
        holder.name.setText(messageViewModel.getAuthorName());
        holder.message.setText(messageViewModel.getText());
        holder.hour.setText(DateUtil.computeHour(messageViewModel.getTimeSent()));

        LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();

        if(messageViewModel.isMessageSentByUser()) {
            holder.message.setBackgroundResource(R.drawable.bubble_text_right);
            lp.gravity = Gravity.RIGHT;
        } else {
            holder.message.setBackgroundResource(R.drawable.bubble_text_left);
            lp.gravity = Gravity.LEFT;
        }

        holder.message.setLayoutParams(lp);

        return convertView;
    }

    static class ViewHolder {
        TextView dateSeparator;
        ImageView picture;
        TextView name;
        TextView message;
        TextView hour;
    }
}
