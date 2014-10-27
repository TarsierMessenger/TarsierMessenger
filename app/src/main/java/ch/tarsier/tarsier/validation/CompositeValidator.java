package ch.tarsier.tarsier.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author romac
 */
public class CompositeValidator<T> extends AbstractValidator<T> {

    List<Validator<T>> mValidators;

    public CompositeValidator() {
        this(new ArrayList<Validator<T>>());
    }

    public CompositeValidator(List<Validator<T>> validators) {
        mValidators = validators;
    }

    public void addValidator(Validator<T> validator) {
        mValidators.add(validator);
    }

    @Override
    protected boolean isValid(T t) {
        for (Validator<T> validator : mValidators) {
            if (!validator.validate(t)) {
                if (validator.hasErrorMessage()) {
                    setErrorMessage(validator.getErrorMessage());
                }
                return false;
            }
        }

        return true;
    }

}
