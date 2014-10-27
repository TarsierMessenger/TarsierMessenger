package ch.tarsier.tarsier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is the Home screen of Tarsier. It allows to enter a Username
 * and initiate a session
 * @author Benjamin Paccaud.
 */
public class HomeActivity extends Activity {

    static private final int MIN_USERNAME_LENGTH = 1;
    static private final int MIN_STATUS_LENGTH = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        enableStartButton(false);
        ((EditText) findViewById(R.id.username)).addTextChangedListener(new EditTextWatcher());
        ((EditText) findViewById(R.id.status_message)).addTextChangedListener(new EditTextWatcher());

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
        EditText username = (EditText) findViewById(R.id.username);
        if (isValidUsername(username.getText().toString())) {
            //continue
        } else {
            //show toast with information on invalidity
        }
        //debug
        Toast.makeText(this, "enabled", Toast.LENGTH_SHORT).show();
    }

    private boolean isValidUsername(String username) {
        //check on the string (length)
        return true;
    }

    public void onClickAddPicture(View view) {
        //launch Activity to have the user choose the picture
        Intent picture = new Intent(this, AddProfilePictureActivity.class);
        this.startActivity(picture);
        //debug
        //Toast.makeText(this, "add picture", Toast.LENGTH_SHORT).show();
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
        }
        return super.onOptionsItemSelected(item);
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
            if (chatButtonCanBeEnabled()) {
                enableStartButton(true);
            } else {
                enableStartButton(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

        private boolean chatButtonCanBeEnabled() {
            if ((((EditText) findViewById(R.id.status_message)).getText().length() >= MIN_STATUS_LENGTH)
                    && (((EditText) findViewById(R.id.username)).getText().length() >= MIN_USERNAME_LENGTH)) {
                return true;
            }
            return false;
        }
    }

}