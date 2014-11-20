package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ch.tarsier.tarsier.domain.model.ChatSummary;
import ch.tarsier.tarsier.R;

/**
 * @author gluthier
 */
public class ChatListAdapter extends ArrayAdapter<ChatSummary> {

    private ChatSummary[] mDiscussions;
    private int mLayoutResourceId;
    private Context mContext;

    public ChatListAdapter(Context context, int layoutResourceId, ChatSummary[] discussions) {
        super(context, layoutResourceId, discussions);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mDiscussions = discussions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChatSummaryHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new ChatSummaryHolder();
            //TODO check if row.getId() is the good id
            holder.mId = row.getId();
            holder.mAvatar = (ImageView) row.findViewById(R.id.avatar);
            holder.mNotification = (TextView) row.findViewById(R.id.notification);
            holder.mName = (TextView) row.findViewById(R.id.name);
            holder.mLastMessage = (TextView) row.findViewById(R.id.lastMessage);
            holder.mHhumanTime = (TextView) row.findViewById(R.id.humanTime);
            holder.mNbOnline = (TextView) row.findViewById(R.id.nbOnline);

            row.setTag(holder);
        } else {
            holder = (ChatSummaryHolder) row.getTag();
        }

        ChatSummary chatSummary = mDiscussions[position];
        holder.mId = chatSummary.getId();
        //TODO:
        //holder.mAvatar.setImageResource(discussionSummary.getAvatar());
        holder.mName.setText(chatSummary.getName());
        holder.mLastMessage.setText(chatSummary.getLastMessage());
        holder.mHhumanTime.setText(chatSummary.getHumanTime());

        if (Integer.parseInt(chatSummary.getNotifications()) > 9) {
            holder.mNotification.setText(R.string.too_much_notifications);
        } else if (Integer.parseInt(chatSummary.getNotifications()) > 0) {
            holder.mNotification.setText(chatSummary.getNotifications());
        } else {
            holder.mNotification.setVisibility(View.GONE);
        }

        //TODO fetch in the db the kind of discussion
        if (Integer.parseInt(chatSummary.getNbOnline()) > 1) {
            holder.mNbOnline.setText(getContext().getString(R.string.nb_people_online) + " " + chatSummary.getNbOnline());
        } else {
            holder.mNbOnline.setVisibility(View.GONE);
        }

        return row;
    }

    /**
     * DiscussionSummaryHolder is the class containing the discussion's information
     */
    private class ChatSummaryHolder {
        private int mId;
        private ImageView mAvatar;
        private TextView mNotification;
        private TextView mName;
        private TextView mLastMessage;
        private TextView mHhumanTime;
        private TextView mNbOnline;
    }

}
