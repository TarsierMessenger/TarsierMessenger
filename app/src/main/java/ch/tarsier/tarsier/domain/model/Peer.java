package ch.tarsier.tarsier.domain.model;

import ch.tarsier.tarsier.Tarsier;

/**
 * @author xawill
 */
public class Peer {

    private long mId;

    private String mName;

    private String mStatusMessage;

    private String mPicturePath;

    private boolean mOnline;

    public Peer() {
        // FIXME: Only until we properly retrieve the peer's picture.
        mPicturePath = Tarsier.app().getStorage().getMyPicturePath();
    }

    // TODO: Remove when ChatRoomParticipantsActivity won't need it anymore.
    public Peer(String name, String statusMessage) {
        mName = name;
        mStatusMessage = statusMessage;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
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
}
