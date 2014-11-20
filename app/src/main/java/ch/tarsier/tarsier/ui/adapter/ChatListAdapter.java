package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.tarsier.tarsier.domain.model.ChatSummary;
import ch.tarsier.tarsier.R;

/**
 * @author gluthier
 */
public class ChatListAdapter extends ArrayAdapter<ChatSummary> {

    private List<ChatSummary> mChatSummaryList;
    private Context mContext;
    private int mLayoutResourceId;

    public ChatListAdapter(Context context, int layoutResourceId, List<ChatSummary> chatSummaryList) {
        super(context, layoutResourceId, chatSummaryList);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mChatSummaryList = chatSummaryList;
    }

    @Override
    public int getCount() {
        return mChatSummaryList.size();
    }

    @Override
    public ChatSummary getItem(int position) {
        return mChatSummaryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mChatSummaryList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChatSummaryHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new ChatSummaryHolder();

            holder.mAvatar = (ImageView) row.findViewById(R.id.avatar);
            holder.mName = (TextView) row.findViewById(R.id.name);
            holder.mLastMessage = (TextView) row.findViewById(R.id.lastMessage);
            holder.mHumanTime = (TextView) row.findViewById(R.id.humanTime);

            row.setTag(holder);
        } else {
            holder = (ChatSummaryHolder) row.getTag();
        }

        ChatSummary chatSummary = mChatSummaryList.get(position);

        holder.mId = chatSummary.getId();
        //TODO:
        //holder.mAvatar.setImageResource(discussionSummary.getAvatar());
        holder.mName.setText(chatSummary.getName());
        holder.mLastMessage.setText(chatSummary.getLastMessage());
        holder.mHumanTime.setText(chatSummary.getHumanTime());

        return row;
    }

    /**
     * DiscussionSummaryHolder is the class containing the discussion's information
     */
    private class ChatSummaryHolder {
        private long mId;
        private ImageView mAvatar;
        private TextView mName;
        private TextView mLastMessage;
        private TextView mHumanTime;
    }

}
