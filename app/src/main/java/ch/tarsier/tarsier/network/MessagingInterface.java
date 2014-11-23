package ch.tarsier.tarsier.network;

import com.squareup.otto.Bus;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author FredericJacobs
 * @author amirezza
 */
public interface MessagingInterface {

    int SERVER_PORT = 8888;

    List<Peer> getMembersList();

    void broadcastMessage(String message);

    void sendMessage(Peer peer, String message);

    void setEventBus(Bus eventBus);
}
