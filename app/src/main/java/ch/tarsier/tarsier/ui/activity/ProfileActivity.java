package ch.tarsier.tarsier.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.prefs.UserPreferences;
import ch.tarsier.tarsier.validation.StatusMessageValidator;
import ch.tarsier.tarsier.validation.UsernameValidator;

/**
 * Displays and allow editing the user's username, status message and profile picture.
 *
 * @author romac
 */
public class ProfileActivity extends Activity {

    private UserPreferences mUserPreferences;

    private EditText mUsername;
    private EditText mStatusMessage;
    private ImageView mProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mUserPreferences = Tarsier.app().getUserPreferences();

        mUsername = (EditText) findViewById(R.id.username);
        mStatusMessage = (EditText) findViewById(R.id.status_message_profile_activity);
        mProfilePicture = (ImageView) findViewById(R.id.add_picture_button);

        refreshFields();

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a user click on the "Add Picture" image button.
     * Launches {@link ch.tarsier.tarsier.ui.activity.AddProfilePictureActivity}.
     *
     * @param view The "Add Picture" image button view
     */
    public void onClickAddPicture(View view) {
        Intent pictureIntent = new Intent(this, AddProfilePictureActivity.class);
        startActivity(pictureIntent);
    }

    /**
     * Save profile informations to the user preferences if those are valid.
     *
     * @param item unused
     */
    public void onClickSave(MenuItem item) {
        if (validateFields()) {
            saveProfileInfos();

            finish();
        }
    }

    /**
     * Refresh the displayed information when the activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();

        refreshFields();
    }

    /**
     * Refresh the displayed information.
     */
    private void refreshFields() {
        String username = mUserPreferences.getUsername();
        mUsername.setText(username);

        String statusMessage = mUserPreferences.getStatusMessage();
        mStatusMessage.setText(statusMessage);

        // check existence of picture profile
        String filePath = mUserPreferences.getPicturePath();

        Bitmap profilePicture = BitmapFactory.decodeFile(filePath);

        if (profilePicture == null) {
            profilePicture = BitmapFactory.decodeResource(getResources(), R.drawable.add_picture);
        }

        mProfilePicture.setImageBitmap(profilePicture);
    }

    /**
     * Save the username and status message into the user preferences.
     */
    private void saveProfileInfos() {
        mUserPreferences.setUsername(mUsername.getText().toString());
        mUserPreferences.setStatusMessage(mStatusMessage.getText().toString());
    }

    /**
     * Validate the username and status message.
     *
     * @return Whether or not both are valid.
     */
    private boolean validateFields() {
        return validateUsername() && validateStatusMessage();
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
}
