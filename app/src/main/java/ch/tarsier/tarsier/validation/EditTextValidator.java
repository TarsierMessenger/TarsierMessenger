package ch.tarsier.tarsier.validation;

import android.widget.EditText;

/**
 * EditTextValidator is the class that validates an EditText.
 *
 * @author romac
 */
public abstract class EditTextValidator extends AbstractValidator<EditText> {

    protected EditTextValidator() {

    }

    @Override
    public boolean validate(EditText editText) {
        boolean valid = isValid(editText);

        if (!valid && hasErrorMessage()) {
            editText.setError(getErrorMessage());
        }

        return valid;
    }

}
