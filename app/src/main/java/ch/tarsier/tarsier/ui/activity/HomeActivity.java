package ch.tarsier.tarsier.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.User;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;
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

    private static final String TAG = "HomeActivity";
    private EditText mUsername;
    private EditText mStatusMessage;
    private ImageView mProfilePicture;
    private UserPreferences mUserPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mUserPreferences = Tarsier.app().getUserPreferences();

        mUsername = (EditText) findViewById(R.id.username_home);
        mStatusMessage = (EditText) findViewById(R.id.status_message_home);
        mProfilePicture = (ImageView) findViewById(R.id.picture);

        if (!mUserPreferences.getUsername().equals("")
            && !mUserPreferences.getStatusMessage().equals("")) {

            this.finish();
            Intent chatListIntent = new Intent(this, ChatListActivity.class);
            startActivity(chatListIntent);
        }

        Button start = (Button) findViewById(R.id.lets_chat);
        start.setClickable(true);

        refreshFields();

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }

    }

    public void onClickLetsChat(View view) {
        //create intent with username and launch the list of rooms
        this.validateUsername();
        this.validateStatusMessage();

        if (validateFields()) {
            // FIXME: should create key the first time the user connects
            saveProfileInfos();
            //remove this activity from the stack.
            this.finish();
            Intent nearbyIntent = new Intent(this, NearbyListActivity.class);
            nearbyIntent.putExtra(NearbyListActivity.EXTRA_FROM_HOME_KEY, true);
            startActivity(nearbyIntent);
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
        return validateUsername() && validateStatusMessage();
    }

    public void onClickAddPicture(View view) {
        //launch Activity to have the user choose the picture
        Intent picture = new Intent(this, AddProfilePictureActivity.class);
        this.startActivity(picture);
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
    }

    private void saveProfileInfos() {
        mUserPreferences.setUsername(mUsername.getText().toString());
        mUserPreferences.setStatusMessage(mStatusMessage.getText().toString());

        User user = Tarsier.app().getUserRepository().getUser();
        try {
            Tarsier.app().getPeerRepository().insert(user);
        } catch (InvalidModelException | InsertException e) {
            e.printStackTrace();
        }
    }

}

