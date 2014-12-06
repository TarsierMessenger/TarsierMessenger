package ch.tarsier.tarsier.domain.model;

import java.io.Serializable;

/**
 * @author McMoudi
 * @author romac
 */
public class Chat implements Serializable {

    private long mId;
    private String mTitle;
    private Peer mHost;
    private boolean mPrivate;
    private int mAvatarRessourceId;

    public Chat() {
        mId = -1;
        mAvatarRessourceId = -1;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    /**
     * @return the other peer in a private chat or the name of the chatroom in a chatroom.
     */
    public String getTitle() {
        if (isPrivate()) {
            return getHost().getUserName();
        } else {
            if (mTitle != null) {
                return mTitle;
            } else {
                String DEFAULT_TITLE = "'s chatroom";
                return getHost().getUserName() + DEFAULT_TITLE;
            }
        }
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isPrivate() {
        return mPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        mPrivate = isPrivate;
    }

    public Peer getHost() {
        return mHost;
    }

    public void setHost(Peer host) {
        mHost = host;
    }

    public boolean isHost(Peer peer) {
        if (peer == null || getHost() == null) {
            return false;
        }

        return getHost().getId() == peer.getId();
    }

    public int getAvatarRessourceId() {
        return mAvatarRessourceId;
    }

    public void setAvatarRessourceId(int id) {
        mAvatarRessourceId = id;
    }
}
