package ch.tarsier.tarsier.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.event.ReceivedChatroomPeersListEvent;
import ch.tarsier.tarsier.event.ReceivedNearbyPeersListEvent;
import ch.tarsier.tarsier.ui.adapter.PeerAdapter;

/**
 * Created by benjamin on 25/11/14.
 */
public class NearbyPeerFragment extends Fragment {

    private Activity mActivity;
    private PeerAdapter mPeerAdapter;
    private int count =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tarsier.app().getEventBus().register(this);
        mPeerAdapter = new PeerAdapter(mActivity,R.layout.row_nearby_peer_list);
        //debug
    }

    @Subscribe
    public void receivedNewPeersList(ReceivedNearbyPeersListEvent event) {
        mPeerAdapter.clear();
        mPeerAdapter.setPeerList(event.getPeers());
        Log.d("EventRecieved", "The event has been recieved");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        //updatePeerAdapter();
        //List<Peer> peerList = getListPeers();
        //Tarsier.app().getEventBus().post(new ReceivedNearbyPeersListEvent(peerList));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rowView = inflater.inflate(R.layout.fragment_nearby_peer, container, false);
        ListView lv = (ListView) rowView.findViewById(R.id.nearby_peer_list);
        lv.setAdapter(mPeerAdapter);
        return rowView;
    }

    private List<Peer> getListPeers() {
        //TODO get the peers in the right way
        List<Peer> peers = new ArrayList<Peer>();
        peers.add(new Peer("Ben", "J'aime les cacahuetes"));
        peers.add(new Peer("gluthier", "J'aime les fraises"));
        peers.add(new Peer("amirezza", "J'aime les patates"));
        peers.add(new Peer("Marin", "J'aime les petits pois"));
        peers.add(new Peer("Fred", "J'aime les pommes"));

//            peers.add(new Peer("Benpac","I am immortal"));
//            peers.add(new Peer("Romac", "Shut up ben"));

        return peers;
    }
}
