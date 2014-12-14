package ch.tarsier.tarsier.event;

/**
 * ConnectedEvent is the event representing the connection.
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
