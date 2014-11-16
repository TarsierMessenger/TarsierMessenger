package ch.tarsier.tarsier.domain.model;

/**
 * @author McMoudi
 * @author romac
 */
public class Chat {

    private long mId;
    private String mTitle;
    private Peer mHost;
    private boolean mPrivate;

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
        return isPrivate() ? getHost().getName() : mTitle;
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
}
