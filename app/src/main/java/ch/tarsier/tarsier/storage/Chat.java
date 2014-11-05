package ch.tarsier.tarsier.storage;

/**
 * Created by McMoudi on 24/10/14.
 */
public class Chat {

    private int mId;
    private String mTitle;
    private String mHost;
    private boolean mPrivate;

    /**
     * Construct a group chat
     *
     * @param id the chat ID
     * @param title the name of the room
     * @param host the host's ID
     */
    public Chat(int id, String title, String host) {
        mId = id;
        mTitle = title;
        mHost = host;
        mPrivate = false;
    }

    /**
     * Construct a private chat
     *
     * @param id the chat ID
     * @param peer the peer's ID
     */
    public Chat(int id, String peer) {
        mId = id;
        mHost = peer;
        mPrivate = true;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() { return mPrivate ? mHost : mTitle ; }

    public boolean isPrivate() {
        return mPrivate;
    }

    public String getHost() {
        return mHost;
    }
}
