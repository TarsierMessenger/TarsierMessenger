package ch.tarsier.tarsier.test;

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
 * Created by benjamin on 28/11/14.
 */
public class NearbyListActivityTest extends ActivityInstrumentationTestCase2<NearbyListActivity> {

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
        List<Peer> peerList = new ArrayList<Peer>();
        Peer ben  =new Peer("Benpac","I am immortal");
        peerList.add(ben);
        peerList.add(new Peer("Romac", "Shut up ben"));
        onView(withText(R.string.tab_peer_name)).perform(click());
        mEventBus.post(new ReceivedNearbyPeersListEvent(peerList));
        peerList.add(ben);
        try {
            Thread.sleep(5000,0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mEventBus.post(new ReceivedNearbyPeersListEvent(peerList));
        peerList.add(ben);
        mEventBus.post(new ReceivedNearbyPeersListEvent(peerList));
        peerList.add(ben);
        mEventBus.post(new ReceivedNearbyPeersListEvent(peerList));
    }

}
