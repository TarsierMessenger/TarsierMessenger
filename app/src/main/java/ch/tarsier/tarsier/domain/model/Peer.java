package ch.tarsier.tarsier.domain.model;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
    private String mPicturePath;
    private boolean mOnline;

    public Peer() {
        mId = -1;
    }

    public Peer(String name) {
        mUserName = name;
        mId = -1;
    }

    // TODO: Remove when ChatroomPeersActivity won't need it anymore.
    public Peer(String name, String statusMessage) {
        this();
        mUserName = name;
        mStatusMessage = statusMessage;
        mId = -1;
    }

    public Peer(String name, PublicKey publicKey) {
        this();
        mUserName = name;
        mPublicKey = publicKey;
        mId = -1;
    }

    public Peer(byte[] data) throws InvalidProtocolBufferException {
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
        if (getPicturePath() != null) {
            return BitmapFactory.decodeFile(getPicturePath());
        }

        return BitmapFactory.decodeResource(Tarsier.app().getResources(), R.drawable.tarsier_placeholder);
    }

    public String getPicturePath() {
        if (mPicturePath == null) {
            mPicturePath = Tarsier.app().getUserPreferences().getPicturePath();
        }
        return mPicturePath;
    }

    public void setPicturePath(String picturePath) {
        mPicturePath = picturePath;
    }

    public boolean isUser() {
        return false;
    }

    public byte[] toByteArray() {
        TarsierWireProtos.Peer.Builder peerBuilder = TarsierWireProtos.Peer.newBuilder();
        peerBuilder.setName(mUserName);
        peerBuilder.setPublicKey(ByteString.copyFrom(mPublicKey.getBytes()));

        return peerBuilder.build().toByteArray();
    }
}
