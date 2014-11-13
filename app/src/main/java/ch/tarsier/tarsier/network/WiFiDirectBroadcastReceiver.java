package ch.tarsier.tarsier.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import ch.tarsier.tarsier.network.server.TarsierServerConnection;


/**
 * A BroadcastReceiver that notifies of important Wi-Fi p2p events.
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver  {

    public final String TAG = "BroadCastReceiver";
    /*
     * Attributes to be updated once an event has occurred
     */
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private WifiP2pManager.PeerListListener peerListListener;

    private WiFiDirectDebugActivity mActivity;
    TarsierServerConnection server = null;


    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                       WiFiDirectDebugActivity activity,WifiP2pManager.PeerListListener peerListListener) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
        this.peerListListener = peerListListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(WiFiDirectDebugActivity.TAG, action);
        if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if (mManager == null) {
                // TODO: (AMIREZZA) WHEN DOES THIS HAPPEN?
                return;
            }
            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if (networkInfo.isConnected()) {
                // we are connected with the other device, request connection
                // info to find group owner IP
                Log.d(WiFiDirectDebugActivity.TAG,
                        "Connected to p2p network. Requesting network details");
                mManager.requestConnectionInfo(mChannel,
                        (WifiP2pManager.ConnectionInfoListener) mActivity);
            } else {
                Log.d(TAG, "Did receive a Intent action : " + action);
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
                .equals(action)) {
            WifiP2pDevice device = (WifiP2pDevice) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            Log.d(WiFiDirectDebugActivity.TAG, "Device status -" + device.status);
        } else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){
            // Request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            if (mManager != null) {
                mManager.requestPeers(mChannel, peerListListener);
            }
            Log.d(TAG, "P2P peers changed");

        }
    }
}