package ch.tarsier.tarsier.event;

import com.google.common.base.Optional;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author romac
 */
public class ReceivedMessageEvent {

    private final String mMessage;

    private final Peer mPeer;

    public ReceivedMessageEvent(String message, Peer peer) {
        mMessage = message;
        mPeer = peer;
    }

    public String getMessage() {
        return mMessage;
    }

    public Peer getPeer() {
        return mPeer;
    }
}
