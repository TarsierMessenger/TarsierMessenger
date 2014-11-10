package ch.tarsier.tarsier.network.server;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.protobuf.ByteString;

import java.io.IOException;
import java.util.ArrayList;

import ch.tarsier.tarsier.network.ByteUtils;
import ch.tarsier.tarsier.network.ConversationStorageDelegate;
import ch.tarsier.tarsier.network.ConversationViewDelegate;
import ch.tarsier.tarsier.network.MessagingInterface;
import ch.tarsier.tarsier.network.Peer;
import ch.tarsier.tarsier.network.WiFiDirectDebugActivity;
import ch.tarsier.tarsier.network.networkMessages.MessageType;
import ch.tarsier.tarsier.network.networkMessages.TarsierWireProtos;

/**
 * Created by fred on 09/11/14.
 */
public class TarsierMessagingServer extends BroadcastReceiver implements  MessagingInterface,
                                                                          ConnectionInfoListener {
    private static final String TAG = "TarsierMessagingServer";
    private WifiP2pManager              mWiFiManager;
    private WifiP2pManager.Channel      mChannel;
    private ConversationStorageDelegate mConversationStorageDelegate;
    private ConversationViewDelegate    mConversationViewDelegate;
    private TarsierServerConnection mServerConnection;

    public TarsierMessagingServer(final Context context, WifiP2pManager WiFiP2pService, Looper looper, ConversationViewDelegate cvd, ConversationStorageDelegate csd) {
        mConversationStorageDelegate = csd;
        mConversationViewDelegate    = cvd;
        mWiFiManager = WiFiP2pService;
        mChannel     = mWiFiManager.initialize(context, looper, null);

        mWiFiManager.createGroup(mChannel,new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Group successfully created.");
            }

            @Override
            public void onFailure(int errorCode) {
                Toast.makeText(context, "Tarsier failed to initiate a new group", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    // ConnectionInfoListener interface method
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        if (info.isGroupOwner) {
            Log.d(TAG, "Connected as group owner");
            try {
                mServerConnection = new TarsierServerConnection(new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        switch (msg.what){
                            case MessageType.MESSAGE_TYPE_PUBLIC:
                                // See how we want to store messages
                            case MessageType.MESSAGE_TYPE_PRIVATE:
                                // See how we want to store messages
                            case MessageType.MESSAGE_TYPE_PEER_LIST:
                                mConversationViewDelegate.receivedNewPeersList(getMembersList());
                        }
                        return true;
                    }
                }));
                mServerConnection.run();
                mConversationViewDelegate.connected();
            } catch (IOException e) {
                Log.d(TAG, "Failed to create a server thread - " + e.getMessage());
                return;
            }
        } else {
            Log.e(TAG, "Server connected as client with info: "+ info.toString());
        }
    }

    @Override
    // BroadcastReceiver inherited method
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, action);
        if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if (networkInfo.isConnected()) {
                mWiFiManager.requestConnectionInfo(mChannel, this);
            } else {
                Log.d(TAG, "Did receive a Intent action : " + action);
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
                .equals(action)) {
            WifiP2pDevice device = (WifiP2pDevice) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            Log.d(WiFiDirectDebugActivity.TAG, "Device status -" + device.status);
        }
    }


    @Override
    public ArrayList<Peer> getMembersList() {
        return mServerConnection.getPeers();
    }

    @Override
    public void broadcastMessage(String message) {
        TarsierWireProtos.TarsierPublicMessage.Builder publicMessage = TarsierWireProtos.TarsierPublicMessage.newBuilder();
        publicMessage.setPlainText(ByteString.copyFrom(message.getBytes()));
        publicMessage.setSenderPublicKey(ByteString.copyFrom("TEMPLATEPUBLICKEY".getBytes())); //TODO:Get key from Storage
        byte[] wireMessage = ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PUBLIC, publicMessage.build().toByteArray());
        mServerConnection.broadcast(wireMessage);
    }

    @Override
    public void sendMessage(Peer peer, String message) {
        TarsierWireProtos.TarsierPrivateMessage.Builder privateMessage = TarsierWireProtos.TarsierPrivateMessage.newBuilder();
        privateMessage.setSenderPublicKey(ByteString.copyFrom("TEMPLATEPUBLICKEY".getBytes())); //TODO:Get key from Storage);
        privateMessage.setReceiverPublicKey(ByteString.copyFrom(peer.getPublicKey()));
        privateMessage.setCipherText(ByteString.copyFrom("THIS IS SUPERSECRET".getBytes()));
        privateMessage.setIV(ByteString.copyFrom("PRIVATEMESSAGEIV".getBytes())); //TODO: Encrypt messages

        byte[] wireMessage = ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PRIVATE, privateMessage.build().toByteArray());
        mServerConnection.sendMessage(peer, wireMessage);
    }

    @Override
    public void setConversationViewDelegate(ConversationViewDelegate delegate) {

    }

    @Override
    public void setConversationStorageDelegate(ConversationStorageDelegate delegate) {

    }
}
