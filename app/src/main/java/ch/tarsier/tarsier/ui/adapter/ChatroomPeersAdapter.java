package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.Peer;

/*
 * @author romac
 * @author gluthier
 */

public class ChatroomPeersAdapter extends ArrayAdapter<Peer> {
    private Context mContext;
    private int mLayoutResourceId;
    private List<Peer> mPeerList;

    public ChatroomPeersAdapter(Context context, int layoutResourceId, List<Peer> peerList) {
        super(context, layoutResourceId, peerList);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
        mPeerList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mPeerList.size();
    }

    @Override
    public Peer getItem(int position) {
        return mPeerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mPeerList.get(position) != null) {
            return mPeerList.get(position).getId();
        }

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PeerHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new PeerHolder();

            holder.mAvatarSrc = (ImageView) row.findViewById(R.id.icon);
            holder.mName = (TextView) row.findViewById(R.id.name);
            holder.mStatus = (TextView) row.findViewById(R.id.status_message_profile_activity);
            holder.mOnlineBadgeView = (TextView) row.getTag(R.id.online_badge);
            holder.mOwnerBadgeView = (TextView) row.getTag(R.id.owner_badge);

            row.setTag(holder);
        } else {
            holder = (PeerHolder) row.getTag();
        }

        Peer peer = this.getItem(position);

        //TODO verify we get the right picture
        holder.mAvatarSrc.setImageBitmap(peer.getPicture());
        holder.mName.setText(peer.getUserName());
        holder.mStatus.setText(peer.getStatusMessage());
        holder.mOnlineBadgeView.setVisibility((peer.isOnline()) ? View.VISIBLE : View.INVISIBLE);
        //TODO mChat?
        //holder.mOwnerBadgeView.setVisibility((mChat.isHost(peer)) ? View.VISIBLE : View.INVISIBLE);

        return row;
    }

    public void addAllPeers(List<Peer> peerList) {
        this.clear();
        this.addAll(peerList);
        mPeerList = peerList;
        this.setNotifyOnChange(true);
    }

    /**
     * PeerHolder is the class containing the peer's information
     */
    private class PeerHolder {
        private ImageView mAvatarSrc;
        private TextView mName;
        private TextView mStatus;
        private TextView mOnlineBadgeView;
        private TextView mOwnerBadgeView;
    }
}
