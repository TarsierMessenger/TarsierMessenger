package ch.tarsier.tarsier.event;

import ch.tarsier.tarsier.domain.model.Chat;

/**
 * @author romac
 * @author amirezza
 */
public class CreateGroupEvent {

    private final Chat mChat;

    public CreateGroupEvent(Chat chat) {
        mChat = chat;
    }

    public Chat getChat() {
        return mChat;
    }
}
