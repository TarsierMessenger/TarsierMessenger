package ch.tarsier.tarsier.ui.activity;

import com.squareup.otto.Subscribe;

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
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.User;
import ch.tarsier.tarsier.event.ReceivedChatroomPeersListEvent;

/**
 * @author romac
 */
public class ChatroomPeersActivity extends ListActivity {

    public static final String EXTRAS_CHAT_KEY = "chat";
    public final static String EXTRAS_PEERS_KEY = "peers";

    private ChatroomPeersArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpData();

        Tarsier.app().getEventBus().register(this);
    }

    @Subscribe
    public void receivedNewPeersList(ReceivedChatroomPeersListEvent event) {
        mAdapter.clear();
        mAdapter.addAll(event.getPeers());
    }

    private void setUpData() {
        Intent sender = getIntent();
        Bundle extras = sender.getExtras();

        if (extras == null || !hasExtrasData(extras)) {
            setUpWithTestData();
            return;
        }

        Chat chat = (Chat) extras.getSerializable(EXTRAS_CHAT_KEY);
        Peer[] peers = (User[]) extras.getSerializable(EXTRAS_PEERS_KEY);

        setAdapter(new ChatroomPeersArrayAdapter(this, chat, peers));
    }

    private void setUpWithTestData() {
        Peer host = new Peer("Amirezza Bahreini", "At Sat', come join me !");

        Chat chat = new Chat();
        chat.setHost(host);
        chat.setTitle("Tarsier rocks!");
        chat.setPrivate(false);

        Peer[] peers = new Peer[]{
                host,
                new Peer("Frederic Jacobs", "Tarsier will beat ISIS !"),
                new Peer("Gabriel Luthier", "There's no place like 127.0.0.1"),
                new Peer("Radu Banabic", "Happy coding !"),
                new Peer("Romain Ruetschi", "Let me rewrite this in Haskell, please.")
        };

        peers[0].setOnline(true);
        peers[4].setOnline(true);

        setAdapter(new ChatroomPeersArrayAdapter(this, chat, peers));
    }

    private boolean hasExtrasData(Bundle extras) {
        return extras.containsKey(EXTRAS_CHAT_KEY)
            && extras.containsKey(EXTRAS_PEERS_KEY);
    }

    private void setAdapter(ChatroomPeersArrayAdapter adapter) {
        mAdapter = adapter;
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat_room_peers, menu);
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
     * @author romac
     */
    class ChatroomPeersArrayAdapter extends ArrayAdapter<Peer> {
        private final static int LAYOUT = R.layout.chatroom_peer;

        private final Context mContext;
        private final LayoutInflater mInflater;
        private final Peer[] mPeers;
        private final Chat mChat;

        ChatroomPeersArrayAdapter(Context context, Chat chat, Peer[] peers) {
            super(context, LAYOUT, peers);

            mContext = context;
            mInflater = getLayoutInflater();
            mPeers = peers;
            mChat = chat;
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

            nameView.setText(p.getUserName());
            statusView.setText(p.getStatusMessage());
            imageView.setImageURI(Uri.parse(p.getPicturePath()));
            imageView.setImageResource(R.drawable.ic_launcher);
            badgeView.setVisibility((p.isOnline()) ? View.VISIBLE : View.INVISIBLE);

            // TODO: Show group owner badge instead of online badge.
            badgeView.setVisibility((mChat.isHost(p)) ? View.VISIBLE : View.INVISIBLE);

            return rowView;
        }
    }
}
