package ch.tarsier.tarsier.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ch.tarsier.tarsier.R;

import com.squareup.otto.Bus;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.event.ConnectToDeviceEvent;
import ch.tarsier.tarsier.ui.adapter.NearbyPeerAdapter;

/**
 * NearbyPeerFragment is the fragment for the NearbyListActivity.
 *
 * @author benpac
 * @author marinnicolini
 */
public class NearbyPeerFragment extends Fragment {

    private Activity mActivity;
    private NearbyPeerAdapter mNearbyPeerAdapter;
    private static final String TAG = "NearbyPeerFragment";
    private Bus mEventBus;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        Log.d(TAG, "onAttach Fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView Fragment");
        Log.d(TAG, "onCreateView Fragment size list peers : " + mNearbyPeerAdapter.getCount());

        View rowView = inflater.inflate(R.layout.fragment_nearby_peer, container, false);
        ListView lv = (ListView) rowView.findViewById(R.id.nearby_peer_list);
        lv.setAdapter(mNearbyPeerAdapter);
        //set on click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                getEventBus().post(new ConnectToDeviceEvent((WifiP2pDevice) adapterView
                        .getItemAtPosition(position)));
            }
        });
        return rowView;
    }

    //FIXME should maybe removed
    public NearbyPeerAdapter getNearbyPeerAdapter() {
        return mNearbyPeerAdapter;
    }

    public void setUpFragment(Activity activity) {
        mActivity = activity;
        mNearbyPeerAdapter = new NearbyPeerAdapter(mActivity, R.layout.row_nearby_peer_list);
    }


    public Bus getEventBus() {
        if (mEventBus == null) {
            mEventBus = Tarsier.app().getEventBus();
            mEventBus.register(this);
        }

        return mEventBus;
    }


}
