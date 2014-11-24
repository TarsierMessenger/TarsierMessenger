package ch.tarsier.tarsier.event;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author romac
 */
public class ReceivedPeersListEvent {

    private final List<Peer> mPeers;

    private final Chat mChat;

    public ReceivedPeersListEvent(Chat chat, List<Peer> peers) {
        mChat = chat;
        mPeers = peers;
    }

    public List<Peer> getPeers() {
        return mPeers;
    }

    public Chat getChat() {
        return mChat;
    }
}
