package ch.tarsier.tarsier.network.server;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.User;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.util.ByteUtils;
import ch.tarsier.tarsier.network.ConnectionInterface;
import ch.tarsier.tarsier.network.messages.MessageType;
import ch.tarsier.tarsier.network.messages.TarsierWireProtos;

/**
 * @author FredericJacobs
 * @author amirezza
 */
public class ServerConnection implements Runnable, ConnectionInterface {

    private static final String TAG = "TarsierServerConnection";

    private LinkedHashMap<String, ConnectionHandler> mConnectionMap
            = new LinkedHashMap<String, ConnectionHandler>();

    private LinkedHashMap<String, Peer> mPeersMap = new LinkedHashMap<String, Peer>();

    private User mLocalUser = Tarsier.app().getUserRepository().getUser();

    private ServerSocket mServer;

    private Handler mHandler;

    public ServerConnection(Handler handler) throws IOException {
        this.mServer = new ServerSocket(MessageType.SERVER_SOCKET);
        this.mHandler = handler;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = mServer.accept();
                new ConnectionHandler(this, socket, mHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    protected void addPeer(TarsierWireProtos.Peer peer, ConnectionHandler handler) {
        mConnectionMap.put(new String(peer.getPublicKey().toByteArray()), handler);
        mPeersMap.put(new String(peer.getPublicKey().toByteArray()),
                new Peer(peer.getName(), new PublicKey(peer.getPublicKey().toByteArray())));

        Log.d(TAG, "Peer " + peer.getName() + " added with key " + new String(
                peer.getPublicKey().toByteArray()));
    }

    protected void peerDisconnected(TarsierWireProtos.Peer peer) {
        mConnectionMap.remove(new String(peer.getPublicKey().toByteArray()));
        mPeersMap.remove(peer.getPublicKey().toByteArray());
        Log.d(TAG, "Peer " + peer.getName() + " is out: " + mPeersMap.values().toString());
    }

    public void broadcastMessage(byte[] message) {
        broadcastMessage(mLocalUser.getPublicKey().getBytes(), message);
    }

    //This sends all public messages.
    @Override
    public void broadcastMessage(byte[] publicKey, byte[] message) {
        for (Peer peer : getPeersList()) {
            if (!peer.getPublicKey().equals(new PublicKey(publicKey))) {
                ConnectionHandler connection = mConnectionMap.get(peer.getPublicKey().toString());

                TarsierWireProtos.TarsierPublicMessage.Builder publicMessage
                        = TarsierWireProtos.TarsierPublicMessage.newBuilder();

                publicMessage.setSenderPublicKey(ByteString.copyFrom(publicKey));
                publicMessage.setPlainText(ByteString.copyFrom(message));

                connection.write(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PUBLIC,
                        publicMessage.build().toByteArray()));
            }
        }

        Log.d(TAG, "A public message is sent to all peers.");
    }

    protected void broadcastUpdatedPeerList() {
        TarsierWireProtos.PeerUpdatedList.Builder peerList = TarsierWireProtos.PeerUpdatedList
                .newBuilder();

        for (Peer aPeer : getPeersList()) {
            TarsierWireProtos.Peer.Builder tarsierPeer = TarsierWireProtos.Peer.newBuilder();
            tarsierPeer.setPublicKey(ByteString.copyFrom(aPeer.getPublicKey().getBytes()));
            tarsierPeer.setName(aPeer.getUserName());
            peerList.addPeer(tarsierPeer.build());
        }

        for (Peer peer : getPeersList()) {
            ConnectionHandler connection = mConnectionMap.get(peer.getPublicKey().toString());
            connection.write(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PEER_LIST,
                    peerList.build().toByteArray()));
        }

        Log.d(TAG, "Updated peerList is broadcast.");
    }

    public void sendMessage(Peer peer, byte[] message) {
        sendMessage(peer.getPublicKey().getBytes(), message);
    }

