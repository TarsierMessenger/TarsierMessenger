package ch.tarsier.tarsier.network;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.exception.PeerCipherException;

/**
 * Abstracts the network connection (typically implemented by ClientConnection and ServerConnection).
 * This connection interface is to be used in the messaging manager.
 * @author amirezza
 */
public interface ConnectionInterface {
    List<Peer> getPeersList();

    void broadcastMessage(byte[] message);

    void sendMessage(Peer peer, byte[] message) throws PeerCipherException;
}
