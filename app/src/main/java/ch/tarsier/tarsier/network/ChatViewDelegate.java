package ch.tarsier.tarsier.network;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author FredericJacobs
 */
public interface ChatViewDelegate {

    void connected();

    void receivedNewPeersList(List<Peer> peers);
}
