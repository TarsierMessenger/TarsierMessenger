package ch.tarsier.tarsier.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.PeerViewModel;
import ch.tarsier.tarsier.ui.adapter.PeerAdapter;

/**
 * Created by benjamin on 25/11/14.
 */
public class NearbyPeerFragment extends Fragment {

    Activity mActivity;
    //PeerViewModel[] tablePeers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle bundle = getArguments();
//        tablePeers = new PeerViewModel[5];
//        tablePeers[0] = new PeerViewModel(new Peer("Ben","J'aime les cacahuetes"));
//        tablePeers[1] = new PeerViewModel(new Peer("gluthier","J'aime les fraises"));
//        tablePeers[2] = new PeerViewModel(new Peer("amirezza","J'aime les patates"));
//        tablePeers[3] = new PeerViewModel(new Peer("Marin","J'aime les jolies filles"));
//        tablePeers[4] = new PeerViewModel(new Peer("Fred","J'aime les pommes"));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listView = inflater.inflate(R.layout.fragment_nearby_peer, container, false);
        ListView lv = (ListView) listView.findViewById(R.id.nearby_peer_list);
        lv.setAdapter(new PeerAdapter(mActivity, R.layout.row_nearby_peer_list, getTablePeers()));
        return listView;
    }

    private PeerViewModel[] getTablePeers() {
        //TODO get the peers in the right way
        PeerViewModel[] peers = new PeerViewModel[5];
        peers[0] = new PeerViewModel(new Peer("Ben","J'aime les cacahuetes"));
        peers[1] = new PeerViewModel(new Peer("gluthier","J'aime les fraises"));
        peers[2] = new PeerViewModel(new Peer("amirezza","J'aime les patates"));
        peers[3] = new PeerViewModel(new Peer("Marin","J'aime les jolies filles"));
        peers[4] = new PeerViewModel(new Peer("Fred","J'aime les pommes"));
        return peers;
    }
}
