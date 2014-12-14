package ch.tarsier.tarsier.test.activity;

import android.net.wifi.p2p.WifiP2pDevice;
import android.test.ActivityInstrumentationTestCase2;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.event.ReceivedNearbyPeersListEvent;
import ch.tarsier.tarsier.ui.activity.NearbyListActivity;

/**
 * NearbyListActivityTest tests the NearbyListActivity class.
 *
 * @see ch.tarsier.tarsier.ui.activity.NearbyListActivity
 * @author benpac
 */
public class NearbyListActivityTest extends ActivityInstrumentationTestCase2<NearbyListActivity> {

    private static final long SLEEP_TIME = 1000;

    private  Bus mEventBus;

    public NearbyListActivityTest() {
        super(NearbyListActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mEventBus = Tarsier.app().getEventBus();
        getActivity();
    }

    public void testEventSend() {
        List<WifiP2pDevice> peerList = new ArrayList<>();

        WifiP2pDevice ben  = new WifiP2pDevice();
        ben.deviceName = "ben";
        ben.status = WifiP2pDevice.AVAILABLE;
        peerList.add(ben);

        WifiP2pDevice romac = new WifiP2pDevice();
        romac.deviceName = "romac";
        romac.status = WifiP2pDevice.FAILED;
        peerList.add(romac);

        postAndWait(peerList);
        peerList.add(ben);

        postAndWait(peerList);
        peerList.add(ben);


        peerList.add(ben);
        postAndWait(peerList);

        List<WifiP2pDevice> peerList2 = new ArrayList<>();
        peerList2.add(ben);
        peerList2.add(romac);
        peerList2.add(ben);
        peerList2.add(romac);

        postAndWait(peerList2);
    }

    private void postAndWait(List<WifiP2pDevice> peers) {
        mEventBus.post(new ReceivedNearbyPeersListEvent(peers));
        try {
            Thread.sleep(SLEEP_TIME, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
