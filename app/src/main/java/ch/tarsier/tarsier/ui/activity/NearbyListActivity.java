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
import android.view.View;

import com.squareup.otto.Subscribe;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.event.ReceivedNearbyPeersListEvent;
import ch.tarsier.tarsier.ui.adapter.PeerAdapter;
import ch.tarsier.tarsier.ui.fragment.NearbyChatListFragment;
import ch.tarsier.tarsier.ui.fragment.NearbyPeerFragment;

public class NearbyListActivity extends Activity {

    private NearbyPeerFragment mNearbyPeer;
    private NearbyChatListFragment mNearbyChatList;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ActionBar actionBar = getActionBar();
        super.onCreate(savedInstanceState);
        Tarsier.app().getEventBus().register(this);
        setContentView(R.layout.activity_nearby_list);
        mFragmentManager = getFragmentManager();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(getString(R.string.action_bar_title));

        mNearbyPeer = new NearbyPeerFragment();
        mNearbyChatList = new NearbyChatListFragment();

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(R.id.inside_nearby,mNearbyPeer,"peer");
        ft.add(R.id.inside_nearby,mNearbyChatList,"chatList");


        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                if (tab.getPosition() == 0) {

                    //ft.attach(mNearbyChatList);
                    View menuNewChat = findViewById(R.id.create_new_chat_from_nearby);
                    if (menuNewChat != null) {
                        menuNewChat.setVisibility(View.VISIBLE);
                    }
                    ft.replace(R.id.inside_nearby, mNearbyChatList);
                } else if (tab.getPosition() == 1) {
                    //ft.attach(mNearbyPeer);
                    View menuNewChat = findViewById(R.id.create_new_chat_from_nearby);
                    if (menuNewChat != null) {
                        menuNewChat.setVisibility(View.GONE);
                    }
                    ft.replace(R.id.inside_nearby, mNearbyPeer);
                }
            }
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                if (tab.getPosition() == 0) {
                    //ft.detach(mNearbyChatList);
                } else if (tab.getPosition() == 1) {
                    //ft.detach(mNearbyPeer);
                }
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.tab_chatroom_name)).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.tab_peer_name)).setTabListener(tabListener));

    }

    @Subscribe
    public void receivedNewPeersList(ReceivedNearbyPeersListEvent event) {
        PeerAdapter peerAdapter = mNearbyPeer.getPeerAdapter();
        peerAdapter.setPeerList(event.getPeers());
        Log.d("EventOnNearbyActivity","size of list of peers :" + peerAdapter.getCount());
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.attach(mNearbyPeer);
        ft.commit();
        //mPeerAdapter.clear();
        //mPeerAdapter.setPeerList(event.getPeers());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nearby_list, menu);
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
        } else if (id == R.id.create_new_chat_from_nearby) {
            Intent newChat = new Intent(this, NewChatRoomActivity.class);
            startActivity(newChat);
        }
        return super.onOptionsItemSelected(item);
    }
}
