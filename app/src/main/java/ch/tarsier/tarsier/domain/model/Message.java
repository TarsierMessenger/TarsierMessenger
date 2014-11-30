package ch.tarsier.tarsier.domain.model;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.value.PublicKey;

/**
 * @author McMoudi
 * @author gluthier
 */
public class Message {

    private long mChatId;
    private String mText;
    private PublicKey mSenderPublicKey;
    private long mDateTime;
    private boolean mIsSentByUser;
    // mId is set to a value > 0 when the Message is inserted into the database
    private long mId;

    /**
     * Create a message sent by a peer
     *
     * @param chatId the id of the chat in which the message has been sent
     * @param text the body of the message
     * @param senderPublicKey the id of the peer which sent the message
     * @param dateTime the timestamp at which the message has been sent
     */
    public Message(long chatId, String text, byte[] senderPublicKey, long dateTime) {
        this(chatId, text, new PublicKey(senderPublicKey), dateTime);
    }

    public Message(long chatId, String text, PublicKey senderPublicKey, long dateTime) {
        mChatId = chatId;
        mText = text;
        mSenderPublicKey = senderPublicKey;
        mIsSentByUser = false;
        mDateTime = dateTime;
        mId = -1;
    }

    /**
     * Create a message sent by the user
     *
     * @param chatId the id of the chat in which the message has been sent
     * @param text the body of the message
     * @param dateTime the timestamp at which the message has been sent
     */
    public Message(long chatId, String text, long dateTime) {
        mChatId = chatId;
        mText = text;
        mSenderPublicKey = new PublicKey(Tarsier.app().getUserPreferences().getKeyPair().getPublicKey());
        mIsSentByUser = true;
        mDateTime = dateTime;
        mId = -1;
    }

    public String getText() {
        return mText;
    }

    public long getChatId() {
        return mChatId;
    }

    public PublicKey getSenderPublicKey() {
        return mSenderPublicKey;
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

    public void setText(String newText) {
        mText = newText;
    }

    public void setChatId(long newId) {
        mChatId = newId;
    }

    public void setDateTime(long newTime) {
        mDateTime = newTime;
    }
}
