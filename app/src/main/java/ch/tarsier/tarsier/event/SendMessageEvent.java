package ch.tarsier.tarsier.event;

import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;

/**
 * SendMessageEvent is the event representing a message being sent.
 *
 * @author romac
 */
public class SendMessageEvent {

    private final Chat mChat;
    private final Message mMessage;

    public SendMessageEvent(Chat chat, Message message) {
        mChat = chat;
        mMessage = message;
    }

    public Message getMessage() {
        return mMessage;
    }

    public Chat getChat() {
        return mChat;
    }

    public boolean isPublic() {
        return !isPrivate();
    }

    public boolean isPrivate() {
        return mChat.isPrivate();
    }
}
