package ch.tarsier.tarsier.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * Created by fred on 09/11/14.
 */
public interface ConversationViewDelegate {
    public void connected();
    public void receivedNewPeersList(List<Peer> peers);
}
