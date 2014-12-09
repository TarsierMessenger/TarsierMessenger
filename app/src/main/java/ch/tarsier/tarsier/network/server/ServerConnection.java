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
import ch.tarsier.tarsier.util.TarsierMessageFactory;

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
        Log.d(TAG, "ServerConnection Created.");
        this.mServer = new ServerSocket(MessageType.SERVER_SOCKET);
        this.mHandler = handler;
        Thread t = new Thread(this);
        t.start();
    }

    private void selfAdd(ConnectionHandler handler) {
        TarsierWireProtos.Peer.Builder peer = TarsierWireProtos.Peer.newBuilder();
        peer.setName(mLocalUser.getUserName());
        peer.setPublicKey(ByteString.copyFrom(mLocalUser.getPublicKey().getBytes()));
        addPeer(peer.build(),handler);
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

    @Override
    public void broadcastMessage(byte[] message) {
        byte[] wireMessage = TarsierMessageFactory.wirePublicProto(message);
        broadcastMessage(mLocalUser.getPublicKey().getBytes(), wireMessage);
    }

    //This sends all public messages.
    public void broadcastMessage(byte[] publicKey, byte[] wireMessage) {
        for (Peer peer : getPeersList()) {
            if (!(new String(peer.getPublicKey().toByteArray()).equals(new String(publicKey)))) {
                ConnectionHandler connection = mConnectionMap.get(new String(peer.getPublicKey().toByteArray()));
                connection.write(wireMessage);
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
            ConnectionHandler connection = mConnectionMap.get(new String(peer.getPublicKey().toByteArray()));
            if(peer == null){
                Log.d(TAG,"peer is null");
            }else if(connection == null){
                Log.d(TAG,"Connection is null");
            }else {

                connection.write(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PEER_LIST,
                        peerList.build().toByteArray()));
            }
        }

        Log.d(TAG, "Updated peerList is broadcast.");
    }

    public void sendMessage(Peer peer, byte[] message) {
        sendMessage(peer.getPublicKey().getBytes(), message);
    }

    protected void sendMessage(byte[] publicKey, byte[] message) {
        ConnectionHandler connection = mConnectionMap.get(new String(publicKey));
        if (connection != null) {

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

    /**
     * @author amirezza
     * @author FredericJacobs
     */
    class ConnectionHandler implements Runnable {

        private static final String TAG = "ConnectionHandler";

        private Socket mSocket;

        private Handler mHandler;

        private OutputStream mOutputStream;

        private ServerConnection mServerConnection;

        private TarsierWireProtos.Peer peer;

        private static final int CURRENT_MAX_MESSAGE_SIZE = 2048;

        public ConnectionHandler(ServerConnection connection, Socket socket, Handler handler) {
            Log.d(TAG, "ConnectionHandler is created");
            this.mServerConnection = connection;
            this.mSocket = socket;
            this.mHandler = handler;
            this.mServerConnection.selfAdd(this);
            Thread t = new Thread(this);
            t.start();
        }

        public void run() {
            try {
                loop();
            } catch (IOException e) {
                if (peer != null) {
                    mServerConnection.peerDisconnected(peer);
                    Log.d(TAG,"Peer disconnected because IOException");
                    mServerConnection.broadcastUpdatedPeerList();
                }

                e.printStackTrace();

            } catch (NullPointerException e) {
                if (peer != null) {
                    mServerConnection.peerDisconnected(peer);
                    Log.d(TAG,"Peer disconnected because NullPointerException");
                    mServerConnection.broadcastUpdatedPeerList();
                }

                e.printStackTrace();
            }
        }

        private void loop() throws IOException {

                try {
                    InputStream mInputStream = mSocket.getInputStream();
                    mOutputStream = mSocket.getOutputStream();
                    byte[] buffer = new byte[CURRENT_MAX_MESSAGE_SIZE];

                    while (true) {
                        int bytes = mInputStream.read(buffer);
                        if (bytes == -1) {
                            if (peer != null) {
                                mServerConnection.peerDisconnected(peer);
                                Log.d(TAG,"Peer disconnected because bytes = -1");
                                mServerConnection.broadcastUpdatedPeerList();
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
                        byte[] serializedProtoBuffer =
                                ByteUtils.split(typeAndMessage, 1, typeAndMessage.length - 1)[1];

                        switch (MessageType.messageTypeFromData(buffer)) {

                            case MessageType.MESSAGE_TYPE_HELLO:
                                Log.d(TAG, "Received a HELLO message");

                                TarsierWireProtos.HelloMessage helloMessage
                                        = TarsierWireProtos.HelloMessage.parseFrom(
                                        serializedProtoBuffer);

                                peer = helloMessage.getPeer();
                                mServerConnection.addPeer(peer, this);
                                mServerConnection.broadcastUpdatedPeerList();

                                mHandler.obtainMessage(MessageType.MESSAGE_TYPE_PEER_LIST).sendToTarget();
                                break;

                            case MessageType.MESSAGE_TYPE_PEER_LIST:
                                Log.e(TAG, "Server shouldn't be receiving this");
                                break;

                            case MessageType.MESSAGE_TYPE_PRIVATE:
                                TarsierWireProtos.TarsierPrivateMessage privateMessage;
                                privateMessage = TarsierWireProtos.TarsierPrivateMessage
                                        .parseFrom(serializedProtoBuffer);
                                if (mServerConnection.isLocalPeer(
                                        privateMessage.getReceiverPublicKey().toByteArray())) {
                                    mHandler.obtainMessage(
                                            MessageType.messageTypeFromData(buffer),
                                            serializedProtoBuffer).sendToTarget();
                                    mHandler.obtainMessage(
                                            MessageType.messageTypeFromData(buffer),
                                            serializedProtoBuffer).sendToTarget();
                                } else {
                                    mServerConnection
                                            .sendMessage(mServerConnection.peerWithPublicKey(
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

                                mServerConnection.broadcastMessage(
                                        publicMessage.getSenderPublicKey().toByteArray(),
                                        typeAndMessage);

                                mHandler.obtainMessage(MessageType.messageTypeFromData(buffer),
                                        serializedProtoBuffer).sendToTarget();

                                Log.d(TAG, "A public message is received: " + serializedProtoBuffer.toString());
                                break;

                            default:
                                Log.d(TAG, "Unknown message type");
                        }
                    }
                } catch (InvalidProtocolBufferException e) {
                    Log.e(TAG, "Got unparsable protocol buffer " + e.getLocalizedMessage());
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
                mHandler.sendEmptyMessage(MessageType.MESSAGE_TYPE_DISCONNECT);
                Log.e(TAG, "Exception during write", e);
            }
        }
    }

}
