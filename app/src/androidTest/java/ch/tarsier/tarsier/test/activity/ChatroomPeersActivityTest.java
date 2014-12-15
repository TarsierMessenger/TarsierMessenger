package ch.tarsier.tarsier.test.activity;

import com.squareup.otto.Bus;

import android.widget.HeaderViewListAdapter;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.event.ReceivedChatroomPeersListEvent;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.test.TarsierTestCase;
import ch.tarsier.tarsier.ui.activity.ChatroomPeersActivity;
import ch.tarsier.tarsier.ui.adapter.ChatroomPeersAdapter;
import ch.tarsier.tarsier.ui.view.ChatroomPeersListView;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.selectedDescendantsMatch;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.hasContentDescription;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isFocusable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withContentDescription;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link ch.tarsier.tarsier.ui.activity.ChatroomPeersActivity}.
 *
 * @author romac
 */
public class ChatroomPeersActivityTest  extends TarsierTestCase<ChatroomPeersActivity> {

    private static final long SLEEP_TIME = 1000;

    private ChatroomPeersActivity mActivity;

    private ChatroomPeersAdapter mAdapter;

    private Bus mEventBus;

    public ChatroomPeersActivityTest() {
        super(ChatroomPeersActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        super.mockRepositories();
        mActivity = (ChatroomPeersActivity) getActivity();
        mAdapter = getAdapter();
        mEventBus = Tarsier.app().getEventBus();
        Tarsier.app().getMessagingManager().setDisabled(true);
    }

    private ChatroomPeersAdapter getAdapter() {
        ChatroomPeersListView peersListView = (ChatroomPeersListView) mActivity.findViewById(R.id.peers_list);
        return (ChatroomPeersAdapter) ((HeaderViewListAdapter) peersListView.getAdapter()).getWrappedAdapter();
    }

    public void testAdapterIsEmptyBeforeReceivingPeersList() {
        assertTrue(mAdapter.isEmpty());
    }

    public void testAdapterIsEmptyAfterReceivingEmptyPeersList() {
        mEventBus.post(new ReceivedChatroomPeersListEvent(new ArrayList<Peer>()));
        sleep();
        assertTrue(mAdapter.isEmpty());
    }

    public void testAdapterIsNotEmptyAfterReceivingNonEmptyPeersList() throws NoSuchModelException {
        List<Peer> peers = Tarsier.app().getPeerRepository().findAll();
        mEventBus.post(new ReceivedChatroomPeersListEvent(peers));
        sleep();
        assertFalse(mAdapter.isEmpty());
    }

    public void testAdapterCountMatchesPeersCount() throws NoSuchModelException {
        List<Peer> peers = Tarsier.app().getPeerRepository().findAll();
        mEventBus.post(new ReceivedChatroomPeersListEvent(peers));
        sleep();
        assertEquals(peers.size(), mAdapter.getCount());
    }


    public void testDisplayedPeersListDoesntIncludeOwnUser() throws NoSuchModelException {
        List<Peer> peers = Tarsier.app().getPeerRepository().findAll();
        Peer firstPeer = peers.get(0);
        Peer spy = spy(firstPeer);
        when(spy.isUser()).thenReturn(true);
        peers.set(0, spy);
        mEventBus.post(new ReceivedChatroomPeersListEvent(peers));
        sleep();

        for (int i = 0; i < getAdapter().getCount(); i += 1) {
            assertFalse(getAdapter().getItem(i).isUser());
        }
    }

    /*
    public void testPeersAreClickable() throws NoSuchModelException {
        List<Peer> peers = Tarsier.app().getPeerRepository().findAll();
        mEventBus.post(new ReceivedChatroomPeersListEvent(peers));
        sleep();

        for (Peer peer : peers) {
            onView(withContentDescription("Peer " + peer.getId()))
                    .check(matches(isClickable()));
        }
    }

    public void testClickingAPeerWillFindOrCreateTheCorrespondingChatroom()
            throws InvalidModelException, NoSuchModelException {

        List<Peer> peers = Tarsier.app().getPeerRepository().findAll();
        Peer firstPeer = peers.get(0);
        mEventBus.post(new ReceivedChatroomPeersListEvent(peers.subList(0, 1)));
        sleep();

        onView(withContentDescription("Peer " + firstPeer.getId()))
                .perform(click());

        sleep();

        verify(chatRepositoryMock).findPrivateChatForPeer(firstPeer);
    }
    */

    private void sleep() {
        try {
            Thread.sleep(SLEEP_TIME, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
