package ch.tarsier.tarsier.exception;

/**
 * @author gluthier
 */
public class NoSuchModelException extends Exception {

    public NoSuchModelException() {
        super();
    }

    public NoSuchModelException(String detailMessage) {
        super(detailMessage);
    }
}
