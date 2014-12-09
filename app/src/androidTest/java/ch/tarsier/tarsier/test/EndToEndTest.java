package ch.tarsier.tarsier.test;

import android.net.wifi.p2p.WifiP2pDevice;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.event.ReceivedChatroomPeersListEvent;
import ch.tarsier.tarsier.event.ReceivedNearbyPeersListEvent;
import ch.tarsier.tarsier.prefs.UserPreferences;
import ch.tarsier.tarsier.ui.activity.HomeActivity;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author benpac
 */
public class EndToEndTest extends TarsierTestCase<HomeActivity> {

    private UserPreferences mUserPrefMock;
    private final static String EMPTY_STRING = "";
    private Bus mEventBus;
    private List<WifiP2pDevice> listWifiP2pDevices;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mEventBus = Tarsier.app().getEventBus();

        mUserPrefMock = mock(UserPreferences.class);
        when(mUserPrefMock.getStatusMessage()).thenReturn(EMPTY_STRING);
        when(mUserPrefMock.getUsername()).thenReturn(EMPTY_STRING);
        Tarsier.app().setUserPreferences(mUserPrefMock);

        setListPeers();
        //Get activity AFTER mocking components...
        getActivity();
    }

    private void setListPeers() {
        listWifiP2pDevices = new ArrayList<>();
        WifiP2pDevice ben = new WifiP2pDevice();
        ben.deviceName = "Benpac";
        ben.status = WifiP2pDevice.AVAILABLE;
        WifiP2pDevice gluthier = new WifiP2pDevice();
        gluthier.deviceName = "Gabriel";
        gluthier.status = WifiP2pDevice.AVAILABLE;
        WifiP2pDevice amirezza = new WifiP2pDevice();
        amirezza.deviceName = "Gabriel";
        amirezza.status = WifiP2pDevice.AVAILABLE;
        listWifiP2pDevices.add(ben);
        listWifiP2pDevices.add(gluthier);
        listWifiP2pDevices.add(amirezza);
    }

    public EndToEndTest(Class activityClass) {
        super(activityClass);
    }

    public void testEndToEnd() {
        //setting username and status
        onView(withId(R.id.username_home)).perform(clearText(), typeText("Benpac"), closeSoftKeyboard());
        onView(withId(R.id.status_message_home)).perform(clearText(), typeText("I love SwEng!"), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).perform(click());
        //entering nearby
        //post false peers
        mEventBus.post(new ReceivedNearbyPeersListEvent(listWifiP2pDevices));
        //select peers.
    }
}
