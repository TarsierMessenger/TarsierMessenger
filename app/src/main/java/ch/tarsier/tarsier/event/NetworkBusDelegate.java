package ch.tarsier.tarsier.event;

import com.google.common.base.Optional;

import com.squareup.otto.Bus;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.network.ChatStorageDelegate;
import ch.tarsier.tarsier.network.ChatViewDelegate;

/**
 * @author romac
 */
public class NetworkBusDelegate implements ChatStorageDelegate, ChatViewDelegate {

    private final Bus mEventBus;

    public NetworkBusDelegate(Bus eventBus) {
        mEventBus = eventBus;
    }

    @Override
    public void storeMessage(String message, Peer peer, Optional<String> discussion) {
        mEventBus.post(new NewMessageEvent(message, peer, discussion));
    }

    @Override
    public void connected() {
        mEventBus.post(new ConnectedEvent());
    }

    @Override
    public void receivedNewPeersList(List<Peer> peers) {
        mEventBus.post(new ReceivedNewPeersListEvent(peers));
    }
}
