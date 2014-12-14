package ch.tarsier.tarsier.event;

/**
 * This Event is launch when the connection is not initialized.
 * This can happen is there is no Wifi on the device or if there is no
 * devices connected to this one
 *
 * @author benpac
 */
public class ErrorConnectionEvent {
    private String mErrorMessage;

    public ErrorConnectionEvent(String error) {
        mErrorMessage = error;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String error) {
        mErrorMessage = error;
    }
}
