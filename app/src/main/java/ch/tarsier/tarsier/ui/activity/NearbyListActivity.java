package ch.tarsier.tarsier.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.ui.fragment.NearbyChatListFragment;
import ch.tarsier.tarsier.ui.fragment.NearbyPeerFragment;

/**
 * @author benpac
 * @author marinnicolini
 *
 * This Activity shows a list of either nearby peers to connect with or a list of
 * chatrooms active nearby.
 *
 */
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

//        FragmentTransaction ft = mFragmentManager.beginTransaction();
//        ft.add(R.id.inside_nearby, mNearbyPeer, "peer");
//        ft.add(R.id.inside_nearby, mNearbyChatList, "chatList");


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
            case R.id.action_settings:
                return true;

            case R.id.create_new_chat_from_nearby:
                displayNewChatroomActivity();
                return true;

            case R.id.action_profile:
                displayProfileActivity();
                return true;

            case R.id.action_chats_list:
                displayChatsListActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void displayNewChatroomActivity() {
        Intent newChat = new Intent(this, NewChatroomActivity.class);
        startActivity(newChat);
    }

    private void displayProfileActivity() {
        Intent displayProfileIntent = new Intent(this, ProfileActivity.class);
        startActivity(displayProfileIntent);
    }

    private void displayChatsListActivity() {
        Intent chatsListActivity = new Intent(this, ChatListActivity.class);
        startActivity(chatsListActivity);
    }
}
