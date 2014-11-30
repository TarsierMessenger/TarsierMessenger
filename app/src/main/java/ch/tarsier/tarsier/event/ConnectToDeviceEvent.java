package ch.tarsier.tarsier.event;

import android.net.wifi.p2p.WifiP2pDevice;

/**
 * @author romac
 * @author amirezza
 */
public class ConnectToDeviceEvent {

    private final WifiP2pDevice mDevice;

    public ConnectToDeviceEvent(WifiP2pDevice device) {
        mDevice = device;
    }

    public WifiP2pDevice getDevice() {
        return mDevice;
    }
}
