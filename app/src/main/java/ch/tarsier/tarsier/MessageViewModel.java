package ch.tarsier.tarsier;


import android.graphics.Bitmap;

import ch.tarsier.tarsier.storage.Message;
import ch.tarsier.tarsier.storage.Peer;
import ch.tarsier.tarsier.storage.StorageAccess;

/**
 * @author xawill
 */
public class MessageViewModel {
    private final boolean isSentByUser;
    private String mText;
    private long mTimeSent;
    private Bitmap mPeerPicture;
    private String mPeerName;

    public MessageViewModel(Message message) {
        mText = message.getText();
        mTimeSent = message.getDateTime();
        int peerId = message.getPeerId();
        Peer peer = StorageAccess.getInstance().getPeer(peerId);
        mPeerPicture = peer.getPicture();
        mPeerName = peer.getName();
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
