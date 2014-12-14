package ch.tarsier.tarsier.event;

import ch.tarsier.tarsier.domain.model.Chat;

/**
 * CreateGroupEvent is the event representing the creation of a group.
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
