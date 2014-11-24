package ch.tarsier.tarsier.network.client;

import com.google.protobuf.ByteString;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.network.ByteUtils;
import ch.tarsier.tarsier.network.ConnectionInterface;
import ch.tarsier.tarsier.network.ChatStorageDelegate;
import ch.tarsier.tarsier.network.ChatViewDelegate;
import ch.tarsier.tarsier.network.messages.MessageType;
import ch.tarsier.tarsier.network.messages.TarsierWireProtos;

/**
 * @author amirreza
 */
public class TarsierClientConnection implements Runnable, ConnectionInterface {


    private static final String TAG = "TarsierClientConnection";

    private static final int CURRENT_MAX_MESSAGE_SIZE = 2048;

    private static final int TIMEOUT_SOCKET_LIMIT = 5000;

    private Socket mSocket;

    private Handler handler;

    private LinkedHashMap<String, Peer> mPeersMap = new LinkedHashMap<String, Peer>();

    // TODO: Fetch from storage;
    private Peer localPeer = new Peer("Client name", new PublicKey("ClientPublicKey".getBytes()));

    private InputStream in;

    private OutputStream out;

    private InetAddress mAddress;

    private ChatViewDelegate mChatViewDelegate;

    private ChatStorageDelegate mChatStorageDelegate;

