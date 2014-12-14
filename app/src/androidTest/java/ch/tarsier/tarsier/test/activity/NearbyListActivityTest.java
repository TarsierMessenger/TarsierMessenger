package ch.tarsier.tarsier.test.activity;

import android.net.wifi.p2p.WifiP2pDevice;
import android.test.ActivityInstrumentationTestCase2;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.event.ReceivedNearbyPeersListEvent;
import ch.tarsier.tarsier.ui.activity.NearbyListActivity;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withContentDescription;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

/**
 * NearbyListActivityTest tests the NearbyListActivity class.
 *
 * @see ch.tarsier.tarsier.ui.activity.NearbyListActivity
 * @author benpac
 */
public class NearbyListActivityTest extends ActivityInstrumentationTestCase2<NearbyListActivity> {

    private static final long SLEEP_TIME = 1000;

    private  Bus mEventBus;

    private NearbyListActivity mActivity;

    public NearbyListActivityTest() {
        super(NearbyListActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mEventBus = Tarsier.app().getEventBus();
        mActivity = getActivity();
    }

    /**
     * This test the refresh of the fragment when a list of WifiP2pDevices is
     * posted through the EventBus
     */
    public void testListRefreshOnEventPost() {
        List<WifiP2pDevice> peerList = new ArrayList<>();

        WifiP2pDevice ben = createBen();
        WifiP2pDevice romac = createRomac();

        peerList.add(ben);
        peerList.add(romac);
        postAndWait(peerList);
        assertEquals(mActivity.getNearbyPeerFragment().getNearbyPeerAdapter().getCount(),
                     peerList.size());

        peerList.add(ben);
        postAndWait(peerList);
        assertEquals(mActivity.getNearbyPeerFragment().getNearbyPeerAdapter().getCount(),
                peerList.size());

        peerList.add(ben);
        peerList.add(ben);
        postAndWait(peerList);
        assertEquals(mActivity.getNearbyPeerFragment().getNearbyPeerAdapter().getCount(),
                peerList.size());

        List<WifiP2pDevice> peerList2 = new ArrayList<>();
        peerList2.add(ben);
        peerList2.add(romac);
        peerList2.add(ben);
        peerList2.add(romac);
        postAndWait(peerList2);
        assertEquals(mActivity.getNearbyPeerFragment().getNearbyPeerAdapter().getCount(),
                peerList2.size());
    }

    /**
     * Test that we are able to click on the devices
     */
    public void testClickOnDevice() {
        WifiP2pDevice ben = createBen();
        List<WifiP2pDevice> peerList = new ArrayList<>();
        peerList.add(ben);
        postAndWait(peerList);
        onView(withId(R.id.inside_nearby)).perform(click());
        onData(anything()).inAdapterView(withContentDescription(
                mActivity.getResources().getString(R.string.description_list_nearby_peer)))
                .atPosition(0).perform(click());
    }

    /**
     * test that we can click the create chat room button
     */
    public void testClickOnCreateChatroom() {
        onView(withId(R.id.create_new_chat_from_nearby)).check(matches(isClickable()));
    }

    /**
     * companion method to testListRefreshOnEventPost that post the event and impose a wait time
     * @param peers the list of WifiP2pDevices to be posted.
     */
    private void postAndWait(List<WifiP2pDevice> peers) {
        mEventBus.post(new ReceivedNearbyPeersListEvent(peers));
        try {
            Thread.sleep(SLEEP_TIME, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private WifiP2pDevice createBen() {
        WifiP2pDevice ben  = new WifiP2pDevice();
        ben.deviceName = "ben";
        ben.status = WifiP2pDevice.AVAILABLE;
        ben.deviceAddress = "his.home";
        return ben;
    }
    private WifiP2pDevice createRomac() {
        WifiP2pDevice romac = new WifiP2pDevice();
        romac.deviceName = "romac";
        romac.status = WifiP2pDevice.INVITED;
    return romac;
    }
}
