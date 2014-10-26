package ch.tarsier.tarsier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A BroadcastReceiver that notifies of important Wi-Fi p2p events.
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver implements WifiP2pManager.ConnectionInfoListener {

    public final String TAG = "BroadCastReceiver";
    /*
     * Attributes to be updated once an event has occurred
     */
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private HomeActivity mActivity;
    private WifiP2pConfig config;
    private WifiP2pManager.PeerListListener mPeerListListener;
    private WifiP2pDevice mDevice;
    Server server = null;

    //List of1 peers
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();


    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                       HomeActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
        enablePeerListListener();
    }

    public void createConnection(WifiP2pDevice device){
        mDevice = device;
        connect();
    }

    private void enablePeerListListener() {
        mPeerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peerList) {
                // Out with the old, in with the new.
                peers.clear();
                peers.addAll(peerList.getDeviceList());
                Log.i(TAG,"PeerList updated: size: "+ peers.size()+" devices: " + peers.toString());
                // If an AdapterView is backed by this data, notify it
                // of the change.  For instance, if you have a ListView of available
                // peers, trigger an update.
                //ToDo: Create the listView
                mActivity.update(peers);
                if (peers.size() == 0) {
                    Log.i(TAG, "No devices found");
                    return;
                }

            }
        };
    }

    public List<WifiP2pDevice> getPeers() {
        return peers;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "OnReceive Called");



        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            Log.i(TAG, "WIFI_P2P_STATE_CHANGED_ACTION");
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                mActivity.setIsWifiP2pEnabled(true);
            } else {
                mActivity.setIsWifiP2pEnabled(false);
            }




        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            Log.i(TAG, "WIFI_P2P_PEERS_CHANGED_ACTION");
            if (mManager != null) {
                mManager.requestPeers(mChannel, mPeerListListener);
                Log.i(TAG, "peers requested");
            }



        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
            Log.i(TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION");
            if (mManager == null) {
                return;
            }

            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {

                // We are connected with the other device, request connection
                // info to find group owner IP

                mManager.requestConnectionInfo(mChannel, this);
                Log.i(TAG, "Connection info requested.");
            }



        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
            Log.i(TAG, "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");
        }
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
        // InetAddress from WifiP2pInfo struct.


        // After the group negotiation, we can determine the group owner.
        if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
            // Do whatever tasks are specific to the group owner.
            // One common case is creating a server thread and accepting
            // incoming connections.
            if(server == null){
                Log.d(TAG, "Server created on this device");
                server = new Server();
                server.start();
            }else{
                Log.d(TAG, "This device is already the GO");
            }
        } else if (wifiP2pInfo.groupFormed) {
            // The other device acts as the client. In this case,
            // you'll want to create a client thread that connects to the group
            // owner.
            Client client = new Client(wifiP2pInfo.groupOwnerAddress);
            client.start();
            Log.d(TAG, "Client is started on this device");
        }

    }

    private void connect(){
        config = new WifiP2pConfig();
        config.deviceAddress = mDevice.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.i(TAG, "Connection initiated successfully.");
            }

            @Override
            public void onFailure(int reason) {
                Log.i(TAG, "Connection initiation failed");
                Toast.makeText(mActivity, "Connect failed. Retry.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}