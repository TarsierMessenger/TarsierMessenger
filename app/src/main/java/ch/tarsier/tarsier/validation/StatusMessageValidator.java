package ch.tarsier.tarsier.validation;

import android.widget.EditText;

/**
 * @author romac
 */
public class StatusMessageValidator extends CompositeValidator<EditText> {

    private static final int MIN_STATUS_LENGTH   = 1;
    private static final int MAX_STATUS_LENGTH   = 140;

    public StatusMessageValidator() {
        super();

        this.addValidator(
            new EditTextLengthValidator(MIN_STATUS_LENGTH, MAX_STATUS_LENGTH)
        );
    }

}
