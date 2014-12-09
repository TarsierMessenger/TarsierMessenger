package ch.tarsier.tarsier.domain.model;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.Serializable;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.data.ByteArraySerializable;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.network.messages.TarsierWireProtos;

/**
 * @author xawill
 * @author romac
 * @author FredericJacobs
 */
public class Peer implements ByteArraySerializable, Serializable {

    private long mId;
    private PublicKey mPublicKey;
    private String mUserName;
    private String mStatusMessage;
    private boolean mOnline;
    // not used yet
    private String mPicturePath;

    public Peer() {
        mId = -1;
    }

    public Peer(String name) {
        this();
        mUserName = name;
    }

    // TODO: Remove when ChatroomPeersActivity won't need it anymore.
    public Peer(String name, String statusMessage) {
        this();
        mUserName = name;
        mStatusMessage = statusMessage;
    }

    public Peer(String name, PublicKey publicKey) {
        this();
        mUserName = name;
        mPublicKey = publicKey;
    }

    public Peer(byte[] data) throws InvalidProtocolBufferException {
        this();
        TarsierWireProtos.Peer peer = TarsierWireProtos.Peer.parseFrom(data);
        mUserName = peer.getName();
        mPublicKey = new PublicKey(peer.getPublicKey().toByteArray());
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public PublicKey getPublicKey() {
        return mPublicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        mPublicKey = publicKey;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getStatusMessage() {
        return mStatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.mStatusMessage = statusMessage;
    }

    public void setOnline(boolean online) {
        mOnline = online;
    }

    public boolean isOnline() {
        return mOnline;
    }

    public Bitmap getPicture() {
        int mod5 = (int) this.getId() % 5;

        switch (mod5) {
            case 0:
                return BitmapFactory.decodeResource(Tarsier.app().getResources(),
                        R.drawable.tarsier_placeholder);
            case 1:
                return BitmapFactory.decodeResource(Tarsier.app().getResources(),
                        R.drawable.tarsier1_placeholder);
            case 2:
                return BitmapFactory.decodeResource(Tarsier.app().getResources(),
                        R.drawable.tarsier2_placeholder);
            case 3:
                return BitmapFactory.decodeResource(Tarsier.app().getResources(),
                        R.drawable.tarsier3_placeholder);
            case 4:
                return BitmapFactory.decodeResource(Tarsier.app().getResources(),
                        R.drawable.tarsier4_placeholder);
            default:
                return BitmapFactory.decodeResource(Tarsier.app().getResources(),
                        R.drawable.tarsier_placeholder);
        }
    }

    public boolean isUser() {
        return false;
    }

    public void setPicturePath(String picturePath) {
        mPicturePath = picturePath;
    }

    public String getPicturePath() {
        return mPicturePath;
    }

    public byte[] toByteArray() {
        TarsierWireProtos.Peer.Builder peerBuilder = TarsierWireProtos.Peer.newBuilder();
        peerBuilder.setName(mUserName);
        peerBuilder.setPublicKey(ByteString.copyFrom(mPublicKey.getBytes()));

        return peerBuilder.build().toByteArray();
    }
}
