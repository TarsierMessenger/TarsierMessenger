package ch.tarsier.tarsier.exception;

/**
 * InvalidCursorException is the exception for the cursor operations.
 *
 * @author gluthier
 */
public class InvalidCursorException extends DomainException {

    public InvalidCursorException(String detailMessage) {
        super(detailMessage);
    }

    public InvalidCursorException(Exception e) {
        super(e);
    }
}
