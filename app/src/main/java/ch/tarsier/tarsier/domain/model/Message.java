package ch.tarsier.tarsier.domain.model;

/**
 * @author McMoudi
 */
public class Message {

    private int mChatId;
    private String mText;
    private long mPeerId;
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
    public Message(int chatID, String text, long peerId, long dateTime) {
        mChatId = chatID;
        mText = text;
        mPeerId = peerId;
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
        // mPeerId = Tarsier.app().getUserPreferences().getId();
        mIsSentByUser = true;
        mDateTime = dateTime;
    }

    public String getText() {
        return mText;
    }

    public long getAuthor() {
        return mPeerId;
    }

    public int getChatID() {
        return mChatId;
    }

    public long getPeerId() {
        return mPeerId;
    }

    public long getDateTime() {
        return mDateTime;
    }

    public boolean isSentByUser() {
        return mIsSentByUser;
    }
}