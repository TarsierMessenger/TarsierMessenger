package ch.tarsier.tarsier.storage;

/**
 * Created by McMoudi on 24/10/14.
 */
public class Message {

    private int mChatID;
    private String mText;
    private String mAuthor;
    private long mDateTime;
    private boolean mSentByUser;


    public Message(int chatID, String text, String author, long dateTime,boolean SentByUser) {
        mChatID = chatID;
        mText = text;
        mAuthor = author;
        mDateTime = dateTime;
        mSentByUser = SentByUser;
    }


    public String getText() {
        return mText;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public int getChatID() {
        return mChatID;
    }

    public long getDateTime() {
        return mDateTime;
    }

    public boolean isSentByUser() { return mSentByUser; }


}
