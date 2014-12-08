package ch.tarsier.tarsier.ui.activity;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.event.ReceivedChatroomPeersListEvent;
import ch.tarsier.tarsier.event.RequestChatroomPeersListEvent;
import ch.tarsier.tarsier.ui.adapter.ChatroomPeersAdapter;
import ch.tarsier.tarsier.ui.view.ChatroomPeersListView;

/**
 * @author romac
 */
public class ChatroomPeersActivity extends Activity {

    public final static String EXTRA_CHAT_KEY = "ch.tarsier.tarsier.ui.activity.CHATROOM_PEERS_ACTIVITY";
    public final static String TAG = "ChatroomPeersActivity";

    private Chat mChat;

    private ChatroomPeersListView mChatroomPeersListView;
    private ChatroomPeersAdapter mChatroomPeersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chatroom_peers);
        setContentView(R.layout.activity_chatroom_peers);

        mChat = (Chat) getIntent().getExtras().getSerializable(EXTRA_CHAT_KEY);

        mChatroomPeersListView = (ChatroomPeersListView) findViewById(R.id.peers_list);
        mChatroomPeersAdapter = new ChatroomPeersAdapter(this, R.layout.row_chatroom_peers_list);

        mChatroomPeersListView.setLoadingView(R.layout.loading_layout);
        mChatroomPeersListView.setChatroomPeersAdapter(mChatroomPeersAdapter);

        mChatroomPeersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Peer peer = mChatroomPeersAdapter.getItem(position);
                //TODO open new private chat
            }
        });

        setUpEvent();
        //setUpData();

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    private void setUpEvent() {
        Bus eventBus = Tarsier.app().getEventBus();
        eventBus.register(this);
        eventBus.post(new RequestChatroomPeersListEvent());
    }

    @Subscribe
    public void onReceivedChatroomPeersListEvent(ReceivedChatroomPeersListEvent event) {
        Log.d(TAG, "Got ReceivedChatroomPeersListEvent");
        mChatroomPeersAdapter.clear();
        mChatroomPeersAdapter.addAll(event.getPeers());
    }
/*
    private void setUpData() {
        Intent sender = getIntent();
        Bundle extras = sender.getExtras();
/*
        if (extras == null || !hasExtrasData(extras)) {
            setUpWithTestData();
            return;
        }
*/
        //Chat chat = (Chat) extras.getSerializable(EXTRAS_CHAT_KEY);
        //Peer[] peers = (Peer[]) extras.getSerializable(EXTRAS_PEERS_KEY);

        //mChatroomPeersAdapter = new ChatroomPeersArrayAdapter(this, chat, peers);
//        setAdapter(mChatroomPeersAdapter);
//    }
/*
    private void setUpWithTestData() {
        Peer host = new Peer("Amirezza Bahreini", "At Sat', come join me !");
        host.setId(1);

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
        peers[3].setOnline(true);

        setAdapter(new ChatroomPeersArrayAdapter(this, chat, peers));
    }
*/
    private boolean hasExtrasData(Bundle extras) {
        /*return extras.containsKey(EXTRAS_CHAT_KEY)
                && extras.containsKey(EXTRAS_PEERS_KEY);*/
        return true;
    }
/*
    private void setAdapter(ChatroomPeersAdapter adapter) {
        mChatroomPeersAdapter = adapter;
        setListAdapter(adapter);
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chatroom_peers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
