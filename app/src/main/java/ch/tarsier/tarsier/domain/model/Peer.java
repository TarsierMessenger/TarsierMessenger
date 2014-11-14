package ch.tarsier.tarsier.domain.model;

import android.net.Uri;

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
        // FIXME: Only until we properly retrieve the peer's picture.
        return Tarsier.app().getStorage().getMyPicturePath();
    }

    public void setPicturePath(String picturePath) {
        mPicturePath = picturePath;
    }
}
