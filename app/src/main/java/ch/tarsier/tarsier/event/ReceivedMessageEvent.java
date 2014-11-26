package ch.tarsier.tarsier.event;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author romac
 */
public class ReceivedMessageEvent {

    private final String mMessage;

    private final Peer mSender;

    public ReceivedMessageEvent(String message, Peer sender) {
        mMessage = message;
        mSender = sender;
    }

    public String getMessage() {
        return mMessage;
    }

    public Peer getSender() {
        return mSender;
    }
}
