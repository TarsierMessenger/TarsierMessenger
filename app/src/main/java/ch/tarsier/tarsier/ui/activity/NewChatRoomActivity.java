package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.prefs.UserPreferences;
import ch.tarsier.tarsier.validation.ChatroomNameValidator;

/**
 * @author gluthier
 */
public class NewChatRoomActivity extends Activity {

    private final static String ID_NEW_CHATROOM_MESSAGE = "ch.tarsier.tarsier.ui.activity.ID_NEW_CHATROOM";

    private EditText mChatroomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chatroom);

        mChatroomName = (EditText) findViewById(R.id.chatroom_name);

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
            case R.id.new_chatroom:
                try {
                    createNewChatroom();
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

    private void createNewChatroom()
            throws InvalidCursorException, NoSuchModelException, InvalidModelException, InsertException {

        if (validateChatRoomName()) {
            ChatRepository chatRepository = Tarsier.app().getChatRepository();
            PeerRepository peerRepository = Tarsier.app().getPeerRepository();
            UserPreferences userPreferences= Tarsier.app().getUserPreferences();

            Peer user = peerRepository.findById(userPreferences.getId());

            Chat newChatroom = new Chat();
            newChatroom.setPrivate(false);
            newChatroom.setTitle(mChatroomName.getText().toString());
            newChatroom.setHost(user);

            chatRepository.insert(newChatroom);

            Intent newChatroomIntent = new Intent(this, ConversationActivity.class);
            newChatroomIntent.putExtra(ID_NEW_CHATROOM_MESSAGE, newChatroom.getId());
            startActivity(newChatroomIntent);
        }
    }

    private boolean validateChatRoomName() {
        return new ChatroomNameValidator().validate(mChatroomName);
    }
}