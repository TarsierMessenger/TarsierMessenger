package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;

/*
 * @author romac
 * @author gluthier
 */

public class ChatroomPeersAdapter extends ArrayAdapter<Peer> {

    private final static int LAYOUT = R.layout.row_chatroom_peer;

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
        /*
        if (convertView == null) {
            convertView = mInflater.inflate(LAYOUT, parent, false);
            convertView.setTag(R.id.name, convertView.findViewById(R.id.name));
            convertView.setTag(R.id.icon, convertView.findViewById(R.id.icon));
            convertView.setTag(R.id.online_badge, convertView.findViewById(R.id.online_badge));
            convertView.setTag(R.id.owner_badge, convertView.findViewById(R.id.owner_badge));
            convertView.setTag(R.id.status_message_profile_activity,
                    convertView.findViewById(R.id.status_message_profile_activity));
        }

        View rowView = convertView;

        TextView nameView = (TextView) convertView.getTag(R.id.name);
        TextView statusView = (TextView) convertView.getTag(R.id.status_message_profile_activity);
        ImageView imageView = (ImageView) convertView.getTag(R.id.icon);
        TextView onlineBadgeView = (TextView) convertView.getTag(R.id.online_badge);
        TextView ownerBadgeView = (TextView) convertView.getTag(R.id.owner_badge);

        Peer p = getItem(position);

        nameView.setText(p.getUserName());
        statusView.setText(p.getStatusMessage());
        imageView.setImageURI(Uri.parse(p.getPicturePath()));
        imageView.setImageResource(R.drawable.ic_launcher);
        onlineBadgeView.setVisibility((p.isOnline()) ? View.VISIBLE : View.INVISIBLE);
        ownerBadgeView.setVisibility((mChat.isHost(p)) ? View.VISIBLE : View.INVISIBLE);

        return rowView;
        */

        View row = convertView;
        PeerHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        }


        return row;
    }

    public void addAllPeers(List<Peer> peerList) {
        this.clear();
        this.addAll(peerList);
        mPeerList = peerList;
        this.setNotifyOnChange(true);
    }

    /**
     * PeerHolder is theclass containing the peer's information
     */
    private class PeerHolder {
        private ImageView mAvatarSrc;
        private TextView mName;
        private TextView mStatus;
        private TextView mBadge;
    }
}
