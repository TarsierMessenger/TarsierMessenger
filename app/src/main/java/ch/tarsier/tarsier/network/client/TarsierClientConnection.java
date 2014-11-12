package ch.tarsier.tarsier.network.client;

import android.os.Handler;
import android.util.Log;

import com.google.protobuf.ByteString;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import ch.tarsier.tarsier.network.ByteUtils;
import ch.tarsier.tarsier.network.Peer;
import ch.tarsier.tarsier.network.WiFiDirectDebugActivity;
import ch.tarsier.tarsier.network.networkMessages.MessageType;
import ch.tarsier.tarsier.network.networkMessages.TarsierWireProtos;

/**
 * Created by amirreza on 10/26/14.
 */
public class TarsierClientConnection implements Runnable {
    private static final String TAG = "TarsierClientConnection";
    private static final int CURRENT_MAX_MESSAGE_SIZE =  2048;
    private static final int TIMEOUT_SOCKET_LIMIT = 5000;

    private Socket mSocket;
    private Handler handler;
    private LinkedHashMap<byte[], Peer> mPeersMap                    = new LinkedHashMap<byte[], Peer>();
    private Peer localPeer = new Peer("My name", "MyPublicKey".getBytes());//TODO: Fetch from storage;

    private InputStream in;
    private OutputStream out;
    private InetAddress mAddress;

    public TarsierClientConnection(Handler handler,InetAddress groupOwnerAddress) {
        this.handler   = handler;
        this.mAddress = groupOwnerAddress;
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

    //TODO: Not sure if this one is used in the client
    public void addPeer(TarsierWireProtos.Peer peer){
        mPeersMap.put(peer.getPublicKey().toByteArray(), new Peer(peer.getName(), peer.getPublicKey().toByteArray()));
    }

    //TODO: This methos is probabely not used in the client. Will be updated by sever broadcast
    public void peerDisconnected(TarsierWireProtos.Peer peer){
        mPeersMap.remove(peer.getPublicKey().toByteArray());

    }

    public void broadcast(byte [] message) {
        TarsierWireProtos.TarsierPublicMessage.Builder publicMessage = TarsierWireProtos.TarsierPublicMessage.newBuilder();
        publicMessage.setSenderPublicKey(ByteString.copyFrom(localPeer.getPublicKey()));
        publicMessage.setPlainText(ByteString.copyFrom(message));
        write(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PUBLIC, publicMessage.build().toByteArray()));
    }

    //TODO: This methos is probabely not used in the client
    public void broadcastUpdatedPeerList (){
//        TarsierWireProtos.PeerUpdatedList.Builder peerList = TarsierWireProtos.PeerUpdatedList.newBuilder();
//
//        for (Peer aPeer : getPeers()){
//            TarsierWireProtos.Peer.Builder tarsierPeer = TarsierWireProtos.Peer.newBuilder();
//            tarsierPeer.setPublicKey(ByteString.copyFrom(aPeer.getPublicKey()));
//            tarsierPeer.setName(aPeer.getPeerName());
//            peerList.addPeer(tarsierPeer.build());
//        }
//
//        broadcast(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PEER_LIST, peerList.build().toByteArray()));
    }


    public void sendMessage(Peer peer, byte[] message) {
        sendMessage(peer.getPublicKey(), message);
    }
    //TODO: I think we need to modify the equivalent method in server as here. To make it easier for
    //TODO: gui to use. Meaning that we consider outsiders don't add the message-type themselves
    //TODO: It is also changed to private. Public method id the one in above.
    private void sendMessage(byte[] publicKey, byte[] message){
        TarsierWireProtos.TarsierPrivateMessage.Builder privateMessage = TarsierWireProtos.TarsierPrivateMessage.newBuilder();
        privateMessage.setReceiverPublicKey(ByteString.copyFrom(publicKey));
        privateMessage.setSenderPublicKey(ByteString.copyFrom(localPeer.getPublicKey()));
        //TODO: is the msg already encrypted? what is setIV ?
        privateMessage.setCipherText(ByteString.copyFrom(message));
        write(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PRIVATE, privateMessage.build().toByteArray()));
    }

    public Peer peerWithPublicKey(byte[] publicKey){
        return mPeersMap.get(publicKey);
    }

    public boolean isLocalPeer (byte[] publicKey) {
        return Arrays.equals(localPeer.getPublicKey(), publicKey);
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

            //TODO: Need something similar in the sever?
            //Give this class to the gui or storage
            handler.obtainMessage(MessageType.MESSAGE_TYPE_MY_HANDLE, this)
                    .sendToTarget();


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

                byte[] typeAndMessage = ByteUtils.split(buffer,bytes,0)[0];
                byte[] serializedProtoBuffer = ByteUtils.split(typeAndMessage, 1, typeAndMessage.length-1)[1];

                switch (MessageType.messageTypeFromData(buffer)) {
                    case MessageType.MESSAGE_TYPE_HELLO:
                        Log.e(TAG, "Client shouldn't be receiving this");
                        break;
                    case MessageType.MESSAGE_TYPE_PEER_LIST:
                        TarsierWireProtos.PeerUpdatedList peerList;
                        peerList = TarsierWireProtos.PeerUpdatedList.parseFrom(serializedProtoBuffer);
                        List<TarsierWireProtos.Peer> peers = peerList.getPeerList();
                        updatePeers();
                        break;
                    case MessageType.MESSAGE_TYPE_PRIVATE:
                        TarsierWireProtos.TarsierPrivateMessage privateMessage;
                        privateMessage = TarsierWireProtos.TarsierPrivateMessage.parseFrom(serializedProtoBuffer);
                        if (isLocalPeer(privateMessage.getReceiverPublicKey().toByteArray())) {
                            handler.obtainMessage(MessageType.messageTypeFromData(buffer), serializedProtoBuffer);
                        } else {
                            Log.e(TAG, "Wrong person!");
                        }
                        break;
                    case MessageType.MESSAGE_TYPE_PUBLIC:
                        Log.e(TAG, "Client doesn't receive public message");
                        break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updatePeers() {

    }

    public synchronized void write(byte[] buffer) {
        try {
            if (buffer.length > CURRENT_MAX_MESSAGE_SIZE)
                out.write(buffer);
        } catch (IOException e) {
            handler.sendEmptyMessage(MessageType.MESSAGE_TYPE_DISCONNECT);
            Log.e(TAG, "Exception during write", e);
        }
    }

    private void sendHelloMessage(){
        TarsierWireProtos.HelloMessage.Builder helloMessage = TarsierWireProtos.HelloMessage.newBuilder();

        TarsierWireProtos.Peer.Builder peer = TarsierWireProtos.Peer.newBuilder();
        peer.setName(localPeer.getPeerName());
        peer.setPublicKey(ByteString.copyFrom(localPeer.getPublicKey()));

        helloMessage.setPeer(peer.build());

        write(ByteUtils.prependInt(MessageType.MESSAGE_TYPE_HELLO, helloMessage.build().toByteArray()));
    }
}


