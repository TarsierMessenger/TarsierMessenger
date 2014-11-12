package ch.tarsier.tarsier.network.server;

import android.os.Handler;
import android.util.Log;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import ch.tarsier.tarsier.network.Peer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;

import ch.tarsier.tarsier.network.networkMessages.TarsierWireProtos;
import ch.tarsier.tarsier.network.networkMessages.MessageType;
import ch.tarsier.tarsier.network.ByteUtils;

public class TarsierServerConnection implements Runnable {
    private static final String TAG = "TarsierServerConnection";
    private LinkedHashMap<byte[], ConnectionHandler> mConnectionMap  = new LinkedHashMap<byte[], ConnectionHandler>();
    private LinkedHashMap<byte[], Peer> mPeersMap                    = new LinkedHashMap<byte[], Peer>();
    private Peer localPeer = new Peer("My name", "MyPublicKey".getBytes());//TODO: Fetch from storage;
    private ServerSocket server;
    private Handler handler;

    public TarsierServerConnection(Handler handler) throws IOException {
        this.server    = new ServerSocket(MessageType.SERVER_SOCKET);
        this.handler   = handler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();
                new ConnectionHandler(this, socket, handler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Peer> getPeers(){
        Set<byte[]> peerKeySet = mPeersMap.keySet();
        ArrayList<Peer> membersList = new ArrayList<Peer>();
        for (byte[] key : peerKeySet) {
            Peer peer = mPeersMap.get(key);
            membersList.add(peer);
        }

        return membersList;
    }

    public void addPeer(TarsierWireProtos.Peer peer, ConnectionHandler handler){
        mConnectionMap.put(peer.getPublicKey().toByteArray(), handler);
        mPeersMap.put(peer.getPublicKey().toByteArray(), new Peer(peer.getName(), peer.getPublicKey().toByteArray()));
    }

    public void peerDisconnected(TarsierWireProtos.Peer peer){
        mConnectionMap.remove(peer.getPublicKey().toByteArray());
        mPeersMap.remove(peer.getPublicKey().toByteArray());

    }

    //This sends all public messages.
    public void broadcast(byte [] message) {
        for (Peer peer : getPeers()){
            ConnectionHandler connection = mConnectionMap.get(peer.getPublicKey());

            TarsierWireProtos.TarsierPublicMessage.Builder publicMessage = TarsierWireProtos.TarsierPublicMessage.newBuilder();
            publicMessage.setSenderPublicKey(ByteString.copyFrom(localPeer.getPublicKey()));
            publicMessage.setPlainText(ByteString.copyFrom(message));
            connection.write(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PUBLIC, publicMessage.build().toByteArray()));
        }
        Log.d(TAG, "A public message is sent to all peers.");
    }

    public void broadcastUpdatedPeerList (){
        TarsierWireProtos.PeerUpdatedList.Builder peerList = TarsierWireProtos.PeerUpdatedList.newBuilder();

        for (Peer aPeer : getPeers()){
            TarsierWireProtos.Peer.Builder tarsierPeer = TarsierWireProtos.Peer.newBuilder();
            tarsierPeer.setPublicKey(ByteString.copyFrom(aPeer.getPublicKey()));
            tarsierPeer.setName(aPeer.getPeerName());
            peerList.addPeer(tarsierPeer.build());
        }

        for (Peer peer : getPeers()){
            ConnectionHandler connection = mConnectionMap.get(peer.getPublicKey());
            connection.write(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PEER_LIST, peerList.build().toByteArray()));
        }

        Log.d(TAG, "Updated peerList is broadcast.");
    }

    public void sendMessage(Peer peer, byte[] message) {
        sendMessage(peer.getPublicKey(), message);
    }

    private void sendMessage(byte[] publicKey, byte[] message){
        ConnectionHandler connection = mConnectionMap.get(publicKey);
        if (connection != null){
            TarsierWireProtos.TarsierPrivateMessage.Builder privateMessage = TarsierWireProtos.TarsierPrivateMessage.newBuilder();
            privateMessage.setReceiverPublicKey(ByteString.copyFrom(publicKey));
            privateMessage.setSenderPublicKey(ByteString.copyFrom(localPeer.getPublicKey()));
            //TODO: is the msg already encrypted? what is setIV ?
            privateMessage.setCipherText(ByteString.copyFrom(message));
            connection.write(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PRIVATE, privateMessage.build().toByteArray()));
            Log.d(TAG, "A private message is sent to " + peerWithPublicKey(publicKey).getPeerName());
            connection.write(message);
        } else{
            Log.e(TAG, "Sadly there is no peer for that public key");
        }
    }

    public Peer peerWithPublicKey(byte[] publicKey){
        return mPeersMap.get(publicKey);
    }

    public boolean isLocalPeer (byte[] publicKey) {
        return Arrays.equals(localPeer.getPublicKey(), publicKey);
    }
}

