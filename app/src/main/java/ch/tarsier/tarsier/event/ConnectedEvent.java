package ch.tarsier.tarsier.event;

/**
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