    protected void sendMessage(byte[] publicKey, byte[] message) {
        ConnectionHandler connection = mConnectionMap.get(new String(publicKey));
        if (connection != null) {
            TarsierWireProtos.TarsierPrivateMessage.Builder privateMessage
                    = TarsierWireProtos.TarsierPrivateMessage.newBuilder();
            privateMessage.setReceiverPublicKey(ByteString.copyFrom(publicKey));
            privateMessage
                    .setSenderPublicKey(ByteString.copyFrom(mLocalUser.getPublicKey().getBytes()));
            //TODO: is the msg already encrypted? what is setIV ?
            privateMessage.setCipherText(ByteString.copyFrom(message));
            connection.write(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PRIVATE,
                    privateMessage.build().toByteArray()));
            Log.d(TAG,
                    "A private message is sent to " + peerWithPublicKey(publicKey).getUserName());
            connection.write(message);
        } else {
            Log.e(TAG, "Sadly there is no peer for that public key");
        }
    }

    protected Peer peerWithPublicKey(byte[] publicKey) {
        return mPeersMap.get(publicKey);
    }

    protected boolean isLocalPeer(byte[] publicKey) {
        return mLocalUser.getPublicKey().equals(new PublicKey(publicKey));
    }

    public List<Peer> getPeersList() {
        Set<String> peerKeySet = mPeersMap.keySet();
        ArrayList<Peer> membersList = new ArrayList<Peer>();
        for (String key : peerKeySet) {

            Peer peer = mPeersMap.get(key);
            Log.d(TAG, "getMember list : " + peer.getUserName() + peer.getPublicKey().toString());
            membersList.add(peer);
        }

        return membersList;
    }

    class ConnectionHandler implements Runnable {

        private static final String TAG = "ConnectionHandler";

        private Socket mSocket;

        private Handler handler;

        private OutputStream mOutputStream;

        private ServerConnection serverConnection;

        private TarsierWireProtos.Peer peer;

        private static final int CURRENT_MAX_MESSAGE_SIZE = 2048;

        public ConnectionHandler(ServerConnection connection, Socket socket,
                Handler handler) {
            Log.d(TAG, "ConnectionHandler is created");
            this.serverConnection = connection;
            this.mSocket = socket;
            this.handler = handler;
            Thread t = new Thread(this);
            t.start();
        }

        public void run() {
            try {
                while (true) {
                    try {
                        InputStream mInputStream = mSocket.getInputStream();
                        mOutputStream = mSocket.getOutputStream();
                        int bytes;
                        byte[] buffer = new byte[CURRENT_MAX_MESSAGE_SIZE];
                        while (true) {
                            bytes = mInputStream.read(buffer);
                            if (bytes == -1) {
                                if (peer != null) {
                                    serverConnection.peerDisconnected(peer);
                                    serverConnection.broadcastUpdatedPeerList();
                                }
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
                                    Log.d(TAG, "Received a HELLO message");
                                    TarsierWireProtos.HelloMessage helloMessage
                                            = TarsierWireProtos.HelloMessage
                                            .parseFrom(serializedProtoBuffer);
                                    peer = helloMessage.getPeer();
                                    serverConnection.addPeer(peer, this);
                                    serverConnection.broadcastUpdatedPeerList();
                                    break;
                                case MessageType.MESSAGE_TYPE_PEER_LIST:
                                    Log.e(TAG, "Server shouldn't be receiving this");
                                    break;
                                case MessageType.MESSAGE_TYPE_PRIVATE:
                                    TarsierWireProtos.TarsierPrivateMessage privateMessage;
                                    privateMessage = TarsierWireProtos.TarsierPrivateMessage
                                            .parseFrom(serializedProtoBuffer);
                                    if (serverConnection.isLocalPeer(
                                            privateMessage.getReceiverPublicKey().toByteArray())) {
                                        handler.obtainMessage(
                                                MessageType.messageTypeFromData(buffer),
                                                serializedProtoBuffer).sendToTarget();
                                        handler.obtainMessage(
                                                MessageType.messageTypeFromData(buffer),
                                                serializedProtoBuffer).sendToTarget();
                                    } else {
                                        serverConnection
                                                .sendMessage(serverConnection.peerWithPublicKey(
                                                                privateMessage
                                                                        .getReceiverPublicKey()
                                                                        .toByteArray()),
                                                        serializedProtoBuffer);
                                    }
                                    break;
                                case MessageType.MESSAGE_TYPE_PUBLIC:
                                    TarsierWireProtos.TarsierPublicMessage publicMessage;
                                    publicMessage = TarsierWireProtos.TarsierPublicMessage
                                            .parseFrom(serializedProtoBuffer);

                                    serverConnection.broadcastMessage(
                                            publicMessage.getSenderPublicKey().toByteArray(),
                                            publicMessage.getPlainText().toByteArray());

                                    handler.obtainMessage(MessageType.messageTypeFromData(buffer),
                                            serializedProtoBuffer).sendToTarget();

                                    Log.d(TAG, "A public message is received: " + serializedProtoBuffer.toString());
                                    break;
                            }
                        }
                    } catch (InvalidProtocolBufferException e) {
                        Log.e(TAG, "Got unparsable protocol buffer " + e.getLocalizedMessage());
                    }

                }
            } catch (IOException e) {
                if (peer != null) {
                    serverConnection.peerDisconnected(peer);
                    serverConnection.broadcastUpdatedPeerList();
                }

                e.printStackTrace();

            } catch (NullPointerException e) {
                if (peer != null) {
                    serverConnection.peerDisconnected(peer);
                    serverConnection.broadcastUpdatedPeerList();
                }

                e.printStackTrace();
            }
        }

        protected synchronized void write(byte[] buffer) {
            try {
                if (buffer.length < CURRENT_MAX_MESSAGE_SIZE) {
                    mOutputStream.write(buffer);
                } else {
                    Log.d(TAG, "buffer.length is bigger than message size");
                }
            } catch (IOException e) {
                handler.sendEmptyMessage(MessageType.MESSAGE_TYPE_DISCONNECT);
                Log.e(TAG, "Exception during write", e);
            }
        }
    }

}
