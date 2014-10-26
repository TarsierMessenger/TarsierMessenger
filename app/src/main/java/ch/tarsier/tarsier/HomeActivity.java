package ch.tarsier.tarsier;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*TODO:
    -Custumize listView
    -
*/
public class HomeActivity extends Activity {
    public boolean testUI = true;

    final public int SERVER_PORT = 8888;
    final public String NAME = "Tarsier"; //TODO: To be modified by UI
    public final String TAG = "HomeActivity";
    private IntentFilter mIntentFilter;
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private WiFiDirectBroadcastReceiver mReceiver;
    private WifiP2pDnsSdServiceRequest serviceRequest;
    final HashMap<String, String> buddies = new HashMap<String, String>();
    private ArrayAdapter<WifiP2pDevice> adapter;
    final HashMap<String,WifiP2pDevice> list = new HashMap<String,WifiP2pDevice>();
    private WifiP2pManager.ActionListener refreshListener;
    ListView listView;
    private boolean isP2PEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        listView = (ListView) findViewById(R.id.listview);
        adapter = new MyArrayAdapter(this,new ArrayList<WifiP2pDevice>());
        listView.setAdapter(adapter);
        // Add intents that broadcast receiver checks for
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
        startClickListener();
        discoverPeers();




    }

    private void discoverPeers() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(TAG,"Peers discovered successfully");
            }

            @Override
            public void onFailure(int reasonCode) {
                Log.w(TAG,"Failed to discover peers.");
            }
        });

    }

    protected void startRegistration() {
        //  Create a string map containing information about your service.
        Map record = new HashMap();
        record.put("listenport", String.valueOf(SERVER_PORT));
        record.put("buddyname", NAME + (int) (Math.random() * 1000));

        // Service information.  Pass it an instance name, service type
        // _protocol._transportlayer , and the map containing
        // information other devices will want once they connect to this one.
        WifiP2pDnsSdServiceInfo serviceInfo =
                WifiP2pDnsSdServiceInfo.newInstance("_test", "_presence._tcp", record);

        // Add the local service, sending the service info, network channel,
        // and listener that will be used to indicate success or failure of
        // the request.
        mManager.addLocalService(mChannel, serviceInfo, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(TAG,"Local service added successfully");
            }

            @Override
            public void onFailure(int arg0) {
                Log.i(TAG,"Adding local service failed");
            }
        });
    }





    private void discoverService() {

        WifiP2pManager.DnsSdTxtRecordListener txtListener = new WifiP2pManager.DnsSdTxtRecordListener() {
            @Override
        /* Callback includes:
         * fullDomain: full domain name: e.g "printer._ipp._tcp.local."
         * record: TXT record dta as a map of key/value pairs.
         * device: The device running the advertised service.
         */

            public void onDnsSdTxtRecordAvailable(
                    String fullDomain, Map record, WifiP2pDevice device) {
                Log.i(TAG, "DnsSdTxtRecord available -" + record.toString());
                buddies.put(device.deviceAddress, (String) record.get("buddyname"));
            }
        };

        WifiP2pManager.DnsSdServiceResponseListener servListener = new WifiP2pManager.DnsSdServiceResponseListener() {
            @Override
            public void onDnsSdServiceAvailable(String instanceName, String registrationType,
                                                WifiP2pDevice resourceType) {

                // Update the device name with the human-friendly version from
                // the DnsTxtRecord, assuming one arrived.
                resourceType.deviceName = buddies
                        .containsKey(resourceType.deviceAddress) ? buddies
                        .get(resourceType.deviceAddress) : resourceType.deviceName;

                // Add to the custom adapter defined specifically for showing
                // wifi devices.
                list.put(resourceType.deviceName,resourceType);
                adapter.add(resourceType);
                adapter.notifyDataSetChanged();
                Log.d(TAG, "Buddy " + resourceType.deviceName + " added");
                Log.d(TAG, "onBonjourServiceAvailable " + instanceName);
            }
        };

        mManager.setDnsSdResponseListeners(mChannel, servListener, txtListener);

        serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        mManager.addServiceRequest(mChannel,
                serviceRequest,
                new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Service request added successfully");
                    }

                    @Override
                    public void onFailure(int code) {
                        Log.d(TAG, "Adding service request failed");
                    }
                });

        refreshListener = new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "Service discovery successful");
            }

            @Override
            public void onFailure(int code) {
                // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
                if (code == WifiP2pManager.P2P_UNSUPPORTED) {
                    Log.d(TAG, "P2P isn't supported on this device.");
                }else if(code == WifiP2pManager.BUSY) {
                    Log.d(TAG, "The system is too busy to process the request.");
                }else if(code == WifiP2pManager.ERROR){
                    Log.d(TAG, "The operation failed due to an internal error");
                }
            }
        };
        mManager.discoverServices(mChannel,refreshListener);
    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
        Log.i(TAG,"onResume():Receiver is registered to intent values.");
    }

    public void setIsWifiP2pEnabled(boolean bool){
        isP2PEnabled = bool;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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


    //dummy UI testing
//    public void dummyMethodTestUI(View view) {
//        //testUI = !testUI;
//        TextView tw = (TextView) findViewById(R.id.helloWorld);
//        tw.setText("Hello World!");
//    }



    private void startClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                final WifiP2pDevice item = (WifiP2pDevice) adapterView.getItemAtPosition(position);
                Log.d(TAG, "Item " + position + " is selected: " + item.deviceName);
                new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mReceiver.createConnection(mReceiver.getPeers().get(position));

                            }
                        }).start();

            }
        });
    }

    /** Called when the user touches the button */
    public void onCreateGroup(View view) {
        Log.i(TAG, "onCreateGroup called.");
        mManager.createGroup(mChannel,new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(TAG,"Group Created Successfully");
            }

            @Override
            public void onFailure(int code) {
                // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
                if (code == WifiP2pManager.P2P_UNSUPPORTED) {
                    Log.d(TAG, "Group creation failed. P2P isn't supported on this device.");
                }else if(code == WifiP2pManager.BUSY) {
                    Log.d(TAG, "Group creation failed. The system is to busy to process the request.");
                }else if(code == WifiP2pManager.ERROR){
                    Log.d(TAG, "Group creation failed. The operation failed due to an internal error");
                }
            }
        });


    }
    public void onLookForGroup(View view){
        Log.i(TAG,"onLookForGroup called.");
        mManager.requestGroupInfo(mChannel,new WifiP2pManager.GroupInfoListener() {
            @Override
            public void onGroupInfoAvailable(WifiP2pGroup wifiP2pGroup) {
                Log.i(TAG,wifiP2pGroup.toString());
            }
        });

    }
    public void onRefresh(View view){
        discoverPeers();
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
        Log.i(TAG,"onPause():Receiver is unregistered.");
    }


    public void update(List<WifiP2pDevice> peers) {
        listView.setAdapter(null);
        adapter.notifyDataSetChanged();
        adapter.addAll(peers);
        adapter.notifyDataSetChanged();
    }
}
