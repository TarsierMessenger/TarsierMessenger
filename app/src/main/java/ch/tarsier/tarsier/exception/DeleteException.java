package ch.tarsier.tarsier.exception;

/**
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
