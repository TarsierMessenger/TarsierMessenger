package ch.tarsier.tarsier.exception;

/**
 * DomainException is the exception for the queries in the database.
 *
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
