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
 * Peer is the class that models a peer.
 *
 * @author xawill
 * @author romac
 * @author FredericJacobs
 */
public class Peer implements ByteArraySerializable, Serializable {

    private static final int CASE_THREE = 3;
    private static final int CASE_FOUR = 4;
    private static final int NUMBER_OF_PLACEHOLDERS = 5;

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

    public Peer(String name, PublicKey publicKey) {
        this();
        mUserName = name;
        mPublicKey = publicKey;
    }

    /**
     * Create a new peer by unpacking its data from a ProtocolBuffer byte array.
     *
     * @param data The ProtocolBuffer byte array.
     * @throws InvalidProtocolBufferException
     */
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

    /**
     * As the network protocol cannot transfer profile pictures,
     * we need this dirty hack to show different profile pictures for up to 5 peers.
     *
     * @return A bitmap representation of a profile picture.
     */
    public Bitmap getPicture() {
        int mod5 = (int) this.getId() % NUMBER_OF_PLACEHOLDERS;

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
            case CASE_THREE:
                return BitmapFactory.decodeResource(Tarsier.app().getResources(),
                        R.drawable.tarsier3_placeholder);
            case CASE_FOUR:
                return BitmapFactory.decodeResource(Tarsier.app().getResources(),
                        R.drawable.tarsier4_placeholder);
            default:
                return BitmapFactory.decodeResource(Tarsier.app().getResources(),
                        R.drawable.tarsier_placeholder);
        }
    }

    /**
     * Check whether this peer is the actual user of the app, by comparing its public
     * key with the public key stored in the UserPreferences.
     *
     * @return true if they match, false otherwise
     */
    public boolean isUser() {
        return Tarsier.app().getUserRepository().getUser().getPublicKey().equals(getPublicKey());
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
