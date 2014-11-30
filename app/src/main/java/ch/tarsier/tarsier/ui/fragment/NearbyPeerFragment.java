package ch.tarsier.tarsier.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.event.ReceivedNearbyPeersListEvent;
import ch.tarsier.tarsier.event.RequestNearbyPeersListEvent;
import ch.tarsier.tarsier.ui.activity.ChatActivity;
import ch.tarsier.tarsier.ui.adapter.PeerAdapter;

/**
 * @author benpac
 * @author marinnicolini
 */
public class NearbyPeerFragment extends Fragment {

    private Activity mActivity;
    private PeerAdapter mPeerAdapter;
    private static final String CHAT = "NearbyChat";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPeerAdapter = new PeerAdapter(mActivity, R.layout.row_nearby_peer_list);
        //Register to event bus and request the list of nearby peer
        Tarsier.app().getEventBus().register(this);
        Tarsier.app().getEventBus().post(new RequestNearbyPeersListEvent());
        //debug

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rowView = inflater.inflate(R.layout.fragment_nearby_peer, container, false);
        ListView lv = (ListView) rowView.findViewById(R.id.nearby_peer_list);
        lv.setAdapter(mPeerAdapter);
        //set on click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent chatIntent = new Intent(mActivity, ChatActivity.class);
                chatIntent.putExtra(CHAT, (Peer) adapterView.getItemAtPosition(position));

                Toast.makeText(mActivity,
                        "peer id is " + ((Peer) adapterView.getItemAtPosition(position)).getId(),
                        Toast.LENGTH_SHORT).show();
                // TODO decomment when it is ok
                //startActivity(chatIntent);
            }
        });
        return rowView;
    }

    @Subscribe
    public void receivedNewPeersList(ReceivedNearbyPeersListEvent event) {
        mPeerAdapter.setPeerList(event.getPeers());
    }

    //FIXME should maybe removed
    public PeerAdapter getPeerAdapter() {
        return mPeerAdapter;
    }

    private List<Peer> getListPeers() {
        List<Peer> peers = new ArrayList<Peer>();

        return peers;
    }
}
