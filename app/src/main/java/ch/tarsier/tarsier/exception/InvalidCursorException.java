package ch.tarsier.tarsier.exception;

/**
 * @author gluthier
 */
public class InvalidCursorException extends Exception {

    public InvalidCursorException(String detailMessage) {
        super(detailMessage);
    }

    public InvalidCursorException(Exception e) {
        super(e);
    }
}
