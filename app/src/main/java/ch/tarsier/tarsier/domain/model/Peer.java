package ch.tarsier.tarsier.domain.model;

import ch.tarsier.tarsier.Tarsier;

/**
 * @author xawill
 * @author romac
 */
public class Peer {

    private PeerId mId;

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

    public PeerId getId() {
        return mId;
    }

    public void setId(PeerId id) {
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

    public boolean isUser() {
        return false;
    }
}
