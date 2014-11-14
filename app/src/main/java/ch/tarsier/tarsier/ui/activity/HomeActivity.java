package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
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
import ch.tarsier.tarsier.network.old.WiFiDirectDebugActivity;
import ch.tarsier.tarsier.validation.StatusMessageValidator;
import ch.tarsier.tarsier.validation.UsernameValidator;

/**
 * This is the Home screen of Tarsier. It allows to enter a Username
 * and initiate a session
 * @author Benjamin Paccaud.
 */
public class HomeActivity extends Activity {

    private EditText username;
    private EditText statusMessage;
    private ImageView mProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        enableStartButton(false);

        username = (EditText) findViewById(R.id.username);
        statusMessage = (EditText) findViewById(R.id.status_message);

        username.addTextChangedListener(new EditTextWatcher());
        statusMessage.addTextChangedListener(new EditTextWatcher());

        mProfilePicture = (ImageView) findViewById(R.id.picture);

    }

    /**
     * Toggle the clickable property of the lets_chat Button
     * @param enable true makes the Button clickable.
     */
    private void enableStartButton(boolean enable) {
        Button start = (Button) findViewById(R.id.lets_chat);
        start.setClickable(enable);
    }


    public void onClickLetsChat(View view) {
        //create intent with username and launch the list of rooms
        if (validateUsername()) {
            //continue
        } else {
            //show toast with information on invalidity
        }
        //debug
        Toast.makeText(this, "enabled", Toast.LENGTH_SHORT).show();
    }

    /**
     * Check if the username's length is within the bounds, and
     * set an error message otherwise.
     *
     * @return Whether it is valid or not
     */
    private boolean validateUsername() {
        return new UsernameValidator().validate(username);
    }

    /**
     * Check if the status message's length is within the bounds, and
     * set an error message otherwise.
     *
     * @return Whether it is valid or not
     */
    private boolean validateStatusMessage() {
        return new StatusMessageValidator().validate(statusMessage);
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

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_profile) {
            displayProfileActivity();
        } else if (id == R.id.action_wifidirectdebug) {
            displayWifiDirectDebugActivity();
        } else if (id == R.id.action_discussions) {
            displayDiscussionsActivity();
        }

        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.action_profile:
                displayProfileActivity();
                return true;

            case R.id.action_wifidirectdebug:
                displayWifiDirectDebugActivity();
                return true;

            case R.id.action_chat_room_participants:
                displayChatRoomParticipants();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //check existence of picture profile
        String filePath= Environment.getExternalStorageDirectory()
                              +"/"+AddProfilePictureActivity.TEMP_PHOTO_FILE;
        Bitmap profilePicture = BitmapFactory.decodeFile(filePath);
        if (profilePicture != null) {
            mProfilePicture.setImageBitmap(profilePicture);
        } else {
            mProfilePicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.add_picture_home));
        }
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
            enableStartButton(chatButtonCanBeEnabled());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

        private boolean chatButtonCanBeEnabled() {
            return validateFields();
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

    private void displayDiscussionsActivity() {
        Intent discussionsActivity= new Intent(this, ChatListActivity.class);
        startActivity(discussionsActivity);
    }

    private void displayChatRoomParticipants() {
        Intent displayChatRoomParticipants = new Intent(this, ChatRoomParticipantsActivity.class);
        startActivity(displayChatRoomParticipants);
    }

}

