package ch.tarsier.tarsier.domain.model;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.value.PublicKey;

/**
 * @author xawill
 * @author romac
 */
public class Peer {

    private long mId;
    private PublicKey mPublicKey;
    private String mUserName;
    private String mStatusMessage;
    private String mPicturePath;
    private boolean mOnline;

    public Peer() {
        // FIXME: Only until we properly retrieve the peer's picture.
        mPicturePath = Tarsier.app().getStorage().getMyPicturePath();
    }

    // TODO: Remove when ChatroomPeersActivity won't need it anymore.
    public Peer(String name, String statusMessage) {
        this();
        mUserName = name;
        mStatusMessage = statusMessage;
    }

    public Peer(String name) {
        this();
        mUserName = name;
    }

    public Peer(String name, long id) {
        this();
        mUserName = name;
        mId = id;
    }

    public Peer(String name, PublicKey publicKey) {
        this();
        mUserName = name;
        mPublicKey = publicKey;
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

    public String getPicturePath() {
        return mPicturePath;
    }

    public void setPicturePath(String picturePath) {
        mPicturePath = picturePath;
    }

    public boolean isUser() {
        return false;
    }
}
