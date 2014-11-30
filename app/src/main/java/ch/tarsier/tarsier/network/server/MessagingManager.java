package ch.tarsier.tarsier.network.server;

import com.google.protobuf.InvalidProtocolBufferException;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

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
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.event.ConnectedEvent;
import ch.tarsier.tarsier.event.ReceivedChatroomPeersListEvent;
import ch.tarsier.tarsier.event.ReceivedMessageEvent;
import ch.tarsier.tarsier.event.ReceivedNearbyPeersListEvent;
import ch.tarsier.tarsier.event.RequestNearbyPeersListEvent;
import ch.tarsier.tarsier.exception.DomainException;
import ch.tarsier.tarsier.network.client.ClientConnection;
import ch.tarsier.tarsier.network.ConnectionInterface;
import ch.tarsier.tarsier.network.MessageHandler;
import ch.tarsier.tarsier.network.MessagingInterface;
import ch.tarsier.tarsier.ui.activity.WiFiDirectDebugActivity;
import ch.tarsier.tarsier.network.messages.MessageType;

import static ch.tarsier.tarsier.network.messages.TarsierWireProtos.TarsierPublicMessage;

/**
 * @author FredericJacobs
 */
public class MessagingManager extends BroadcastReceiver implements MessagingInterface,
        ConnectionInfoListener,
        Handler.Callback,
        MessageHandler {

    private static final String NETWORK_LAYER_TAG = "TarsierMessagingManager";

    private static final String WIFI_DIRECT_TAG = "WiFiDirect";

    private WifiP2pManager mManager;

    private WifiP2pManager.Channel mChannel;

    private ConnectionInterface mConnection;

    private WifiP2pManager.PeerListListener peerListListener;

    private final ArrayList<WifiP2pDevice> mPeers = new ArrayList<WifiP2pDevice>();

    private Handler mHandler = new Handler(this);

    private Runnable mConnectionHandler;

    private Bus mEventBus;

    public MessagingManager(WifiP2pManager wifiManager, WifiP2pManager.Channel channel) {

        mConnectionHandler = null;
        mManager = wifiManager;
        mChannel = channel;

        createPeerListener();
        initiatePeerDiscovery();

        mManager.requestPeers(mChannel, peerListListener);
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo p2pInfo) {
        // The group owner accepts connections using a server socket and then spawns a
        // client socket for every client.
        Log.d(WIFI_DIRECT_TAG, "onConnectionInfoAvailable");

        if (mConnectionHandler == null) {
            if (p2pInfo.isGroupOwner) {
                Log.d(WIFI_DIRECT_TAG, "Connected as group owner");

                try {
                    mConnectionHandler = new ServerConnection(getConnectionHandler());
                } catch (IOException e) {
                    Log.d(WIFI_DIRECT_TAG, "Failed to create a server thread - " + e.getMessage());
                    return;
                }

                mEventBus.post(new ConnectedEvent(true));

            } else {
                Log.d(WIFI_DIRECT_TAG, "Connected as peer");

                mConnectionHandler = new ClientConnection(
                    getConnectionHandler(),
                    p2pInfo.groupOwnerAddress);

                mEventBus.post(new ConnectedEvent(false));
            }
        }
        mConnection = (ConnectionInterface) mConnectionHandler;
    }

    @Override
    // BroadcastReceiver inherited method
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.d(WiFiDirectDebugActivity.TAG, action);

        if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Log.d(WIFI_DIRECT_TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION");

            if (mManager == null) {
                Log.e(WIFI_DIRECT_TAG, "Fatal error! mManager does not exist");
                return;
            }

            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {
                // we are connected with the other device, request connection
                // info to find group owner IP
                Log.d(WiFiDirectDebugActivity.TAG,
                      "Connected to p2p network. Requesting network details");

                mManager.requestConnectionInfo(mChannel, this);
            } else {
                Log.d(WIFI_DIRECT_TAG, "Did receive a Intent action : " + action);
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

            WifiP2pDevice device = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);

            Log.d(WiFiDirectDebugActivity.TAG, "Device status -" + device.status);

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            if (mManager != null) {
                mManager.requestPeers(mChannel, peerListListener);
            }

            Log.d(WIFI_DIRECT_TAG, "P2P peers changed");
        }
    }

    private void createPeerListener() {

        peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public synchronized void onPeersAvailable(WifiP2pDeviceList peersList) {
                mPeers.clear();
                mPeers.addAll(peersList.getDeviceList());

                // If an AdapterView is backed by this data, notify it
                // of the change.  For instance, if you have a ListView of available
                // peers, trigger an update.

                Log.d(WIFI_DIRECT_TAG, "Peer list updated: " + mPeers.toString());

                mEventBus.post(new ReceivedChatroomPeersListEvent(getPeersList()));

                if (mPeers.size() == 0) {
                    Log.d(WIFI_DIRECT_TAG, "No devices found");
                }
            }
        };
    }

    private void initiatePeerDiscovery() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(WIFI_DIRECT_TAG, "Peer discovery initiation succeeded");
            }

            @Override
            public void onFailure(int reasonCode) {
                if (reasonCode == WifiP2pManager.P2P_UNSUPPORTED) {
                    Log.d(WIFI_DIRECT_TAG,
                            "Peer discovery initiation failed. P2P isn't supported on this device.");
                } else if (reasonCode == WifiP2pManager.BUSY) {
                    Log.d(WIFI_DIRECT_TAG,
                            "Peer discovery initiation failed. The system is too busy to process the request.");
                } else if (reasonCode == WifiP2pManager.ERROR) {
                    Log.d(WIFI_DIRECT_TAG,
                            "Peer discovery initiation failed. The operation failed due to an internal error.");
                }
            }
        });
    }

    @Override
    public List<Peer> getPeersList() {
        if (mConnection == null) {
            //FIXME Testing to show something on NearbyListActivity.
            Log.d("Connection","mConnection is null");
            List<Peer> peerList = new ArrayList<Peer>();
            peerList.add(new Peer("ben", "lalala"));
            return peerList;
        }

        return mConnection.getPeersList();
    }

    @Subscribe
    public void onRecievedRequestListPeer(RequestNearbyPeersListEvent event) {
        Log.d("RequestList","List is requested");
        if (mEventBus != null) {
            mEventBus.post(new ReceivedNearbyPeersListEvent(getPeersList()));

        }
    }

    @Override
    public void broadcastMessage(String message) {
        mConnection.broadcastMessage(message.getBytes());
    }

    @Override
    public void sendMessage(Peer peer, String message) {
        mConnection.sendMessage(peer, message.getBytes());
    }

    @Override
    public void setEventBus(Bus eventBus) {
        if (eventBus == null) {
            throw new IllegalArgumentException("Event bus cannot be null");
        }

        mEventBus = eventBus;
        mEventBus.register(this);
    }

    @Override
    public boolean handleMessage(Message message) {
        Log.d(NETWORK_LAYER_TAG, "handleMessage called");

        switch (message.what) {
            case MessageType.MESSAGE_TYPE_HELLO:
                Log.d(NETWORK_LAYER_TAG, "MESSAGE_TYPE_HELLO received.");
                break;
            case MessageType.MESSAGE_TYPE_PEER_LIST:
                Log.d(NETWORK_LAYER_TAG, "MESSAGE_TYPE_PEER_LIST received.");
                break;
            case MessageType.MESSAGE_TYPE_PRIVATE:
                Log.d(NETWORK_LAYER_TAG, "MESSAGE_TYPE_PRIVATE received.");
                break;
            case MessageType.MESSAGE_TYPE_PUBLIC:
                Log.d(NETWORK_LAYER_TAG, "MESSAGE_TYPE_PUBLIC received.");

                try {
                    TarsierPublicMessage msg = TarsierPublicMessage.parseFrom((byte[]) message.obj);

                    byte[] publicKey = msg.getSenderPublicKey().toByteArray();
                    String contents = msg.getPlainText().toString();

                    Peer sender = Tarsier.app().getPeerRepository().findByPublicKey(publicKey);
                    mEventBus.post(new ReceivedMessageEvent(contents, sender));

                } catch (InvalidProtocolBufferException e) {
                     e.printStackTrace();
                } catch (DomainException e) {
                    Log.d(NETWORK_LAYER_TAG, "Could not find peer in database for received message.");
                }

                break;

            default:
                Log.d(NETWORK_LAYER_TAG, "Unknown message type");
        }
        return true;
    }

    @Override
    public Handler getConnectionHandler() {
        return mHandler;
    }
}
