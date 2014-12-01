package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
    
import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author benpac
 */
public class PeerAdapter extends ArrayAdapter<WifiP2pDevice> {

    private List<WifiP2pDevice> mPeerList;
    private Context mContext;
    private int mRowLayoutId;

    public PeerAdapter(Context context, int resource) {
        super(context, resource);
        mPeerList = new ArrayList<WifiP2pDevice>();
        mContext = context;
        mRowLayoutId = resource;

    }

    @Override
    public void clear() {
        mPeerList.clear();
    }

    public void setPeerList(List<WifiP2pDevice> newListPeer) {
        mPeerList = newListPeer;
    }

    @Override
    public WifiP2pDevice getItem(int position) {
        return mPeerList.get(position);
    }

    @Override
    public int getCount() {
        return mPeerList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PeerHolder peerHolder = null;

        Log.d("PeerAdapter", "mContext is : " + ((Activity) mContext).getLocalClassName());
        if (row == null) {
            //create row
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mRowLayoutId, parent, false);
            peerHolder = new PeerHolder();

            peerHolder.mProfilePicture = (ImageView) row.findViewById(R.id.nearbyPeerPicture);
            peerHolder.mStatus = (TextView) row.findViewById(R.id.nearbyPeerStatus);
            peerHolder.mUsername = (TextView) row.findViewById(R.id.nearbyPeerName);

            row.setTag(peerHolder);

        } else {
            // recover the information
            peerHolder = (PeerHolder) row.getTag();
        }
        WifiP2pDevice peerToShow = getItem(position);
        peerHolder.mUsername.setText(peerToShow.deviceName);
        peerHolder.mStatus.setText(peerToShow.deviceAddress);
        peerHolder.mProfilePicture.setImageBitmap(
            BitmapFactory.decodeResource(
                    Tarsier.app().getResources(),
                    R.drawable.tarsier_placeholder
            )
        );

        return row;
    }



    private class PeerHolder {
        TextView mUsername;
        TextView mStatus;
        ImageView mProfilePicture;
    }
}
