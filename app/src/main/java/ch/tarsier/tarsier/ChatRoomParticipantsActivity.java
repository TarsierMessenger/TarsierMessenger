package ch.tarsier.tarsier;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Romain Ruetschi
 */
public class ChatRoomParticipantsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ChatRoomParticipant[] values = new ChatRoomParticipant[] {
            new ChatRoomParticipant("Amirezza Bahreini", "At Sat', come join me !"),
            new ChatRoomParticipant("Frederic Jacobs", "Tarsier will beat ISIS !"),
            new ChatRoomParticipant("Gabriel Luthier", "There's no place like 127.0.0.1"),
            new ChatRoomParticipant("Radu Banabic", "Happy coding !"),
            new ChatRoomParticipant("Romain Ruetschi", "Let me rewrite this in Haskell, please.")
        };

        setListAdapter(new ChatRoomParticipantArrayAdapter(this, values));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat_room_participants, menu);
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

    class ChatRoomParticipantArrayAdapter extends ArrayAdapter<ChatRoomParticipant> {
        private final static int LAYOUT = R.layout.chatroom_participant;

        private final Context mContext;
        private final LayoutInflater mInflater;
        private final ChatRoomParticipant[] mValues;

        ChatRoomParticipantArrayAdapter(Context context, ChatRoomParticipant[] values) {
            super(context, LAYOUT, values);

            mContext = context;
            mInflater = getLayoutInflater();
            mValues = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(LAYOUT, parent, false);
                convertView.setTag(R.id.name, convertView.findViewById(R.id.name));
                convertView.setTag(R.id.status_message, convertView.findViewById(R.id.status_message));
                convertView.setTag(R.id.icon, convertView.findViewById(R.id.icon));
            }

            View rowView = convertView;

            TextView nameView = (TextView) convertView.getTag(R.id.name);
            TextView statusView = (TextView) convertView.getTag(R.id.status_message);
            ImageView imageView = (ImageView) convertView.getTag(R.id.icon);

            ChatRoomParticipant p = getItem(position);

            nameView.setText(p.getName());
            statusView.setText(p.getStatusMessage());
            imageView.setImageResource(R.drawable.ic_launcher);

            return rowView;
        }
    }
}
