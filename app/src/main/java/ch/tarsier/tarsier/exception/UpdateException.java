package ch.tarsier.tarsier.exception;

/**
 * UpdateException is the exception for the update query in the database.
 *
 * @author gluthier
 */
public class UpdateException extends DomainException {

    public UpdateException(String detailMessage) {
        super(detailMessage);
    }
}
