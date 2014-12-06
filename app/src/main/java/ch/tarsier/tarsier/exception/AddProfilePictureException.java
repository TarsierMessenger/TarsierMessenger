package ch.tarsier.tarsier.exception;

/**
 * @author Benjamin Paccaud
 */
public class AddProfilePictureException extends Exception {
    public AddProfilePictureException() {
        super("Unable to set profile picture.");
    }

    public AddProfilePictureException(String detailMessage) {
        super(detailMessage);
    }
}
