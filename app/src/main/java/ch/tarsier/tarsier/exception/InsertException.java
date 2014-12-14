package ch.tarsier.tarsier.exception;

/**
 * InsertException is the exception for the insert query in the database.
 *
 * @author gluthier
 */
public class InsertException extends DomainException {

    public InsertException(String detailMessage) {
        super(detailMessage);
    }
}
