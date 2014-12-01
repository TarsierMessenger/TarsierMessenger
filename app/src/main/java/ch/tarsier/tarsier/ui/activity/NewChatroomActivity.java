package ch.tarsier.tarsier.ui.activity;

import com.squareup.otto.Bus;

import android.app.ActionBar;
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
import ch.tarsier.tarsier.event.CreateGroupEvent;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.validation.ChatroomNameValidator;

/**
 * @author gluthier
 */
public class NewChatroomActivity extends Activity {

    private final static String CHAT_MESSAGE = "ch.tarsier.tarsier.ui.activity.CHAT";

    private EditText mChatroomName;

    private Bus mEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chatroom);

        mChatroomName = (EditText) findViewById(R.id.chatroom_name);
        mEventBus = Tarsier.app().getEventBus();

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
        }
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

        if (!validateChatRoomName()) {
            Toast.makeText(this, "The chatroom name is invalid.", Toast.LENGTH_SHORT).show();
            return;
        }

        ChatRepository chatRepository = Tarsier.app().getChatRepository();
        UserRepository userRepository = Tarsier.app().getUserRepository();

        Chat newChatroom = new Chat();
        newChatroom.setPrivate(false);
        newChatroom.setTitle(mChatroomName.getText().toString());
        newChatroom.setHost(userRepository.getUser());

        chatRepository.insert(newChatroom);

        mEventBus.post(new CreateGroupEvent(newChatroom));

        Intent newChatroomIntent = new Intent(this, ChatActivity.class);
        newChatroomIntent.putExtra(ChatActivity.EXTRA_CHAT_MESSAGE_KEY, newChatroom);

        startActivity(newChatroomIntent);
    }

    private boolean validateChatRoomName() {
        return new ChatroomNameValidator().validate(mChatroomName);
    }
}
