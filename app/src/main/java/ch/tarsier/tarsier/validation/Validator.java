package ch.tarsier.tarsier.validation;

/**
 * Validator is the interface for the validators.
 *
 * @author romac
 * @param <T> The type of the elements to validate.
 */
public interface Validator<T> {

    boolean validate(T t);

    boolean hasErrorMessage();
    String getErrorMessage();
}
