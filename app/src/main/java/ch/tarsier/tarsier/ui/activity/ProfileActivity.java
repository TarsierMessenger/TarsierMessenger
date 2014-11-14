package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.storage.StorageAccess;
import ch.tarsier.tarsier.validation.StatusMessageValidator;
import ch.tarsier.tarsier.validation.UsernameValidator;

/**
 * @author Romain Ruetschi (romac)
 */
public class ProfileActivity extends Activity {

    private StorageAccess storage;

    private EditText mUsername;
    private EditText mStatusMessage;
    private ImageView mProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        storage = Tarsier.app().getStorage();

        mUsername = (EditText) findViewById(R.id.username);
        mStatusMessage = (EditText) findViewById(R.id.status_message);
        mProfilePicture = (ImageView) findViewById(R.id.add_picture_button);

        refreshFields();
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
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when a user click on the "Add Picture" image button.
     *
     * @param view The "Add Picture" image button view
     */
    public void onClickAddPicture(View view) {
        Intent pictureIntent = new Intent(this, AddProfilePictureActivity.class);
        startActivity(pictureIntent);
    }

    public void onClickCancel(MenuItem item) {
        finish();
    }

    public void onClickSave(MenuItem item) {
        if (validateFields()) {
            storage.setMyUsername(mUsername.getText().toString());
            storage.setMyMood(mStatusMessage.getText().toString());

            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshFields();
    }

    private void refreshFields() {
        String username = storage.getMyUsername();
        mUsername.setText(username);

        String statusMessage = storage.getMyMood();
        mStatusMessage.setText(statusMessage);

        // check existence of picture profile
        String filePath = Environment.getExternalStorageDirectory() + "/"
                + AddProfilePictureActivity.TEMP_PHOTO_FILE;

        Bitmap profilePicture = BitmapFactory.decodeFile(filePath);

        if (profilePicture == null) {
            profilePicture = BitmapFactory.decodeResource(getResources(), R.drawable.add_picture_home);
        }

        mProfilePicture.setImageBitmap(profilePicture);
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
