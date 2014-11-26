package ch.tarsier.tarsier.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ch.tarsier.tarsier.R;

public class NearbyListActivity extends Activity {

    private NearbyPeerFragment mNearbyPeer;
    private NearbyChatListFragment mNearbyChatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ActionBar actionBar = getActionBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_list);

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(getString(R.string.action_bar_title));

        mNearbyPeer = new NearbyPeerFragment();
        mNearbyChatList = new NearbyChatListFragment();


        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                if (tab.getPosition() == 0) {
                    //ft.attach(mNearbyChatList);
                    ft.replace(R.id.inside_nearby,mNearbyChatList);
                } else if (tab.getPosition() == 1) {
                    //ft.attach(mNearbyPeer);
                    ft.replace(R.id.inside_nearby,mNearbyPeer);
                }
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };
        actionBar.addTab(actionBar.newTab().setText("Chat rooms").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Peers").setTabListener(tabListener));

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
        }
        return super.onOptionsItemSelected(item);
    }
}
