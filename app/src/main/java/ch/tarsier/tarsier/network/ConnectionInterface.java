package ch.tarsier.tarsier.network;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author amirezza
 */
public interface ConnectionInterface {

    List<Peer> getMembersList();

    void broadcastMessage(byte[] publicKey, byte[] message);

    void sendMessage(Peer peer, byte[] message);

    void setConversationViewDelegate(ConversationViewDelegate delegate);

    void setConversationStorageDelegate(ConversationStorageDelegate delegate);
}
