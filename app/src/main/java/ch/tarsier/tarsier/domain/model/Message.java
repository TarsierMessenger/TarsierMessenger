package ch.tarsier.tarsier.domain.model;

import ch.tarsier.tarsier.Tarsier;

/**
 * @author McMoudi
 * @author gluthier
 */
public class Message {

    private int mChatId;
    private String mText;
    private long mPeerId;
    private long mDateTime;
    private boolean mIsSentByUser;
    // mId is set to a value > 0 when the Message is inserted into the database
    private long mId;

    /**
     * Create a message sent by a peer
     *
     * @param chatID the id of the chat in which the message has been sent
     * @param text the body of the message
     * @param peerId the id of the peer which sent the message
     * @param dateTime the timestamp at which the message has been sent
     */
    public Message(int chatID, String text, long peerId, long dateTime) {
        mChatId = chatID;
        mText = text;
        mPeerId = peerId;
        mIsSentByUser = false;
        mDateTime = dateTime;
        mId = -1;
    }

    /**
     * Create a message sent by the user
     *
     * @param chatID the id of the chat in which the message has been sent
     * @param text the body of the message
     * @param dateTime the timestamp at which the message has been sent
     */
    public Message(int chatID, String text, long dateTime) {
        mChatId = chatID;
        mText = text;
        mPeerId = Tarsier.app().getUserPreferences().getId();
        mIsSentByUser = true;
        mDateTime = dateTime;
        mId = -1;
    }

    public String getText() {
        return mText;
    }

    public long getAuthor() {
        return mPeerId;
    }

    // TODO: Remove, use getChatId instead.
    public int getChatID() {
        return (int) mChatId;
    }

    public long getChatId() {
        return mChatId;
    }

    public long getPeerId() {
        return mPeerId;
    }

    public long getDateTime() {
        return mDateTime;
    }

    public long getId() {
        return mId;
    }

    public boolean isSentByUser() {
        return mIsSentByUser;
    }

    public void setId(long newId) {
        mId = newId;
    }
}
