package ch.tarsier.tarsier.ui.fragment;

import android.app.ListFragment;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;

/**
 * WiFiDirectGroupList is the fragment for the WiFiDirectDebugActivity.
 *
 * @author amirezza
 */
public class WiFiDirectGroupList extends ListFragment {

    /**
     * DeviceClickListener is the interface that implement a click listener for the device
     */
    public interface DeviceClickListener {
        void connectP2p(WifiP2pDevice device);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.devices_list, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        WiFiDevicesAdapter mListAdapter = new WiFiDevicesAdapter(this.getActivity(),
                android.R.layout.simple_list_item_2, android.R.id.text1,
                new ArrayList<WifiP2pDevice>());

        setListAdapter(mListAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        ((DeviceClickListener) getActivity()).connectP2p((WifiP2pDevice) l
                .getItemAtPosition(position));
        ((TextView) v.findViewById(android.R.id.text2)).setText("Connecting");
    }

    /**
     * WiFiDevicesAdapter is the adapter for WifiP2pDevice
     */
    public class WiFiDevicesAdapter extends ArrayAdapter<WifiP2pDevice> {

        private List<WifiP2pDevice> mItems;

        public WiFiDevicesAdapter(Context context, int resource,
                                  int textViewResourceId, List<WifiP2pDevice> items) {
            super(context, resource, textViewResourceId, items);
            this.mItems = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(android.R.layout.simple_list_item_2, null);
            }
            WifiP2pDevice device = mItems.get(position);
            if (device != null) {
                TextView nameText = (TextView) v
                        .findViewById(android.R.id.text1);
                if (nameText != null) {
                    nameText.setText(device.deviceName);
                }
                TextView statusText = (TextView) v
                        .findViewById(android.R.id.text2);
                statusText.setText(getDeviceStatus(device.status));
            }
            return v;
        }
    }

    public static String getDeviceStatus(int statusCode) {
        switch (statusCode) {
            case WifiP2pDevice.CONNECTED:
                return "Connected";
            case WifiP2pDevice.INVITED:
                return "Invited";
            case WifiP2pDevice.FAILED:
                return "Failed";
            case WifiP2pDevice.AVAILABLE:
                return "Available";
            case WifiP2pDevice.UNAVAILABLE:
                return "Unavailable";
            default:
                return "Unknown";
        }
    }
}
