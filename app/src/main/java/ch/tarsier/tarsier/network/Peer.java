package ch.tarsier.tarsier.network;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import ch.tarsier.tarsier.network.networkMessages.TarsierWireProtos;

/**
 * Created by fred on 09/11/14.
 */
public class Peer {
    private String mName;
    private byte[] mPublicKey;

    public Peer (String name, byte[] publicKey) {
        mName = name;
        mPublicKey = publicKey;
    }

    public Peer (byte[] data) throws InvalidProtocolBufferException {
        TarsierWireProtos.Peer peer = TarsierWireProtos.Peer.parseFrom(data);
        mName = peer.getName();
        mPublicKey = peer.getPublicKey().toByteArray();
    }

    public String getPeerName (){
        return mName;
    }

    public byte[] getPublicKey (){
        return mPublicKey;
    }

    public byte[] serialized (){
        TarsierWireProtos.Peer.Builder peer = TarsierWireProtos.Peer.newBuilder();
        peer.setName(mName);
        peer.setPublicKey(ByteString.copyFrom(mPublicKey));
        return peer.build().toByteArray();
    }
}
