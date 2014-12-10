package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.network.MessagingManager;
import ch.tarsier.tarsier.ui.fragment.WiFiDirectGroupList;

// Important note: This is currently an activity, it will later be a ran as a service so that it
// can run in the background too.

/**
 * @author FredericJacobs
 * @author amirezza
 */
public class WiFiDirectDebugActivity
        extends Activity
        implements WiFiDirectGroupList.DeviceClickListener {

    public static final String TAG = "WiFiDirectDebugActivity";

    private boolean isServer = false;


    private IntentFilter mIntentFilter;

    private WifiP2pManager mManager;

    private WifiP2pManager.Channel mChannel;

    private MessagingManager mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifidebug);

        // Add intents that broadcast receiver checks for
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

        WiFiDirectGroupList groupList = new WiFiDirectGroupList();
        getFragmentManager().beginTransaction()
                .add(R.id.container, groupList, "groups").commit();


    }

    @Override
    public void connectP2p(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        if (isServer) {
            config.groupOwnerIntent = 0;
        } else {
            config.groupOwnerIntent = 15;
        }
        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Connecting to peer");
            }

            @Override
            public void onFailure(int errorCode) {
                Log.d(TAG, "Failed connecting to service");
            }
        });
    }

    public void onCreateGroup(View view) {
        Log.d(TAG, "Create Group clicked");
        isServer = true;
        mManager.createGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Group successfully created.");
            }

            @Override
            public void onFailure(int errorCode) {
                Toast.makeText(getApplicationContext(), "Tarsier failed to initiate a new group",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        Fragment frag = getFragmentManager().findFragmentByTag("groups");
        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
        super.onRestart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mReceiver = new MessagingManager(mManager, mChannel);
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStop() {
        if (mManager != null && mChannel != null) {
            mManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {
                @Override
                public void onFailure(int reasonCode) {
                    Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);
                }

                @Override
                public void onSuccess() {
                }
            });
        }
        super.onStop();
    }

}