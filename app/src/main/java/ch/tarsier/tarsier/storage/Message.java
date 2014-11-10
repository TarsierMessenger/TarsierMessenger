package ch.tarsier.tarsier.storage;

/**
 * Created by McMoudi on 24/10/14.
 */
public class Message {

    private int mChatId;
    private String mText;
    private int mAuthorId;
    private long mDateTime;
    private boolean mIsSentByUser;

    /**
     * Create a message sent by a peer
     *
     * @param chatID the id of the conversation in which the message has been sent
     * @param text the body of the message
     * @param peerId the id of the peer which sent the message
     * @param dateTime the timestamp at which the message has been sent
     */
    public Message(int chatID, String text, int peerId, long dateTime) {
        mChatId = chatID;
        mText = text;
        mAuthorId = peerId;
        mIsSentByUser = false;
        mDateTime = dateTime;
    }

    /**
     * Create a message sent by the user
     *
     * @param chatID the id of the conversation in which the message has been sent
     * @param text the body of the message
     * @param dateTime the timestamp at which the message has been sent
     */
    public Message(int chatID, String text, long dateTime) {
        mChatId = chatID;
        mText = text;
        mAuthorId = StorageAccess.getInstance().getMyId();
        mIsSentByUser = true;
        mDateTime = dateTime;
    }

    public String getText() {
        return mText;
    }

    public int getAuthor() {
        return mAuthorId;
    }

    public int getChatID() {
        return mChatId;
    }

    public long getDateTime() {
        return mDateTime;
    }

    public boolean isSentByUser() {
        return mIsSentByUser;
    }
}
