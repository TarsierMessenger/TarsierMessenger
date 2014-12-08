package ch.tarsier.tarsier.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ch.tarsier.tarsier.R;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.event.ConnectToDeviceEvent;
import ch.tarsier.tarsier.event.ConnectedEvent;
import ch.tarsier.tarsier.event.MainThreadBus;
import ch.tarsier.tarsier.event.ReceivedNearbyPeersListEvent;
import ch.tarsier.tarsier.event.RequestNearbyPeersListEvent;
import ch.tarsier.tarsier.network.MessagingManager;
import ch.tarsier.tarsier.ui.activity.ChatActivity;
import ch.tarsier.tarsier.ui.adapter.NearbyPeerAdapter;

/**
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
        Log.d(TAG, "onCreate Fragment");
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
//                Intent chatIntent = new Intent(mActivity, ChatActivity.class);

//                Chat chat= new Chat();
//                chat.setTitle("TestChat");
//                chat.setPrivate(false);
//                chatIntent.putExtra(ChatActivity.EXTRA_CHAT_MESSAGE_KEY, (Peer) adapterView.getItemAtPosition(position));
//
//                Toast.makeText(mActivity,
//                        "peer id is " + ((Peer) adapterView.getItemAtPosition(position)).getId(),
//                        Toast.LENGTH_SHORT).show();
                // TODO decomment when it is ok
//                startActivity(chatIntent);
            }
        });
        return rowView;
    }


    //FIXME should maybe removed
    public NearbyPeerAdapter getNearbyPeerAdapter() {
        return mNearbyPeerAdapter;
    }

    public void setUp(Activity activty) {
        mActivity = activty;
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
