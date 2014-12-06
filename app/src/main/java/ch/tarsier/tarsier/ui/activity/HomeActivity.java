package ch.tarsier.tarsier.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.prefs.UserPreferences;
import ch.tarsier.tarsier.util.BitmapFromPath;
import ch.tarsier.tarsier.validation.StatusMessageValidator;
import ch.tarsier.tarsier.validation.UsernameValidator;


/**
 * This is the Home screen of Tarsier. It allows to enter a Username
 * and initiate a session
 * @author Benjamin Paccaud.
 */
public class HomeActivity extends Activity {

    private EditText mUsername;
    private EditText mStatusMessage;
    private ImageView mProfilePicture;

    private UserPreferences mUserPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toggleStartButton(false);

        mUserPreferences = Tarsier.app().getUserPreferences();

        mUsername = (EditText) findViewById(R.id.username_home);
        mStatusMessage = (EditText) findViewById(R.id.status_message_home);
        mProfilePicture = (ImageView) findViewById(R.id.picture);

        mUsername.addTextChangedListener(new EditTextWatcher());
        mStatusMessage.addTextChangedListener(new EditTextWatcher());


        if (!mUserPreferences.getUsername().equals("")
            && !mUserPreferences.getStatusMessage().equals("")) {

            Intent chatListIntent = new Intent(this, ChatListActivity.class);
            startActivity(chatListIntent);
            this.finish();
        }

        refreshFields();
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    /**
     * Toggle the clickable property of the lets_chat Button
     * @param enable true makes the Button clickable.
     */
    private void toggleStartButton(boolean enable) {
        Button start = (Button) findViewById(R.id.lets_chat);
        start.setClickable(enable);
    }


    public void onClickLetsChat(View view) {
        //create intent with username and launch the list of rooms
        if (validateFields()) {
            // FIXME: should create key the first time the user connects
            saveProfileInfos();
            //remove this activity from the stack.
            this.finish();
            Intent nearbyIntent = new Intent(this, NearbyListActivity.class);
            startActivity(nearbyIntent);
        } else {
            Toast.makeText(this, getString(R.string.error_lets_chat_toast), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Check if the username's length is within the bounds, and
     * set an error message otherwise.
     *
     * @return Whether it is valid or not
     */
    private boolean validateUsername() {
        return new UsernameValidator().validate(mUsername);
    }

    /**
     * Check if the status message's length is within the bounds, and
     * set an error message otherwise.
     *
     * @return Whether it is valid or not
     */
    private boolean validateStatusMessage() {
        return new StatusMessageValidator().validate(mStatusMessage);
    }

    /**
     * Validate the username and status message.
     *
     * @return Whether or not both are valid.
     */
    private boolean validateFields() {
        boolean valid;
        valid = validateStatusMessage();
        valid = validateUsername() && valid;
        return valid;
    }

    public void onClickAddPicture(View view) {
        //launch Activity to have the user choose the picture
        Intent picture = new Intent(this, AddProfilePictureActivity.class);
        this.startActivity(picture);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.action_profile:
                displayProfileActivity();
                return true;

            case R.id.action_wifidirectdebug:
                displayWifiDirectDebugActivity();
                return true;

            case R.id.action_chatroom_peers:
                displayChatroomPeers();
                return true;

            case R.id.action_chats_list:
                displayChatsListActivity();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshFields();
    }

    private void refreshFields() {
        String username = mUserPreferences.getUsername();
        mUsername.setText(username);

        String statusMessage = mUserPreferences.getStatusMessage();
        mStatusMessage.setText(statusMessage);

        // check existence of picture profile. put default if non existent.
        String filePath = mUserPreferences.getPicturePath();
        Bitmap profilePicture = BitmapFromPath.getBitmapFromPath(this, filePath);
        mProfilePicture.setImageBitmap(profilePicture);

        validateFields();
    }

    private void saveProfileInfos() {
        mUserPreferences.setUsername(mUsername.getText().toString());
        mUserPreferences.setStatusMessage(mStatusMessage.getText().toString());
    }

    /**
     * Verify that we can enable the Button that initiate the session.
     * by checking the EditText s of the Activity
     */
    private final class EditTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (validateFields()) {
                saveProfileInfos();
                toggleStartButton(true);
            } else {
                toggleStartButton(false);
            }
        }
    }

    public void displayProfileActivity() {
        Intent displayProfileIntent = new Intent(this, ProfileActivity.class);
        startActivity(displayProfileIntent);
    }

    private void displayWifiDirectDebugActivity() {
        Intent displayWifiDirectDebugIntent = new Intent(this, WiFiDirectDebugActivity.class);
        startActivity(displayWifiDirectDebugIntent);
    }

    private void displayChatsListActivity() {
        Intent chatsListActivity = new Intent(this, ChatListActivity.class);
        startActivity(chatsListActivity);
    }

    private void displayChatroomPeers() {
        Intent displayChatroomPeers = new Intent(this, ChatroomPeersActivity.class);
        startActivity(displayChatroomPeers);
    }

}

