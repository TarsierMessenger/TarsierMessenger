package ch.tarsier.tarsier.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.PeerViewModel;

/**
 * Created by benjamin on 25/11/14.
 */
public class PeerAdapter extends ArrayAdapter<PeerViewModel> {

    PeerViewModel[] tablePeers = new PeerViewModel[5];



    public PeerAdapter(Context context, int resource, PeerViewModel[] objects) {
        super(context, resource, objects);
        //initiate with fictional peers - to be removed
        tablePeers[0] = new PeerViewModel(new Peer("Ben","J'aime les cacahuetes"));
        tablePeers[1] = new PeerViewModel(new Peer("gluthier","J'aime les fraises"));
        tablePeers[2] = new PeerViewModel(new Peer("amirezza","J'aime les patates"));
        tablePeers[3] = new PeerViewModel(new Peer("Marin","J'aime les jolies filles"));
        tablePeers[4] = new PeerViewModel(new Peer("Fred","J'aime les pommes"));
            

    }
}
