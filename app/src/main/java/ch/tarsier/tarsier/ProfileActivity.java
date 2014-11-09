package ch.tarsier.tarsier;

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

import ch.tarsier.tarsier.validation.StatusMessageValidator;
import ch.tarsier.tarsier.validation.UsernameValidator;

/**
 * @author Romain Ruetschi (romac)
 */
public class ProfileActivity extends Activity {

    private EditText username;
    private EditText statusMessage;
    private ImageView mProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = (EditText) findViewById(R.id.username);
        statusMessage = (EditText) findViewById(R.id.status_message);
        mProfilePicture = (ImageView) findViewById(R.id.add_picture_button);
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
            Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid!", Toast.LENGTH_SHORT).show();
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
}
