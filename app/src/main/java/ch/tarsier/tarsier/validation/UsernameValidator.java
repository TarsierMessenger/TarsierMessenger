package ch.tarsier.tarsier.validation;

import android.widget.EditText;

/**
 * @author romac
 */
public class UsernameValidator extends CompositeValidator<EditText> {

    private static final int MIN_USERNAME_LENGTH = 1;
    private static final int MAX_USERNAME_LENGTH = 36;

    public UsernameValidator() {
        super();

        this.addValidator(
            new EditTextLengthValidator(MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH)
        );
    }

}
