package ch.tarsier.tarsier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gluthier
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
            holder.mAvatar = (ImageView) row.findViewById(R.id.avatar);
            holder.mNotification = (ImageView) row.findViewById(R.id.notification);
            holder.mName = (TextView) row.findViewById(R.id.name);
            holder.mLastMessage = (TextView) row.findViewById(R.id.lastMessage);
            holder.mHhumanTime = (TextView) row.findViewById(R.id.humanTime);
            holder.mNbOnline = (TextView) row.findViewById(R.id.nbOnline);

            row.setTag(holder);
        } else {
            holder = (DiscussionSummaryHolder) row.getTag();
        }

        DiscussionSummary discussionSummary = mDiscussions[position];
        //TODO
        //holder.mAvatar.setImageResource(discussionSummary.getAvatar());
        //holder.mNotification.setImageDrawable(discussionSummary.getNotifications());
        holder.mName.setText(discussionSummary.getName());
        holder.mLastMessage.setText(discussionSummary.getLastMessage());
        holder.mHhumanTime.setText(discussionSummary.getHumanTime());
        holder.mNbOnline.setText(discussionSummary.getNbOnline());

        return row;
    }

    /**
     * DiscussionSummaryHolder is the class containing the discussion's information
     */
    private class DiscussionSummaryHolder {
        private ImageView mAvatar;
        private ImageView mNotification;
        private TextView mName;
        private TextView mLastMessage;
        private TextView mHhumanTime;
        private TextView mNbOnline;
    }

}
