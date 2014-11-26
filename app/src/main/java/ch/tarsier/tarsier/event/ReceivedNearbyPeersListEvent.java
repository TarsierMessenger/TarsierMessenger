package ch.tarsier.tarsier.event;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author romac
 */
public class ReceivedNearbyPeersListEvent {

    private final List<Peer> mPeers;

    public ReceivedNearbyPeersListEvent(List<Peer> peers) {
        mPeers = peers;
    }

    public List<Peer> getPeers() {
        return mPeers;
    }

}
