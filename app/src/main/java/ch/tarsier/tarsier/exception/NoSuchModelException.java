package ch.tarsier.tarsier.exception;

/**
 * @author gluthier
 */
public class NoSuchModelException extends Exception {

    public NoSuchModelException(String detailMessage) {
        super(detailMessage);
    }

    public NoSuchModelException(InvalidCursorException e) {
        super(e);
    }
}