class ConnectionHandler implements Runnable {
    private static final String TAG = "ConnectionHandler";
    private Socket       mSocket;
    private Handler      handler;
    private OutputStream mOutputStream;
    private TarsierServerConnection serverConnection;

    private TarsierWireProtos.Peer peer;

    private static final int CURRENT_MAX_MESSAGE_SIZE =  2048;

    public ConnectionHandler(TarsierServerConnection connection, Socket socket, Handler handler) {
        this.serverConnection = connection;
        this.mSocket = socket;
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        try{
            while(true){
                try {
                    InputStream mInputStream = mSocket.getInputStream();
                    mOutputStream = mSocket.getOutputStream();
                    int bytes;
                    byte[] buffer = new byte[CURRENT_MAX_MESSAGE_SIZE];

                    while (true) {
                        bytes = mInputStream.read(buffer);
                        if (bytes == -1) {
                            break;
                        }

                        Log.v(TAG, "Read some bytes");

                        if (bytes >= CURRENT_MAX_MESSAGE_SIZE){
                            //TODO: Fix message max size
                            Log.e(TAG, "Tarsier doesn't support those messages yet");
                            mSocket.close();
                        }

                        byte[] typeAndMessage = ByteUtils.split(buffer,bytes,0)[0];
                        byte[] serializedProtoBuffer = ByteUtils.split(typeAndMessage, 1, typeAndMessage.length-1)[1];

                        switch (MessageType.messageTypeFromData(buffer)){
                            case MessageType.MESSAGE_TYPE_HELLO:
                                TarsierWireProtos.HelloMessage helloMessage = TarsierWireProtos.HelloMessage.parseFrom(ByteUtils.split(buffer, 1, buffer.length-1)[1]);
                                peer  = helloMessage.getPeer();
                                serverConnection.addPeer(peer, this);
                                serverConnection.broadcastUpdatedPeerList();
                                break;
                            case MessageType.MESSAGE_TYPE_PEER_LIST:
                                Log.e(TAG, "Server shouldn't be receiving this");
                                break;
                            case MessageType.MESSAGE_TYPE_PRIVATE:
                                TarsierWireProtos.TarsierPrivateMessage privateMessage;
                                privateMessage = TarsierWireProtos.TarsierPrivateMessage.parseFrom(serializedProtoBuffer);
                                if (serverConnection.isLocalPeer(privateMessage.getReceiverPublicKey().toByteArray())){
                                    handler.obtainMessage(MessageType.messageTypeFromData(buffer), serializedProtoBuffer);
                                } else {
                                    serverConnection.sendMessage(serverConnection.peerWithPublicKey(privateMessage.getReceiverPublicKey().toByteArray()), serializedProtoBuffer);
                                }
                                break;
                            case MessageType.MESSAGE_TYPE_PUBLIC:
                                serverConnection.broadcast(serializedProtoBuffer);
                                handler.obtainMessage(MessageType.messageTypeFromData(buffer), serializedProtoBuffer).sendToTarget();
                                Log.d(TAG, "A public message is received.");
                                break;
                        }
                    }
                } catch (InvalidProtocolBufferException e){
                    Log.e(TAG, "Got unparsable protocol buffer " + e.getLocalizedMessage());
                }

            }
        } catch (IOException e) {
            if (peer != null){
                serverConnection.peerDisconnected(peer);
                serverConnection.broadcastUpdatedPeerList();
            }
            e.printStackTrace();
        }
    }

    public synchronized void write(byte[] buffer) {
        try {
            if (buffer.length > CURRENT_MAX_MESSAGE_SIZE)
            mOutputStream.write(buffer);
        } catch (IOException e) {
            handler.sendEmptyMessage(MessageType.MESSAGE_TYPE_DISCONNECT);
            Log.e(TAG, "Exception during write", e);
        }
    }
}
