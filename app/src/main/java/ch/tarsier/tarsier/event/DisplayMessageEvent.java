package ch.tarsier.tarsier.event;

import ch.tarsier.tarsier.domain.model.Message;

/**
 * @author romac
 * @author amirezzaw
 */
public class DisplayMessageEvent {

    private final Message mMessage;

    public DisplayMessageEvent(Message message) {
        mMessage = message;
    }

    public Message getMessage() {
        return mMessage;
    }
}
