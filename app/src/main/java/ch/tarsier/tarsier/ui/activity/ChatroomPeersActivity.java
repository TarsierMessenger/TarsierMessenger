package ch.tarsier.tarsier.ui.activity;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.event.ReceivedChatroomPeersListEvent;
import ch.tarsier.tarsier.event.RequestChatroomPeersListEvent;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.exception.UpdateException;
import ch.tarsier.tarsier.ui.adapter.ChatroomPeersAdapter;
import ch.tarsier.tarsier.ui.view.ChatroomPeersListView;

/**
 * @author romac
 * @author gluthier
 */
public class ChatroomPeersActivity extends Activity {
    public final static String EXTRA_CHAT_KEY = "ch.tarsier.tarsier.ui.activity.CHATROOM_PEERS_ACTIVITY";
    public final static String TAG = "ChatroomPeersActivity";
    private Chat mChat;
    private Bus mEventBus;
    private ChatroomPeersListView mChatroomPeersListView;
    private ChatroomPeersAdapter mChatroomPeersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_peers);

        mChat = (Chat) getIntent().getExtras().getSerializable(EXTRA_CHAT_KEY);
        if (mChat == null) {
            Log.d(TAG, "EXTRA_CHAT_KEY intent does not exist.");
            this.finish();
        }

        mEventBus = Tarsier.app().getEventBus();

        mChatroomPeersListView = (ChatroomPeersListView) findViewById(R.id.peers_list);
        mChatroomPeersAdapter = new ChatroomPeersAdapter(this, R.layout.row_chatroom_peers_list);

        mChatroomPeersListView.setLoadingView(R.layout.loading_layout);
        mChatroomPeersListView.setChatroomPeersAdapter(mChatroomPeersAdapter);

        mChatroomPeersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Peer peer = mChatroomPeersAdapter.getItem(position);
                createPrivateChat(peer);
            }
        });

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEventBus.register(this);
        mEventBus.post(new RequestChatroomPeersListEvent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mEventBus.unregister(this);
    }

    @Subscribe
    public void onReceivedChatroomPeersListEvent(ReceivedChatroomPeersListEvent event) {
        Log.d(TAG, "Got ReceivedChatroomPeersListEvent");
        mChatroomPeersAdapter.clear();
        mChatroomPeersListView.addNewData(filterOutOwnUser(event.getPeers()));
    }

    private List<Peer> filterOutOwnUser(List<Peer> peers) {
        ArrayList<Peer> filteredPeers = new ArrayList<Peer>();
        for (Peer peer : peers) {
            if (!peer.isUser()) {
                filteredPeers.add(peer);
            }
        }
        return filteredPeers;
    }

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
            case R.id.goto_profile_activity:
                openProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createPrivateChat(Peer peer) {
        Log.d(TAG, "Create private Chat");
        ChatRepository chatRepository = Tarsier.app().getChatRepository();

        try {
            Chat newPrivateChat = chatRepository.findPrivateChatForPeer(peer);
            newPrivateChat.setPrivate(true);
            chatRepository.update(newPrivateChat);

            Intent newPrivateChatIntent = new Intent(this, ChatActivity.class);
            newPrivateChatIntent.putExtra(ChatActivity.EXTRA_CHAT_MESSAGE_KEY, newPrivateChat);
            startActivity(newPrivateChatIntent);
        } catch (NoSuchModelException | InvalidModelException | UpdateException e) {
            e.printStackTrace();
        }
    }

    private void openProfile() {
        Intent openProfileIntent = new Intent(this, ProfileActivity.class);
        startActivity(openProfileIntent);
    }
}
