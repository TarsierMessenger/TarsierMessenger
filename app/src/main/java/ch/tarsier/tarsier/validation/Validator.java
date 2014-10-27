package ch.tarsier.tarsier.validation;

/**
 * @author Romain Ruetschi
 * @param <T> The type of the elements to validate.
 */
public interface Validator<T> {

    boolean validate(T t);

    boolean hasErrorMessage();
    String getErrorMessage();
}
