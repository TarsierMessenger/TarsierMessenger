package ch.tarsier.tarsier.validation;

import android.widget.EditText;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;

/**
 * UsernameValidator is the class that validates the username.
 *
 * @author romac
 */
public class UsernameValidator extends CompositeValidator<EditText> {

    private static final int MIN_USERNAME_LENGTH = 1;
    private static final int MAX_USERNAME_LENGTH = 36;

    public UsernameValidator() {
        addValidator(
                new EditTextLengthValidator(
                        MIN_USERNAME_LENGTH,
                        MAX_USERNAME_LENGTH,
                        Tarsier.app().getResources().getString(R.string.error_username_length)
                )
        );
        addValidator(new EditTextNoWhitespaceValidator(
                Tarsier.app().getResources().getString(R.string.error_username_whitespace)
        ));
    }

}
