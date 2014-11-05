package ch.tarsier.tarsier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class NewDiscussionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_discussion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.create_room:
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
        /*
        TODO verify user input
        TODO create AddNewDiscussionActivity class OR link to the good class
        Intent newRoomIntent = new Intent(this, AddNewDiscussionActivity.class);
        startActivity(newRoomIntent);
        */
        Toast.makeText(this, "create room", Toast.LENGTH_SHORT).show();
    }
}