    public TarsierClientConnection(Handler handler, InetAddress groupOwnerAddress) {
        this.handler = handler;
        this.mAddress = groupOwnerAddress;
        Log.d(TAG, "Client is created successfully.");
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        mSocket = new Socket();
        try {
            mSocket.bind(null);
            mSocket.connect(new InetSocketAddress(mAddress.getHostAddress(),
                    MessageType.SERVER_SOCKET), TIMEOUT_SOCKET_LIMIT);

            in = mSocket.getInputStream();
            out = mSocket.getOutputStream();
            int bytes;
            byte[] buffer = new byte[CURRENT_MAX_MESSAGE_SIZE];

            //Send Hello message
            sendHelloMessage();

            while (true) {
                // Read from the InputStream
                bytes = in.read(buffer);
                if (bytes == -1) {
                    break;
                }

                Log.v(TAG, "Read some bytes");
                if (bytes >= CURRENT_MAX_MESSAGE_SIZE) {
                    //TODO: Fix message max size
                    Log.e(TAG, "Tarsier doesn't support those messages yet");
                    mSocket.close();
                }

                byte[] typeAndMessage = ByteUtils.split(buffer, bytes, 0)[0];
                byte[] serializedProtoBuffer = ByteUtils
                        .split(typeAndMessage, 1, typeAndMessage.length - 1)[1];

                switch (MessageType.messageTypeFromData(buffer)) {

                    case MessageType.MESSAGE_TYPE_HELLO:
                        Log.e(TAG, "Hello message shouldn't be received by a client.");
                        break;
                    case MessageType.MESSAGE_TYPE_PEER_LIST:
                        TarsierWireProtos.PeerUpdatedList peerList;
                        peerList = TarsierWireProtos.PeerUpdatedList
                                .parseFrom(serializedProtoBuffer);
                        List<TarsierWireProtos.Peer> peers = peerList.getPeerList();
                        updatePeers(peers);
                        Log.d(TAG, "Peer list message handled and peer list is updated.");
                        break;
                    case MessageType.MESSAGE_TYPE_PRIVATE:
                        TarsierWireProtos.TarsierPrivateMessage privateMessage;
                        privateMessage = TarsierWireProtos.TarsierPrivateMessage
                                .parseFrom(serializedProtoBuffer);
                        if (isLocalPeer(privateMessage.getReceiverPublicKey().toByteArray())) {
                            handler.obtainMessage(MessageType.messageTypeFromData(buffer),
                                    serializedProtoBuffer).sendToTarget();
                        } else {
                            Log.e(TAG, "A private message for another peer is received.");
                        }
                        break;
                    case MessageType.MESSAGE_TYPE_PUBLIC:
                        handler.obtainMessage(MessageType.messageTypeFromData(buffer),
                                serializedProtoBuffer).sendToTarget();
                        Log.d(TAG, "Public message handled to handler");
                        break;

                    default:
                        Log.d(TAG, "Unknown message type");
                        break;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private synchronized void write(byte[] buffer) {
        try {
            if (buffer.length < CURRENT_MAX_MESSAGE_SIZE) {
                out.write(buffer);
            } else {
                Log.d(TAG, "buffer.length > CURRENT_MAX_MESSAGE_SIZE : " + buffer.length);
            }
        } catch (IOException e) {
            handler.sendEmptyMessage(MessageType.MESSAGE_TYPE_DISCONNECT);
            Log.e(TAG, "Exception during write", e);
        }
    }

    @Override
    public void broadcastMessage(byte[] publicKey, byte[] message) {
        TarsierWireProtos.TarsierPublicMessage.Builder publicMessage
                = TarsierWireProtos.TarsierPublicMessage.newBuilder();
        publicMessage.setSenderPublicKey(ByteString.copyFrom(localPeer.getPublicKey().getBytes()));
        publicMessage.setPlainText(ByteString.copyFrom(message));
        write(ByteUtils
                .prependInt(MessageType.MESSAGE_TYPE_PUBLIC, publicMessage.build().toByteArray()));
        Log.d(TAG, "A public message is sent.");
    }

    @Override
    public void sendMessage(Peer peer, byte[] message) {
        sendMessage(peer.getPublicKey(), message);
    }

    private void sendMessage(PublicKey publicKey, byte[] message) {
        TarsierWireProtos.TarsierPrivateMessage.Builder privateMessage
                = TarsierWireProtos.TarsierPrivateMessage.newBuilder();
        privateMessage.setReceiverPublicKey(ByteString.copyFrom(publicKey.getBytes()));
        privateMessage.setSenderPublicKey(ByteString.copyFrom(localPeer.getPublicKey().getBytes()));
        // TODO: is the msg already encrypted? what is setIV ?
        privateMessage.setCipherText(ByteString.copyFrom(message));
        write(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PRIVATE,
                privateMessage.build().toByteArray()));
        Log.d(TAG, "A private message is sent to " + peerWithPublicKey(publicKey.getBytes())
                .getUserName());
    }

    @Override
    public void setChatViewDelegate(ChatViewDelegate delegate) {
        mChatViewDelegate = delegate;
    }

    @Override
    public void setChatStorageDelegate(ChatStorageDelegate delegate) {
        mChatStorageDelegate = delegate;
    }

    @Override
    public List<Peer> getMembersList() {
        Set<String> peerKeySet = mPeersMap.keySet();
        ArrayList<Peer> membersList = new ArrayList<Peer>();
        for (String key : peerKeySet) {
            Peer peer = mPeersMap.get(key);
            membersList.add(peer);
        }

        return membersList;
    }

    private Peer peerWithPublicKey(byte[] publicKey) {
        return mPeersMap.get(publicKey);
    }

    private boolean isLocalPeer(byte[] publicKey) {
        return localPeer.getPublicKey().equals(new PublicKey(publicKey));
    }

    private void updatePeers(List<TarsierWireProtos.Peer> peers) {
        mPeersMap.clear();
        for (TarsierWireProtos.Peer peer : peers) {
            mPeersMap.put(new String(peer.getPublicKey().toByteArray()),
                    new Peer(peer.getName(), new PublicKey(peer.getPublicKey().toByteArray())));
        }
        Log.d(TAG, "Peer list updated");
    }

    private void sendHelloMessage() {
        TarsierWireProtos.HelloMessage.Builder helloMessage = TarsierWireProtos.HelloMessage
                .newBuilder();

        TarsierWireProtos.Peer.Builder peer = TarsierWireProtos.Peer.newBuilder();
        peer.setName(localPeer.getUserName());
        peer.setPublicKey(ByteString.copyFrom(localPeer.getPublicKey().getBytes()));

        helloMessage.setPeer(peer.build());

        write(ByteUtils
                .prependInt(MessageType.MESSAGE_TYPE_HELLO, helloMessage.build().toByteArray()));
        Log.d(TAG, "Hello message sent successfully");
    }

}

