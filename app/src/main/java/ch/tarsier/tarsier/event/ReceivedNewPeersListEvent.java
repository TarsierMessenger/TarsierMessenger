package ch.tarsier.tarsier.event;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author romac
 */
public class ReceivedNewPeersListEvent {

    private final List<Peer> mPeers;

    public ReceivedNewPeersListEvent(List<Peer> peers) {
        mPeers = peers;
    }

    public List<Peer> getPeers() {
        return mPeers;
    }
}
