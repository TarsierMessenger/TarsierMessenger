package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.validation.ChatroomNameValidator;

/**
 * @author gluthier
 */
public class NewChatRoomActivity extends Activity {

    private EditText mChatRoomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chatroom);

        mChatRoomName = (EditText) findViewById(R.id.chat_room_name);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_chatroom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.create_chatroom:
                createRoom();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onInvitationOnlyCheckboxClicked(View view) {
        TextView informationInvitation = (TextView) this.findViewById(R.id.information_invitation);

        if (((CheckBox) view).isChecked()) {
            informationInvitation.setText(R.string.information_invitation_close);
        } else {
            informationInvitation.setText(R.string.information_invitation_open);
        }
    }

    private void createRoom() {
        if (validateChatRoomName()) {
            Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show();
            /*
            TODO create AddNewDiscussionActivity class OR link to the good class
            Intent newRoomIntent = new Intent(this, AddNewDiscussionActivity.class);
            startActivity(newRoomIntent);
            */
        } else {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateChatRoomName() {
        return new ChatroomNameValidator().validate(mChatRoomName);
    }
}
