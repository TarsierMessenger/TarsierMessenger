package ch.tarsier.tarsier;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gluthier on 23.10.2014.
 */
public class DiscussionsAdapter extends RecyclerView.Adapter <DiscussionsAdapter.DiscussionsHolder> {

    private List<DiscussionSummary> mDiscussions;
    private Context mContext;

    public DiscussionsAdapter(List<DiscussionSummary> discussions, Context context) {
        this.mDiscussions = discussions;
        this.mContext = context;
    }

    @Override
    public DiscussionsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_discussion, viewGroup, false);
        return new DiscussionsHolder(v);
    }

    @Override
    public void onBindViewHolder(DiscussionsHolder viewHolder, int i) {
        DiscussionSummary discussion = mDiscussions.get(i);
        viewHolder.name.setText(discussion.mName);
        viewHolder.lastMessage.setText(discussion.mLastMessage);
        viewHolder.humanTime.setText(discussion.mHumanTime);
        viewHolder.nbOnline.setText(discussion.mNbOnline);
    }

    @Override
    public int getItemCount() {
        return mDiscussions == null ? 0 : mDiscussions.size();
    }

    public static class DiscussionsHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        ImageView notification;
        TextView name;
        TextView lastMessage;
        TextView humanTime;
        TextView nbOnline;

        public DiscussionsHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            notification = (ImageView) itemView.findViewById(R.id.notification);
            name = (TextView) itemView.findViewById(R.id.name);
            lastMessage = (TextView) itemView.findViewById(R.id.lastMessage);
            humanTime = (TextView) itemView.findViewById(R.id.humanTime);
            nbOnline = (TextView) itemView.findViewById(R.id.nbOnline);
        }
    }

}
