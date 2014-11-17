package ch.tarsier.tarsier.validation;

import android.widget.EditText;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;

/**
 * @author gluthier
 */
public class ChatroomNameValidator extends CompositeValidator<EditText> {
    
    private static final int MIN_CHAT_ROOM_NAME_LENGTH = 1;
    private static final int MAX_CHAT_ROOM_NAME_LENGTH = 36;
    
    public ChatroomNameValidator() {
        addValidator(
            new EditTextLengthValidator(
                MIN_CHAT_ROOM_NAME_LENGTH,
                MAX_CHAT_ROOM_NAME_LENGTH,
                Tarsier.app().getResources().getString(R.string.error_chat_room_name_length)
            )
        );
    }
}
