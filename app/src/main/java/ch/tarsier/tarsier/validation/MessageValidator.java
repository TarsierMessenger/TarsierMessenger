package ch.tarsier.tarsier.validation;

import android.widget.EditText;

/**
 * Created by Marin on 16.11.2014.
 */
public class MessageValidator extends EditTextValidator {

    public MessageValidator(String errorMessage){
        setErrorMessage(errorMessage);
    }

    @Override
    protected boolean isValid(EditText editText) {
        return editText.getText().length() != 0;
    }
}
