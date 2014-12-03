package ch.tarsier.tarsier.test;

import android.net.wifi.p2p.WifiP2pDevice;
import android.test.ActivityInstrumentationTestCase2;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.event.ReceivedNearbyPeersListEvent;
import ch.tarsier.tarsier.ui.activity.NearbyListActivity;
import ch.tarsier.tarsier.ui.fragment.NearbyPeerFragment;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withChild;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withClassName;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
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
        List<WifiP2pDevice> peerList = new ArrayList<WifiP2pDevice>();

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
