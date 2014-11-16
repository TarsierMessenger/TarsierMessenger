package ch.tarsier.tarsier.network;

import java.util.List;

/**
 * Created by amirreza on 11/16/14.
 */
public interface ConnectionInterface {
    public List<Peer> getMembersList();
    public void broadcastMessage(byte[] publicKey, byte[] message);
    public void sendMessage(Peer peer, byte[] message);

    public void setConversationViewDelegate(ConversationViewDelegate delegate);
    public void setConversationStorageDelegate(ConversationStorageDelegate delegate);
}
