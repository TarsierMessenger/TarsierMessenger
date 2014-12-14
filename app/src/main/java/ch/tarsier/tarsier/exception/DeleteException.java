package ch.tarsier.tarsier.exception;

/**
 * DeleteException is the exception for the delete query in the database.
 *
 * @author gluthier
 */
public class DeleteException extends DomainException {

    public DeleteException() {
        super();
    }

    public DeleteException(String detailMessage) {
        super(detailMessage);
    }
}
