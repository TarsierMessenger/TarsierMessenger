package ch.tarsier.tarsier.domain.model;


import android.graphics.Bitmap;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;

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
        Peer peer = Tarsier.app().getStorage().getPeer(peerId);
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
