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

/**
 * @author benpac
 */
public class NearbyPeerAdapter extends ArrayAdapter<WifiP2pDevice> {

    private List<WifiP2pDevice> mPeerList;
    private Context mContext;
    private int mRowLayoutId;
    private final static String TAG = "NearbyPeerAdapter";

    public NearbyPeerAdapter(Context context, int resource) {
        super(context, resource);
        mPeerList = new ArrayList<WifiP2pDevice>();
        mContext = context;
        mRowLayoutId = resource;
        Log.d(TAG, "Nearby Peer Adapter created");
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

        //Log.d("PeerAdapter", "mContext is : " + ((Activity) mContext).getLocalClassName());

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
        peerHolder.mStatus.setText(getDeviceStatus(peerToShow.status));
        peerHolder.mProfilePicture.setImageBitmap(
            BitmapFactory.decodeResource(
                    Tarsier.app().getResources(),
                    R.drawable.tarsier_placeholder
            )
        );

        return row;
    }

    private String getDeviceStatus(int statusCode) {
        switch (statusCode) {
            case WifiP2pDevice.CONNECTED:
                return mContext.getString(R.string.device_status_connected);
            case WifiP2pDevice.INVITED:
                return mContext.getString(R.string.ifi_device_status_invited);
            case WifiP2pDevice.FAILED:
                return mContext.getString(R.string.wifi_device_status_failed);
            case WifiP2pDevice.AVAILABLE:
                return mContext.getString(R.string.wifi_device_status_available);
            case WifiP2pDevice.UNAVAILABLE:
                return mContext.getString(R.string.wifi_device_status_unavailable);
            default:
                return mContext.getString(R.string.wifi_device_status_unknown);
        }
    }

    /**
     * PeerHolder is the class wrapping the peer's informations
     */
    private class PeerHolder {
        private TextView mUsername;
        private TextView mStatus;
        private ImageView mProfilePicture;
    }
}
