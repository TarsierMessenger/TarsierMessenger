package ch.tarsier.tarsier.event;

/**
 * This event is posted by the network stack whenever we connect to / create a chatroom.
 *
 * @author romac
 */
public class ConnectedEvent {

    private final boolean mIsGroupOwner;

    public ConnectedEvent(boolean isGroupOwner) {
        mIsGroupOwner = isGroupOwner;
    }

    public boolean isGroupOwner() {
        return mIsGroupOwner;
    }
}
