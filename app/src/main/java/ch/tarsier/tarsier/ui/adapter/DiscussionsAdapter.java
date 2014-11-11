package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ch.tarsier.tarsier.domain.model.DiscussionSummary;
import ch.tarsier.tarsier.R;

/**
 * @author gluthier
 */
public class DiscussionsAdapter extends ArrayAdapter<DiscussionSummary> {

    private DiscussionSummary[] mDiscussions;
    private int mLayoutResourceId;
    private Context mContext;

    public DiscussionsAdapter(Context context, int layoutResourceId, DiscussionSummary[] discussions) {
        super(context, layoutResourceId, discussions);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mDiscussions = discussions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DiscussionSummaryHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new DiscussionSummaryHolder();
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
            holder = (DiscussionSummaryHolder) row.getTag();
        }

        DiscussionSummary discussionSummary = mDiscussions[position];
        holder.mId = discussionSummary.getId();
        //TODO:
        //holder.mAvatar.setImageResource(discussionSummary.getAvatar());
        holder.mName.setText(discussionSummary.getName());
        holder.mLastMessage.setText(discussionSummary.getLastMessage());
        holder.mHhumanTime.setText(discussionSummary.getHumanTime());

        if (Integer.parseInt(discussionSummary.getNotifications()) > 9) {
            holder.mNotification.setText(R.string.too_much_notifications);
        } else if (Integer.parseInt(discussionSummary.getNotifications()) > 0) {
            holder.mNotification.setText(discussionSummary.getNotifications());
        } else {
            holder.mNotification.setVisibility(View.GONE);
        }

        //TODO fetch in the db the kind of discussion
        if (Integer.parseInt(discussionSummary.getNbOnline()) > 1) {
            holder.mNbOnline.setText(getContext().getString(R.string.nb_people_online) + " " + discussionSummary.getNbOnline());
        } else {
            holder.mNbOnline.setVisibility(View.GONE);
        }

        return row;
    }

    /**
     * DiscussionSummaryHolder is the class containing the discussion's information
     */
    private class DiscussionSummaryHolder {
        private int mId;
        private ImageView mAvatar;
        private TextView mNotification;
        private TextView mName;
        private TextView mLastMessage;
        private TextView mHhumanTime;
        private TextView mNbOnline;
    }

}
