package ch.tarsier.tarsier.event;

import ch.tarsier.tarsier.domain.model.Chat;

/**
 * This event is posted by an activity when we want to create a new group.
 *
 * @author romac
 * @author amirrezaw
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
