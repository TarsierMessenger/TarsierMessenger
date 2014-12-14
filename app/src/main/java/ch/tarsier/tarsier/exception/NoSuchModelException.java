package ch.tarsier.tarsier.exception;

/**
 * NoSuchModelException is the exceptions for when the Model is not found.
 *
 * @author gluthier
 */
public class NoSuchModelException extends DomainException {

    public NoSuchModelException(String detailMessage) {
        super(detailMessage);
    }

    public NoSuchModelException(Exception e) {
        super(e);
    }
}
