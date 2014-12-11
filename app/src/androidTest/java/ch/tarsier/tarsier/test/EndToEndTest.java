package ch.tarsier.tarsier.test;

import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.crypto.EC25519;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.event.ConnectedEvent;
import ch.tarsier.tarsier.event.ReceivedChatroomPeersListEvent;
import ch.tarsier.tarsier.event.ReceivedMessageEvent;
import ch.tarsier.tarsier.event.ReceivedNearbyPeersListEvent;
import ch.tarsier.tarsier.prefs.UserPreferences;
import ch.tarsier.tarsier.ui.activity.HomeActivity;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withContentDescription;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This test aims to visit every activity in the app and verify a usual use of the app
 *
 * @author benpac
 */
public class EndToEndTest extends TarsierTestCase<HomeActivity> {

    private UserPreferences mUserPrefMock;
    private UserPreferences mUserPrefStore;

    private final static String EMPTY_STRING = "";
    private final static int SLEEP_TIME = 1000;
    private Bus mEventBus;
    private List<WifiP2pDevice> listWifiP2pDevices;
    private List<Peer> listPeers;

    public EndToEndTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mEventBus = Tarsier.app().getEventBus();

        //store old user pref
        mUserPrefStore = Tarsier.app().getUserPreferences();
        //mock UserPreferences
        mUserPrefMock = mock(UserPreferences.class);
        when(mUserPrefMock.getKeyPair()).thenReturn(mUserPrefStore.getKeyPair());
        when(mUserPrefMock.getPicturePath()).thenReturn(mUserPrefStore.getPicturePath());
        when(mUserPrefMock.getPictureUri()).thenReturn(mUserPrefStore.getPictureUri());
        when(mUserPrefMock.getStatusMessage()).thenReturn(EMPTY_STRING);
        when(mUserPrefMock.getUsername()).thenReturn(EMPTY_STRING);
        Tarsier.app().setUserPreferences(mUserPrefMock);

        //create list of WifiP2pDevices
        setListWifiP2pDevices();
        setListPeers();

        //Get activity AFTER mocking components...
        getActivity();
    }

    protected void tearDown() throws Exception {
        Tarsier.app().setUserPreferences(mUserPrefStore);
        super.tearDown();
    }

    private void setListWifiP2pDevices() {
        listWifiP2pDevices = new ArrayList<>();
        WifiP2pDevice ben = new WifiP2pDevice();
        ben.deviceName = "Benpac";
        ben.status = WifiP2pDevice.AVAILABLE;
        ben.deviceAddress = "his.home";
        WifiP2pDevice gluthier = new WifiP2pDevice();
        gluthier.deviceName = "Gabriel";
        gluthier.status = WifiP2pDevice.AVAILABLE;
        gluthier.deviceAddress = "gluthier.ch";
        WifiP2pDevice amirezza = new WifiP2pDevice();
        amirezza.deviceName = "Gabriel";
        amirezza.status = WifiP2pDevice.AVAILABLE;
        amirezza.deviceAddress = "his.bed";
        listWifiP2pDevices.add(ben);
        listWifiP2pDevices.add(gluthier);
        listWifiP2pDevices.add(amirezza);
    }

    private void setListPeers() {
        listPeers = new ArrayList<>();
        Peer ben = new Peer("Benpac");
        ben.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
        ben.setStatusMessage("I love potatoes");
        ben.setPicturePath(mUserPrefMock.getPicturePath());
        listPeers.add(ben);
        Peer gluthier = new Peer("Gab");
        gluthier.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
        gluthier.setStatusMessage("I love chips");
        listPeers.add(gluthier);
        Peer amirezza = new Peer("Amirezza");
        amirezza.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
        amirezza.setStatusMessage("I love french fries");
        listPeers.add(amirezza);
    }

    public void testEndToEnd() {
        //setting username and status
        onView(withId(R.id.username_home)).perform(clearText(), typeText("Benpac"), closeSoftKeyboard());
        onView(withId(R.id.status_message_home)).perform(clearText(), typeText("I love SwEng!"), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).perform(click());
        //entering nearby
        //post false peers
        mEventBus.post(new ReceivedNearbyPeersListEvent(listWifiP2pDevices));
        // create chatroom
        onView(withId(R.id.create_new_chat_from_nearby)).perform(click());
        mEventBus.post(new ConnectedEvent(true));
        sendMessage("Hello world!");

        mEventBus.post(new ReceivedChatroomPeersListEvent(listPeers));

        mEventBus.post(new ReceivedMessageEvent("Hi there little tarsier!", listPeers.get(1), false));
        mEventBus.post(new ReceivedMessageEvent("So many people!!", listPeers.get(2), false));
        mEventBus.post(new ReceivedMessageEvent("I love this app! it's the best thing I know!",
                listPeers.get(0), false));
        sendMessage("I know, right");
        //try to send empty message
        onView(withId(R.id.sendImageButton)).perform(click());

        //Go private
        mEventBus.post(new ReceivedMessageEvent("I prefere talking in private", listPeers.get(1), true));
        waitTest(3); // wait to see toast
        onView(withId(R.id.goto_chatroom_peers_activity)).perform(click());
        mEventBus.post(new ReceivedChatroomPeersListEvent(listPeers));

        onView(withText(listPeers.get(1).getUserName())).perform(click());
        sendMessage("Yeah privacy is good. I really think amirezza is a nice guy thought");

        //got to profile
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.action_profile)).perform(click());
        //change name and status
        onView(withId(R.id.username)).perform(clearText(), typeText("Batman"), closeSoftKeyboard());
        onView(withId(R.id.status_message_profile_activity)).perform(
                clearText(),
                typeText("I'm the night"),
                closeSoftKeyboard()
        );
        onView(withId(R.id.action_save_profile)).perform(click());

        //back to the Chat
        sendMessage("Brb, criminels don't wait!");

        //quit activity by pressing the back stack
        pressBack();
        pressBack();
        onView(withText(R.string.chatroom_default_title)).perform(click());
    }

    private void sendMessage(String message) {
        onView(withId(R.id.message_to_send)).perform(typeText(message), closeSoftKeyboard());
        onView(withId(R.id.sendImageButton)).perform(click());
    }

    private void waitTest(int numberOfWait) {
        try {
            Thread.sleep(numberOfWait*SLEEP_TIME, 0);
        } catch (InterruptedException ie) {
            fail();
            ie.printStackTrace();
        }
    }
}
