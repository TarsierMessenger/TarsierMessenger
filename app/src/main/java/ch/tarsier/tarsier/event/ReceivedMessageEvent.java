package ch.tarsier.tarsier.event;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author romac
 */
public class ReceivedMessageEvent {

    private final String mMessage;

    private final Peer mSender;

    private final boolean mIsPrivate;

    public ReceivedMessageEvent(String message, Peer sender, boolean isPrivate) {
        mMessage = message;
        mSender = sender;
        mIsPrivate = isPrivate;
    }

    public String getMessage() {
        return mMessage;
    }

    public Peer getSender() {
        return mSender;
    }

    public boolean isPrivate() {
        return mIsPrivate;
    }

    public boolean isPublic() {
        return !isPrivate();
    }
}
