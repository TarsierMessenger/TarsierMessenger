package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.Toast;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.UserRepository;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.validation.ChatroomNameValidator;

/**
 * @author gluthier
 */
public class NewChatRoomActivity extends Activity {

    private final static String CHAT_MESSAGE = "ch.tarsier.tarsier.ui.activity.CHAT";

    private EditText mChatroomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chatroom);

        mChatroomName = (EditText) findViewById(R.id.chat_room_name);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_chatroom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_chatroom:
                try {
                    createChatroom();
                } catch (InvalidCursorException e) {
                    e.printStackTrace();
                } catch (NoSuchModelException e) {
                    e.printStackTrace();
                } catch (InvalidModelException e) {
                    e.printStackTrace();
                } catch (InsertException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createChatroom()
            throws InvalidCursorException, NoSuchModelException, InvalidModelException, InsertException {

        if (validateChatRoomName()) {
            ChatRepository chatRepository = Tarsier.app().getChatRepository();
            UserRepository userRepository = Tarsier.app().getUserRepository();

            Chat newChatroom = new Chat();
            newChatroom.setPrivate(false);
            newChatroom.setTitle(mChatroomName.getText().toString());
            newChatroom.setHost(userRepository.getUser());

            chatRepository.insert(newChatroom);

            Intent newChatroomIntent = new Intent(this, ChatActivity.class);
            newChatroomIntent.putExtra(CHAT_MESSAGE, newChatroom);

            Toast.makeText(this, "TODO: start ChatActivity", Toast.LENGTH_SHORT).show();
            //TODO startActivity(newChatroomIntent);
        }
    }

    private boolean validateChatRoomName() {
        return new ChatroomNameValidator().validate(mChatroomName);
    }
}
