package ch.tarsier.tarsier.event;

import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author romac
 */
public class SendMessageEvent {

    private final Chat mChat;
    private final Peer mPeer;
    private final String mMessage;

    public SendMessageEvent(Chat chat, String message) {
        mPeer = null;
        mChat = chat;
        mMessage = message;
    }

    public SendMessageEvent(Peer peer, String message) {
        mChat = null;
        mPeer = peer;
        mMessage = message;
    }

    public String getMessage() {
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
