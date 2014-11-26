package ch.tarsier.tarsier.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.PeerViewModel;
import ch.tarsier.tarsier.util.BitmapFromPath;

/**
 * Created by benjamin on 25/11/14.
 */
public class PeerAdapter extends ArrayAdapter<PeerViewModel> {

    private PeerViewModel[] tablePeers;
    private Context mContext;
    private int mRowLayoutId;


    public PeerAdapter(Context context, int resource, PeerViewModel[] peers) {
        super(context, resource, peers);
        //initiate with fictional peers - to be removed

        tablePeers = peers;
        mContext = context;
        mRowLayoutId = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PeerViewModelHolder peerHolder = null;
        Log.d("PeerAdapter", "mContext is : " + ((Activity) mContext).getLocalClassName());
        if (row == null) {
            //create row
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mRowLayoutId,parent,false);
            peerHolder = new PeerViewModelHolder();

            peerHolder.mProfilePicture = (ImageView) row.findViewById(R.id.nearbyPeerPicture);
            peerHolder.mStatus = (TextView) row.findViewById(R.id.nearbyPeerStatus);
            peerHolder.mUsername = (TextView) row.findViewById(R.id.nearbyPeerName);

            row.setTag(peerHolder);

        } else {
            // recover the information
            peerHolder = (PeerViewModelHolder) row.getTag();
        }
        PeerViewModel peerToShow = tablePeers[position];
        peerHolder.mUsername.setText(peerToShow.getPeerName());
        peerHolder.mStatus.setText(peerToShow.getPeerStatusMessage());
        peerHolder.mProfilePicture.setImageBitmap(
                BitmapFromPath.getBitmapFromPath(mContext, peerToShow.getPeerPicturePath())
        );

        return row;
    }



    private class PeerViewModelHolder {
        TextView mUsername;
        TextView mStatus;
        ImageView mProfilePicture;
    }
}
