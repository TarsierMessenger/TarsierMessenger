package ch.tarsier.tarsier.event;

import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author romac
 */
public class SendMessageEvent {

    private final Chat mChat;
    private final Peer mPeer;
    private final Message mMessage;

    public SendMessageEvent(Chat chat, Message message) {
        mPeer = null;
        mChat = chat;
        mMessage = message;
    }

    public SendMessageEvent(Peer peer, Message message) {
        mChat = null;
        mPeer = peer;
        mMessage = message;
    }

    public Message getMessage() {
        return mMessage;
    }

    public Chat getChat() {
        return mChat;
    }

    public Peer getPeer() {
        return mPeer;
    }

    public boolean isPublic() {
        return mChat != null;
    }

    public boolean isPrivate() {
        return mPeer != null;
    }
}
