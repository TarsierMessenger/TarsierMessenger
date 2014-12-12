package ch.tarsier.tarsier.exception;

/**
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
