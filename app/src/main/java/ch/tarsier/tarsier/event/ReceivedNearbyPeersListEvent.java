package ch.tarsier.tarsier.event;

import android.net.wifi.p2p.WifiP2pDevice;

import java.util.List;

/**
 * ReceivedNearbyPeersListEvent is the event representing the reception of the list of nearby peers.
 *
 * @author romac
 */
public class ReceivedNearbyPeersListEvent {

    private final List<WifiP2pDevice> mPeers;

    public ReceivedNearbyPeersListEvent(List<WifiP2pDevice> peers) {
        mPeers = peers;
    }

    public List<WifiP2pDevice> getPeers() {
        return mPeers;
    }
}
