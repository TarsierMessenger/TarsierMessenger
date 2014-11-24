package ch.tarsier.tarsier.network.server;

import com.google.protobuf.InvalidProtocolBufferException;

import com.squareup.otto.Bus;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.ui.fragment.ChatroomFragment;
import ch.tarsier.tarsier.network.ConnectionInterface;
import ch.tarsier.tarsier.network.MessageHandler;
import ch.tarsier.tarsier.network.MessagingInterface;
import ch.tarsier.tarsier.ui.activity.WiFiDirectDebugActivity;
import ch.tarsier.tarsier.ui.fragment.WiFiDirectGroupList;
import ch.tarsier.tarsier.network.client.TarsierClientConnection;
import ch.tarsier.tarsier.network.messages.MessageType;
import ch.tarsier.tarsier.network.messages.TarsierWireProtos;

/**
 * @author FredericJacobs
 */
public class TarsierMessagingManager extends BroadcastReceiver implements MessagingInterface,
        ConnectionInfoListener,
        Handler.Callback,
        MessageHandler {

    private static final String NetworkLayerTAG = "TarsierMessagingManager";

    private static final String WiFiDirectTag = "WiFiDirect";

    private WifiP2pManager mManager;

    private WifiP2pManager.Channel mChannel;

    private ConnectionInterface mConnection;

    private WifiP2pManager.PeerListListener peerListListener;

    private final ArrayList<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();

    private Handler mHandler = new Handler(this);

    private Runnable handler;

    private ChatroomFragment mChatroomFragment;

    private Activity mActivity;

    private Bus mEventBus;

    public TarsierMessagingManager(final Activity activity, WifiP2pManager wifiManager,
            WifiP2pManager.Channel channel, Looper looper) {

        handler = null;
        mActivity = activity;
        mManager = wifiManager;
        mChannel = channel;
        createPeerListener();
        initiatePeerDiscovery();
        mManager.requestPeers(mChannel, peerListListener);

    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo p2pInfo) {
        /*
        * The group owner accepts connections using a server socket and then spawns a
        * client socket for every client.
        */
        Log.d(WiFiDirectTag, "onConnectionInfoAvail   ");
        if (handler == null) {
            if (p2pInfo.isGroupOwner) {
                Log.d(WiFiDirectTag, "Connected as group owner");
                try {

                    handler = new TarsierServerConnection(((MessageHandler) this).getHandler());


                } catch (IOException e) {
                    Log.d(WiFiDirectTag,
                            "Failed to create a server thread - " + e.getMessage());
                    return;
                }
            } else {
                Log.d(WiFiDirectTag, "Connected as peer");
                handler = new TarsierClientConnection(
                        ((MessageHandler) this).getHandler(),
                        p2pInfo.groupOwnerAddress);

            }
        }
        mConnection = (ConnectionInterface) handler;
        mChatroomFragment = new ChatroomFragment();
        mChatroomFragment.setMessengerDelegate(this);
        mActivity.getFragmentManager().beginTransaction()
                .replace(R.id.container, mChatroomFragment).commit();

    }

    @Override
    // BroadcastReceiver inherited method
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(WiFiDirectDebugActivity.TAG, action);
        if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            Log.d(WiFiDirectTag, "WIFI_P2P_CONNECTION_CHANGED_ACTION");
            if (mManager == null) {
                Log.e(WiFiDirectTag, "Fatal error! mManager does not exist");
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
                        this);
            } else {
                Log.d(WiFiDirectTag, "Did receive a Intent action : " + action);
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
                .equals(action)) {
            WifiP2pDevice device = (WifiP2pDevice) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            Log.d(WiFiDirectDebugActivity.TAG, "Device status -" + device.status);
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            if (mManager != null) {
                mManager.requestPeers(mChannel, peerListListener);
            }
            Log.d(WiFiDirectTag, "P2P peers changed");

        }
    }

    private void createPeerListener() {

        peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public synchronized void onPeersAvailable(WifiP2pDeviceList peersList) {
                peers.clear();
                peers.addAll(peersList.getDeviceList());

                // If an AdapterView is backed by this data, notify it
                // of the change.  For instance, if you have a ListView of available
                // peers, trigger an update.
                WiFiDirectGroupList fragment = (WiFiDirectGroupList) mActivity.getFragmentManager()
                        .findFragmentByTag("groups");
                if (fragment != null) {
                    WiFiDirectGroupList.WiFiDevicesAdapter adapter =
                            (WiFiDirectGroupList.WiFiDevicesAdapter) fragment.getListAdapter();

                    adapter.clear();
                    adapter.addAll(peers);
                    //TODO: Why it's not updated each time but only first time ?
                    adapter.notifyDataSetChanged();
                    Log.d(WiFiDirectTag, "Peer list updated: " + peers.toString());
                    if (peers.size() == 0) {
                        Log.d(WiFiDirectTag, "No devices found");
                    }
                }
            }
        };
    }

    private void initiatePeerDiscovery() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(WiFiDirectTag, "Peer discovery initiation succeeded");
            }

            @Override
            public void onFailure(int reasonCode) {
                if (reasonCode == WifiP2pManager.P2P_UNSUPPORTED) {
                    Log.d(WiFiDirectTag,
                            "Peer discovery initiation failed. P2P isn't supported on this device.");
                } else if (reasonCode == WifiP2pManager.BUSY) {
                    Log.d(WiFiDirectTag,
                            "Peer discovery initiation failed. The system is too busy to process the request.");
                } else if (reasonCode == WifiP2pManager.ERROR) {
                    Log.d(WiFiDirectTag,
                            "Peer discovery initiation failed. The operation failed due to an internal error.");
                }
            }
        });


    }

    @Override
    public List<Peer> getMembersList() {
        return mConnection.getMembersList();
    }

    @Override
    public void broadcastMessage(String message) {
        mConnection.broadcastMessage("MytemplatePublicKey".getBytes(), message.getBytes());
    }

    @Override
    public void sendMessage(Peer peer, String message) {
        mConnection.sendMessage(peer, message.getBytes());
    }

    @Override
    public void setEventBus(Bus eventBus) {
        mEventBus = eventBus;
    }

    @Override
    public boolean handleMessage(Message message) {
        Log.d(NetworkLayerTAG, "handleMessage called");
        switch (message.what) {
            case MessageType.MESSAGE_TYPE_HELLO:
                Log.d(NetworkLayerTAG, "MESSAGE_TYPE_HELLO received.");
                break;
            case MessageType.MESSAGE_TYPE_PEER_LIST:
                Log.d(NetworkLayerTAG, "MESSAGE_TYPE_PEER_LIST received.");
                break;
            case MessageType.MESSAGE_TYPE_PRIVATE:
                Log.d(NetworkLayerTAG, "MESSAGE_TYPE_PRIVATE received.");
                break;
            case MessageType.MESSAGE_TYPE_PUBLIC:
                Log.d(NetworkLayerTAG, "MESSAGE_TYPE_PUBLIC received.");
                TarsierWireProtos.TarsierPublicMessage publicMessage;
                try {
                    publicMessage = TarsierWireProtos.TarsierPublicMessage
                            .parseFrom((byte[]) message.obj);
                    mChatroomFragment.pushMessage(
                            "Buddy: " + new String(publicMessage.getPlainText().toByteArray()));
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }

                break;

            default:
                Log.d(NetworkLayerTAG, "Unknown message type");
        }
        return true;
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }
}
