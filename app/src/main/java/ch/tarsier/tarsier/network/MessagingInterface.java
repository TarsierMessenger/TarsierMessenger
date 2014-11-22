package ch.tarsier.tarsier.network;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author FredericJacobs
 * @author amirezza
 */
public interface MessagingInterface {

    static final int SERVER_PORT = 8888;

    List<Peer> getMembersList();

    void broadcastMessage(String message);

    void sendMessage(Peer peer, String message);

    void setConversationViewDelegate(ConversationViewDelegate delegate);

    void setConversationStorageDelegate(ConversationStorageDelegate delegate);
}
