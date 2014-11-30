package ch.tarsier.tarsier.event;

import java.util.List;

import ch.tarsier.tarsier.domain.model.Chat;

/**
 * Created by benjamin on 30/11/14.
 */
public class ReceivedNearbyChatListEvent {
    private final List<Chat> mChats;

    public ReceivedNearbyChatListEvent(List<Chat> peers) {
        mChats = peers;
    }

    public List<Chat> getChats() {
        return mChats;
    }

}
