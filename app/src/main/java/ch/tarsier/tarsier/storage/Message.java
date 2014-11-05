package ch.tarsier.tarsier.storage;

/**
 * Created by McMoudi on 24/10/14.
 */
public class Message {

    private int mId, mChatID;
    private String mContent;
    private String mAuthor;
    private long mDateTime;
    private boolean mSentByUser;


    public Message(int id, int chatID, String content, String author, long dateTime,boolean SentByUser) {
        mId = id;
        mChatID = chatID;
        mContent = content;
        mAuthor = author;
        mDateTime = dateTime;
        mSentByUser = SentByUser;
    }


    public int getId() {
        return mId;
    }

    public String getContent() {
        return mContent;
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
