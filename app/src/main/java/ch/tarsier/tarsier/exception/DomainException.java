package ch.tarsier.tarsier.exception;

/**
 * @author romac
 */
public class DomainException extends Exception {

    public DomainException() {
        super();
    }

    public DomainException(String msg) {
        super(msg);
    }

    public DomainException(Exception e) {
        super(e);
    }
}
