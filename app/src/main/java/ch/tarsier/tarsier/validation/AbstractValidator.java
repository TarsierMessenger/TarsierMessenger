package ch.tarsier.tarsier.validation;

/**
 * @author romac
 */
public abstract class AbstractValidator<T> implements Validator<T> {

    private String mErrorMessage = null;

    protected abstract boolean isValid(T t);

    @Override
    public boolean validate(T t) {
        return isValid(t);
    }

    @Override
    public boolean hasErrorMessage() {
        return mErrorMessage != null;
    }

    @Override
    public String getErrorMessage() {
        return mErrorMessage;
    }

    protected void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }
}
