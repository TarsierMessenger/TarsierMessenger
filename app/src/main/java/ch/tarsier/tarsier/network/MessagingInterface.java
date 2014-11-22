package ch.tarsier.tarsier.network;

import java.util.EventListener;
import java.util.List;
import java.util.Observer;

import ch.tarsier.tarsier.domain.model.Peer;

public interface MessagingInterface {
    static final int SERVER_PORT = 8888;

    public List<Peer> getMembersList();
    public void broadcastMessage(String message);
    public void sendMessage(Peer peer, String message);

    public void setConversationViewDelegate(ConversationViewDelegate delegate);
    public void setConversationStorageDelegate(ConversationStorageDelegate delegate);
}
