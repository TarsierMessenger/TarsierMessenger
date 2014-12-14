package ch.tarsier.tarsier.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.event.ConnectedEvent;
import ch.tarsier.tarsier.event.CreateGroupEvent;
import ch.tarsier.tarsier.event.ReceivedNearbyPeersListEvent;
import ch.tarsier.tarsier.event.RequestNearbyPeersListEvent;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.ui.adapter.NearbyPeerAdapter;
import ch.tarsier.tarsier.ui.fragment.NearbyPeerFragment;

/**
 * This Activity shows a list of either nearby peers to connect with or a list of
 * chatrooms active nearby.
 *
 * @author benpac
 * @author marinnicolini
 */
public class NearbyListActivity extends Activity {

    public final static String EXTRA_FROM_HOME_KEY = "ch.tarsier.tarsier.ui.activity.NEARBY";

    private final static String TAG = "NearbyList";

    private NearbyPeerFragment mNearbyPeer;
    private FragmentManager mFragmentManager;
    private Bus mEventBus;

    private ChatRepository mChatRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_list);

        mChatRepository = Tarsier.app().getChatRepository();

        getEventBus().register(this);

        mFragmentManager = getFragmentManager();

        mNearbyPeer = new NearbyPeerFragment();
        mNearbyPeer.setUpFragment(this);

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(R.id.inside_nearby, mNearbyPeer);
        ft.attach(mNearbyPeer);
        ft.commit();

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            boolean comesFromHomeActivity = getIntent().getBooleanExtra(EXTRA_FROM_HOME_KEY, false);

            if (comesFromHomeActivity) {
                actionBar.setDisplayHomeAsUpEnabled(false);
            } else {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    @Subscribe
    public void receivedNewPeersList(ReceivedNearbyPeersListEvent event) {
        Log.d(TAG, "Got ReceivedNearbyPeersListEvent");
        NearbyPeerAdapter peerAdapter = mNearbyPeer.getNearbyPeerAdapter();
        if (peerAdapter != null) {
            peerAdapter.setPeerList(event.getPeers());
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.detach(mNearbyPeer);
        ft.attach(mNearbyPeer);
        ft.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        getEventBus().register(this);
        getEventBus().post(new RequestNearbyPeersListEvent());
    }

    @Override
    public void onPause() {
        getEventBus().unregister(this);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nearby_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.create_new_chat_from_nearby:
                displayNewChatroomActivity();
                return true;

            case R.id.action_profile:
                displayProfileActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void displayNewChatroomActivity() {
        ChatRepository chatRepository = Tarsier.app().getChatRepository();
        Chat mNewChatroom = null;
        try {
            mNewChatroom = chatRepository.findPublicChat();
            Toast.makeText(this, "Waiting for connection...", Toast.LENGTH_SHORT).show();
            mEventBus.post(new CreateGroupEvent(mNewChatroom));
        } catch (InvalidModelException | NoSuchModelException e) {
            Toast.makeText(this, "Failed to connect to new chatroom",  Toast.LENGTH_SHORT).show();
        }
    }

    private void displayProfileActivity() {
        Intent displayProfileIntent = new Intent(this, ProfileActivity.class);
        startActivity(displayProfileIntent);
    }

    private void displayChatsListActivity() {
        Intent chatsListActivity = new Intent(this, ChatListActivity.class);
        startActivity(chatsListActivity);
    }
    private Bus getEventBus() {
        if (mEventBus == null) {
            mEventBus = Tarsier.app().getEventBus();
        }

        return mEventBus;
    }

    @Subscribe
    public void onConnectedEvent(ConnectedEvent event) {
        Log.d(TAG, "Got ConnectedEvent");

        Intent chatIntent = new Intent(this, ChatActivity.class);

        try {
            super.onBackPressed();
            Chat chat = mChatRepository.findPublicChat();
            chatIntent.putExtra(ChatActivity.EXTRA_CHAT_MESSAGE_KEY, chat);
            startActivity(chatIntent);
        } catch (NoSuchModelException | InvalidModelException e) {
            Log.d(TAG, "Cannot find public chat.");
            e.printStackTrace();
        }
    }
}