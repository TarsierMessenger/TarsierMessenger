package ch.tarsier.tarsier.domain.model;

<<<<<<< HEAD
import java.io.Serializable;
=======
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
>>>>>>> 7320ede... modify model for adapter

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
        return isPrivate() ? getHost().getUserName() : mTitle;
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

<<<<<<< HEAD
    public boolean isHost(Peer peer) {
        if (peer != null && getHost() != null) {
            // TODO: Replace with Peer.equals() once we have it.
            return getHost().getId() == peer.getId();
        } else {
            return false;
        }
=======
    public int getAvatarRessourceId() {
        return mAvatarRessourceId;
    }

    public void setAvatarRessourceId(int id) {
        mAvatarRessourceId = id;
>>>>>>> 7320ede... modify model for adapter
    }
}
