package ch.tarsier.tarsier;


import android.graphics.Bitmap;

import ch.tarsier.tarsier.storage.Message;
import ch.tarsier.tarsier.storage.StorageAccess;

/**
 * @author xawill
 */
public class MessageViewModel {
    private final boolean isSentByUser;
    private String mText;
    private long mTimeSent;
    private Bitmap mPicture;
    private String mAuthorName;

    public MessageViewModel(Message message) {
        mText = message.getContent();
        mTimeSent = message.getDateTime();
        long authorID = message.getAuthor();
        Author author = StorageAccess.getInstance().getAuthor(authorID);
        mPicture = author.getPicture();
        mAuthorName = author.getName();
        isSentByUser = message.isSentByUser();
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

    public boolean isMessageSentByUser() {
        return isSentByUser;
    }
}
