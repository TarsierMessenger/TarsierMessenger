package ch.tarsier.tarsier.validation;

import android.widget.EditText;

/**
 * EditTextLengthValidator is the class that validates an EditText for authorized length.
 *
 * @author romac
 */
public class EditTextLengthValidator extends EditTextValidator {

    private int mMinLength = 0;
    private int mMaxLength = Integer.MAX_VALUE;

    public EditTextLengthValidator(int minLength, int maxLength, String errorMessage) {
        mMinLength = Math.max(0, minLength);
        mMaxLength = Math.max(0, maxLength);

        setErrorMessage(errorMessage);
    }

    public EditTextLengthValidator(int length, String errorMessage) {
        this(length, length, errorMessage);
    }

    @Override
    public boolean isValid(EditText editText) {
        int length = editText.getText().length();

        return length >= mMinLength && length <= mMaxLength;
    }
}
