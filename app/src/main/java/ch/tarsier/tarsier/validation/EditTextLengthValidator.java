package ch.tarsier.tarsier.validation;

import android.widget.EditText;

/**
 * @author Romain Ruetschi
 */
public class EditTextLengthValidator extends EditTextValidator {

    private int mMinLength = 0;
    private int mMaxLength = Integer.MAX_VALUE;

    public EditTextLengthValidator(int minLength, int maxLength) {
        mMinLength = Math.max(0, minLength);
        mMaxLength = Math.max(0, maxLength);

        setErrorMessage("Length should be between " + mMinLength + " and " + mMaxLength + ".");
    }

    public EditTextLengthValidator(int length) {
        this(length, length);

        setErrorMessage("Length should be " + mMinLength + ".");
    }

    @Override
    public boolean isValid(EditText editText) {
        int length = editText.getText().length();

        return length >= mMinLength && length <= mMaxLength;
    }
}
