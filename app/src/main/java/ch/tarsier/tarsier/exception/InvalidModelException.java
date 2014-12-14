package ch.tarsier.tarsier.exception;

/**
 * InvalidModelException is the exception for the Model's operations.
 *
 * @author gluthier
 */
public class InvalidModelException extends DomainException {

    public InvalidModelException(String detailMessage) {
        super(detailMessage);
    }
}
