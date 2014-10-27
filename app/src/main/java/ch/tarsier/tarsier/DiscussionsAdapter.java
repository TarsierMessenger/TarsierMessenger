package ch.tarsier.tarsier;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gluthier on 23.10.2014.
 */
public class DiscussionsAdapter extends RecyclerView.Adapter <DiscussionsAdapter.ViewHolder> {

    private List<DiscussionSummary> mDiscussions;
    private Context mContext;

    public DiscussionsAdapter(List<DiscussionSummary> discussions, Context context) {
        this.mDiscussions = discussions;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_discussion, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        DiscussionSummary discussion = mDiscussions.get(i);
        viewHolder.peopleName.setText(discussion.peopleName);
        viewHolder.lastMessage.setText(discussion.lastMessage);
    }

    @Override
    public int getItemCount() {
        return mDiscussions == null ? 0 : mDiscussions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView peopleName;
        public TextView lastMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            peopleName = (TextView) itemView.findViewById(R.id.peopleName);
            lastMessage = (TextView) itemView.findViewById(R.id.lastMessage);
        }
    }

}
