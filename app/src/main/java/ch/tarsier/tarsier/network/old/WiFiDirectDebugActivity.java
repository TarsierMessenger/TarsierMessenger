package ch.tarsier.tarsier.network.old;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import java.io.IOException;
import java.util.ArrayList;

import ch.tarsier.tarsier.R;

/**
 * @author amirezza
 */
public class WiFiDirectDebugActivity
    extends Activity
    implements WifiP2pManager.ConnectionInfoListener,
               WiFiDirectGroupList.DeviceClickListener,
        Server.MessageTarget,
               Handler.Callback {

    public static final String TAG = "WiFiDirectDebugActivity";
    public static final int SERVER_PORT = 8888;
    public static final int MESSAGE_READ = 0x401;
    public static final int MY_HANDLE = 0x402;


    private WifiP2pManager.PeerListListener peerListListener;
    private IntentFilter mIntentFilter;
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private WiFiDirectBroadcastReceiver mReceiver;


    private WiFiDirectGroupList groupList;
    private final ArrayList<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    private Chatroom mChatroom;
    private Handler mHandler = new Handler(this);

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

        //Be careful, using peerList before initializing it
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this, peerListListener);
        createPeerListener();
        initiatePeerDiscovery();


        mManager.requestPeers(mChannel, peerListListener);
        groupList = new WiFiDirectGroupList();
        getFragmentManager().beginTransaction()
                .add(R.id.container, groupList, "groups").commit();

    }

    private void createPeerListener() {

        peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peersList) {
                peers.clear();
                peers.addAll(peersList.getDeviceList());

                // If an AdapterView is backed by this data, notify it
                // of the change.  For instance, if you have a ListView of available
                // peers, trigger an update.
                WiFiDirectGroupList fragment = (WiFiDirectGroupList) getFragmentManager()
                        .findFragmentByTag("groups");
                if (fragment != null) {
                    WiFiDirectGroupList.WiFiDevicesAdapter adapter =
                            (WiFiDirectGroupList.WiFiDevicesAdapter) fragment.getListAdapter();

                    adapter.clear();
                    adapter.addAll(peers);
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "Peer list updated: "+ peers.toString());
                    if (peers.size() == 0) {
                        Log.d(TAG, "No devices found");
                    }
                }
            }
        };
    }

    @Override
    protected void onRestart() {
        Fragment frag = getFragmentManager().findFragmentByTag("groups");
        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
        super.onRestart();
    }


    public void initiatePeerDiscovery() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Peer discovery initiation succeeded");
            }

            @Override
            public void onFailure(int reasonCode) {
                if (reasonCode == WifiP2pManager.P2P_UNSUPPORTED) {
                    Log.d(TAG, "Peer discovery initiation failed. P2P isn't supported on this device.");
                } else if (reasonCode == WifiP2pManager.BUSY) {
                    Log.d(TAG, "Peer discovery initiation failed. The system is too busy to process the request.");
                } else if (reasonCode == WifiP2pManager.ERROR) {
                    Log.d(TAG, "Peer discovery initiation failed. The operation failed due to an internal error.");
                }
            }
        });


    }

    @Override
    public void connectP2p(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
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

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    public void onResume() {
        super.onResume();
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this, peerListListener);
        registerReceiver(mReceiver, mIntentFilter);
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

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo p2pInfo) {
        Thread handler = null;
        /*
        * The group owner accepts connections using a server socket and then spawns a
        * client socket for every client.
        */
        if (p2pInfo.isGroupOwner) {
            Log.d(TAG, "Connected as group owner");
            try {
                handler = new Server(
                        ((Server.MessageTarget) this).getHandler());
                handler.start();
            } catch (IOException e) {
                Log.d(TAG,
                        "Failed to create a server thread - " + e.getMessage());
                return;
            }
        } else {
            Log.d(TAG, "Connected as peer");
            handler = new Client(
                    ((Server.MessageTarget) this).getHandler(),
                    p2pInfo.groupOwnerAddress);
            handler.start();
        }

        mChatroom = new Chatroom();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, mChatroom).commit();

    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public boolean handleMessage(Message message) {

        switch (message.what) {
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) message.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, message.arg1);
                Log.d(TAG, readMessage);
                (mChatroom).pushMessage("Peer: " + readMessage);
                break;
            case MY_HANDLE:
                Object obj = message.obj;
                (mChatroom).setMyConnection((MyConnection) obj);
        }
        return true;
    }

}