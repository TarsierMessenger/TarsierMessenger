package ch.tarsier.tarsier.validation;

import android.widget.EditText;

/**
 * Created by benjamin on 09/11/14.
 */
public class EditTextNoWhitespaceValidator extends EditTextValidator {

    public EditTextNoWhitespaceValidator(String errorMessage) {
        setErrorMessage(errorMessage);
    }

    @Override
    protected boolean isValid(EditText editText) {
        return (editText.getText().length() == 0 || editText.getText().toString().trim().length() != 0);
    }
}
