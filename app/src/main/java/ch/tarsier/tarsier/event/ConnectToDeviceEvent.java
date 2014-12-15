package ch.tarsier.tarsier.event;

import android.net.wifi.p2p.WifiP2pDevice;

/**
 * This event is posted when we connect to another device,
 * before connecting to the actual chatroom.
 *
 * @author romac
 * @author amirrezaw
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
