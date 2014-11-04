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
 * Created by gluthier on 23.10.2014.
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

        //TODO understand that
        if (row == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new DiscussionSummaryHolder();
            holder.avatar = (ImageView)row.findViewById(R.id.avatar);
            holder.notification = (ImageView)row.findViewById(R.id.notification);
            holder.name = (TextView)row.findViewById(R.id.name);
            holder.lastMessage = (TextView)row.findViewById(R.id.lastMessage);
            holder.humanTime = (TextView)row.findViewById(R.id.humanTime);
            holder.nbOnline = (TextView)row.findViewById(R.id.nbOnline);

            row.setTag(holder);
        } else {
            holder = (DiscussionSummaryHolder) row.getTag();
        }

        DiscussionSummary discussionSummary = mDiscussions[position];
        //TODO
        //holder.avatar.setImageResource(discussionSummary.mAvatarImage);
        // holder.notification.setImageDrawable(discussionSummary.mNotifications);
        holder.name.setText(discussionSummary.mName);
        holder.lastMessage.setText(discussionSummary.mLastMessage);
        holder.humanTime.setText(discussionSummary.mHumanTime);
        holder.nbOnline.setText(discussionSummary.mNbOnline);

        return row;
    }

    private static class DiscussionSummaryHolder {
        ImageView avatar;
        ImageView notification;
        TextView name;
        TextView lastMessage;
        TextView humanTime;
        TextView nbOnline;
    }

}
