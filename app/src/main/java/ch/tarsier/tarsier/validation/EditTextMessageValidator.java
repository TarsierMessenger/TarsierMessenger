package ch.tarsier.tarsier.validation;

import android.widget.EditText;

/**
 * Created by Marin on 16.11.2014.
 */
public class EditTextMessageValidator extends EditTextValidator {

    public EditTextMessageValidator(String errorMessage){
        setErrorMessage(errorMessage);
    }

    @Override
    protected boolean isValid(EditText editText) {
        return (editText.getText().length()!=0);
    }
}
