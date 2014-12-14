package ch.tarsier.tarsier.exception;

/**
 * AddProfilePictureException is the exception for the addition of the profile picture.
 *
 * @author benpac
 */
public class AddProfilePictureException extends Exception {
    public AddProfilePictureException() {
        super("Unable to set profile picture.");
    }

    public AddProfilePictureException(String detailMessage) {
        super(detailMessage);
    }
}
