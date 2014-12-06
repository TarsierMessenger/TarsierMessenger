package ch.tarsier.tarsier.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ch.tarsier.tarsier.R;
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
                Intent chatIntent = new Intent(mActivity, ChatActivity.class);
                //chatIntent.putExtra(CHAT, (Peer) adapterView.getItemAtPosition(position));

//                Toast.makeText(mActivity,
//                        "peer id is " + ((Peer) adapterView.getItemAtPosition(position)).getId(),
//                        Toast.LENGTH_SHORT).show();
                // TODO decomment when it is ok
                //startActivity(chatIntent);
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
}
