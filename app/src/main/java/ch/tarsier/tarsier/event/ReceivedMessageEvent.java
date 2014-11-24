package ch.tarsier.tarsier.event;

import com.google.common.base.Optional;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author romac
 */
public class ReceivedMessageEvent {

    private final Optional<String> mDiscussion;

    private final String mMessage;

    private final Peer mPeer;

    public ReceivedMessageEvent(String message, Peer peer, Optional<String> discussion) {
        mMessage = message;
        mPeer = peer;
        mDiscussion = discussion;
    }

    public Optional<String> getDiscussion() {
        return mDiscussion;
    }

    public String getMessage() {
        return mMessage;
    }

    public Peer getPeer() {
        return mPeer;
    }
}
