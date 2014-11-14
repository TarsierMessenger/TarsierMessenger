package ch.tarsier.tarsier.ui.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.User;

/**
 * @author Romain Ruetschi
 */
public class ChatRoomParticipantsActivity extends ListActivity {

    public final static String EXTRAS_PARTICIPANTS_KEY = "participants";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent sender = getIntent();
        Bundle extras = sender.getExtras();

        Peer[] peers;

        if (extras != null && extras.containsKey(EXTRAS_PARTICIPANTS_KEY)) {
            peers = (User[]) extras.get(EXTRAS_PARTICIPANTS_KEY);
        } else {
            // Just some test data in case we got nothing from the parent,
            // as it is the case when accessing this view from the menu.
            peers = new Peer[] {
                new Peer("Amirezza Bahreini", "At Sat', come join me !"),
                new Peer("Frederic Jacobs", "Tarsier will beat ISIS !"),
                new Peer("Gabriel Luthier", "There's no place like 127.0.0.1"),
                new Peer("Radu Banabic", "Happy coding !"),
                new Peer("Romain Ruetschi", "Let me rewrite this in Haskell, please.")
            };
        }

        setListAdapter(new ChatRoomParticipantArrayAdapter(this, peers));
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

    class ChatRoomParticipantArrayAdapter extends ArrayAdapter<Peer> {
        private final static int LAYOUT = R.layout.chatroom_participant;

        private final Context mContext;
        private final LayoutInflater mInflater;
        private final Peer[] mValues;

        ChatRoomParticipantArrayAdapter(Context context, Peer[] values) {
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
                convertView.setTag(R.id.badge, convertView.findViewById(R.id.badge));
            }

            View rowView = convertView;

            TextView nameView = (TextView) convertView.getTag(R.id.name);
            TextView statusView = (TextView) convertView.getTag(R.id.status_message);
            ImageView imageView = (ImageView) convertView.getTag(R.id.icon);
            TextView badgeView = (TextView) convertView.getTag(R.id.badge);

            Peer p = getItem(position);

            nameView.setText(p.getName());
            statusView.setText(p.getStatusMessage());
            imageView.setImageURI(Uri.parse(p.getPicturePath()));
            imageView.setImageResource(R.drawable.ic_launcher);
            badgeView.setVisibility((p.isOnline()) ? View.VISIBLE : View.INVISIBLE);

            return rowView;
        }
    }
}
