//package ch.tarsier.tarsier;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.net.wifi.WpsInfo;
//import android.net.wifi.p2p.WifiP2pConfig;
//import android.net.wifi.p2p.WifiP2pDevice;
//import android.net.wifi.p2p.WifiP2pInfo;
//import android.net.wifi.p2p.WifiP2pManager;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.net.InetAddress;
//
//
///**
// * Created by amirreza on 10/25/14.
// */
//public class ConnectionListener implements WifiP2pManager.ConnectionInfoListener{
//
//    public final String TAG = "ConnectionListener";
//    private Activity mActivity;
//    private WifiP2pDevice mDevice;
//    private WifiP2pConfig config;
//    private WifiP2pManager mManager;
//    private WifiP2pManager.Channel mChannel;
//    private BroadcastReceiver mReceiver;
//    Server server = null;
//
//    public ConnectionListener(Activity activity, WifiP2pDevice device, WifiP2pManager manager, WifiP2pManager.Channel channel, WiFiDirectBroadcastReceiver receiver){
//        mActivity = activity;
//        mDevice = device;
//        config = new WifiP2pConfig();
//        mManager = manager;
//        mChannel = channel;
//        mReceiver = receiver;
//        connect();
//    }
//
//    private void connect(){
//        config.deviceAddress = mDevice.deviceAddress;
//        config.wps.setup = WpsInfo.PBC;
//
//        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
//
//            @Override
//            public void onSuccess() {
//                Log.i(TAG, "Connection initiated successfully.");
//            }
//
//            @Override
//            public void onFailure(int reason) {
//                Log.i(TAG, "Connection initiation failed");
//                Toast.makeText(mActivity, "Connect failed. Retry.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
//        // InetAddress from WifiP2pInfo struct.
//
//
//        // After the group negotiation, we can determine the group owner.
//        if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
//            // Do whatever tasks are specific to the group owner.
//            // One common case is creating a server thread and accepting
//            // incoming connections.
//            if(server == null){
//                Log.d(TAG, "Server created on this device");
//                server = new Server();
//                server.start();
//            }else{
//                Log.d(TAG, "This device is already the GO");
//            }
//        } else if (wifiP2pInfo.groupFormed) {
//            // The other device acts as the client. In this case,
//            // you'll want to create a client thread that connects to the group
//            // owner.
//            Client client = new Client(wifiP2pInfo.groupOwnerAddress);
//            client.start();
//            Log.d(TAG, "Client is started on this device");
//        }
//
//    }
//}
