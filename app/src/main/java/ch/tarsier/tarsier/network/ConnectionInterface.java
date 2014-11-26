package ch.tarsier.tarsier.network;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;

/**
 * @author amirezza
 */
public interface ConnectionInterface {

    List<Peer> getPeersList();

    void broadcastMessage(byte[] message);

    void broadcastMessage(byte[] publicKey, byte[] message);

    void sendMessage(Peer peer, byte[] message);
}
