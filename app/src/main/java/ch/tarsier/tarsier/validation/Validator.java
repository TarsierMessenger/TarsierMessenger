package ch.tarsier.tarsier.validation;

/**
 * @author romac
 */
public interface Validator<T> {

    public boolean validate(T t);

    public boolean hasErrorMessage();
    public String getErrorMessage();

}
