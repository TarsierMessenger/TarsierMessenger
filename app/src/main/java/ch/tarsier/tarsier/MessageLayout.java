package ch.tarsier.tarsier;


import android.graphics.Bitmap;

/**
 * @author xawill
 */
public class MessageLayout {
    private String mText;
    private long mTimeSent;
    private Bitmap mPicture;
    private String mAuthorName;

    public MessageLayout(Message message) {
        mText = message.getText();
        mTimeSent = message.getTime();
        long authorID = message.getAuthor();
        Author author = StorageManager.getAuthor(authorID);
        mPicture = author.getPicture();
        mAuthorName = author.getName();
    }

    public long getTimeSent() {
        return mTimeSent;
    }

    public Bitmap getPicture() {
        return mPicture;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public String getText() {
        return mText;
    }
}
