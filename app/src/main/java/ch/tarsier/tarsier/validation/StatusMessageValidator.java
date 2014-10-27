package ch.tarsier.tarsier.validation;

import android.widget.EditText;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;

/**
 * @author Romain Ruetschi
 */
public class StatusMessageValidator extends CompositeValidator<EditText> {

    private static final int MIN_STATUS_LENGTH   = 1;
    private static final int MAX_STATUS_LENGTH   = 50;

    public StatusMessageValidator() {
        addValidator(
            new EditTextLengthValidator(
                MIN_STATUS_LENGTH,
                MAX_STATUS_LENGTH,
                Tarsier.app().getResources().getString(R.string.error_status_message_length)
            )
        );
    }

}
