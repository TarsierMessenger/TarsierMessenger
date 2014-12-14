package ch.tarsier.tarsier.event;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * ReceivedChatroomPeersListEvent is the event representing
 * the reception of the list of peers in a chatroom.
 *
 * @author romac
 */
public class ReceivedChatroomPeersListEvent {

    private final List<Peer> mPeers;

    public ReceivedChatroomPeersListEvent(List<Peer> peers) {
        mPeers = peers;
    }

    public List<Peer> getPeers() {
        return mPeers;
    }
}
