package ch.tarsier.tarsier.event;

import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;

/**
 * This event represents a new message to display, and should be handled by any activity
 * that display messages on the screen.
 *
 * @author romac
 * @author amirezzaw
 */
public class DisplayMessageEvent {

    private final Message mMessage;

    private final Peer mSender;

    private final Chat mChat;

    public DisplayMessageEvent(Message message, Peer sender, Chat chat) {
        mMessage = message;
        mSender = sender;
        mChat = chat;
    }

    public Message getMessage() {
        return mMessage;
    }

    public Peer getSender() {
        return mSender;
    }

    public Chat getChat() {
        return mChat;
    }
}
