package ch.tarsier.tarsier.domain.model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.exception.NoSuchModelException;

/**
 * @author xawill
 */
public class MessageViewModel {
    private final boolean isSentByUser;
    private String mText;
    private long mTimeSent;
    private Bitmap mPeerPicture;
    private String mPeerName;

    public MessageViewModel(Message message) throws InvalidCursorException, NoSuchModelException {
        mText = message.getText();
        mTimeSent = message.getDateTime();
        long peerId = message.getPeerId();
        Peer peer = Tarsier.app().getPeerRepository().findById(peerId);
        mPeerPicture = BitmapFactory.decodeFile(peer.getPicturePath());
        mPeerName = peer.getUserName();
        isSentByUser = message.isSentByUser();
    }

    public long getTimeSent() {
        return mTimeSent;
    }

    public Bitmap getPicture() {
        return mPeerPicture;
    }

    public String getAuthorName() {
        return mPeerName;
    }

    public String getText() {
        return mText;
    }

    public boolean isMessageSentByUser() {
        return isSentByUser;
    }
}
