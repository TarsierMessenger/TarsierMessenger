package ch.tarsier.tarsier.ui.adapter;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.tarsier.tarsier.R;

/**
 * Created by amirreza on 10/26/14.
 */
public class MyArrayAdapter extends ArrayAdapter<WifiP2pDevice> {

    public final String TAG = "MyArrayAdapter";


    public MyArrayAdapter(Context context, List<WifiP2pDevice> list) {


        super(context, 0, list);
        Log.d(TAG,"Adapter created" );


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        WifiP2pDevice buddyDevice = getItem(position);
        Log.d(TAG, "getView called : " + buddyDevice.deviceName);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_buddy, parent, false);
        }
        // Lookup view for data population
        TextView mName = (TextView) convertView.findViewById(R.id.name);
        // Populate the data into the template view using the data object
        mName.setText(buddyDevice.deviceName);
        // Return the completed view to render on screen
        return convertView;
    }
}
