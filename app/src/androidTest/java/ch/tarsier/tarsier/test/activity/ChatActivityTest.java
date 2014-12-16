package ch.tarsier.tarsier.test.activity;

import android.content.Intent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.event.DisplayMessageEvent;
import ch.tarsier.tarsier.event.SendMessageEvent;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.test.FillDBForTesting;
import ch.tarsier.tarsier.test.TarsierTestCase;
import ch.tarsier.tarsier.ui.activity.ChatActivity;
import ch.tarsier.tarsier.ui.adapter.BubbleAdapter;
import ch.tarsier.tarsier.ui.view.BubbleListViewItem;
import ch.tarsier.tarsier.ui.view.EndlessListView;
import ch.tarsier.tarsier.util.DateUtil;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * @author xawill
 */
public class ChatActivityTest extends TarsierTestCase<ChatActivity> {

    private static final int ONE_SECOND = 1000;

    private BubbleAdapter mAdapter;
    private EndlessListView mListView;
    private Chat mChat;

    private MessageRepository mMessageRepository;

    private Bus mEventBus;
    private boolean mEventTriggered;

    private static final String MESSAGE_1 = "This is a new message to send in the Chat Activity. "
            + "This should appear at the end of the screen, be stored in the database and be sent "
            + "over the network. MESSAGE_1";
    private static final String MESSAGE_2 = "This is a new message to send in the Chat Activity. "
            + "This should appear at the end of the screen, be stored in the database and be sent "
            + "over the network. MESSAGE_2";
    private static final String MESSAGE_3 = "This is a new message to send in the Chat Activity. "
            + "This should appear at the end of the screen, be stored in the database and be sent "
            + "over the network. MESSAGE_3";
    private static final String MESSAGE_4 = "This is a new message to send in the Chat Activity. "
            + "This should appear at the end of the screen, be stored in the database and be sent "
            + "over the network. MESSAGE_4";

    public ChatActivityTest() {
        super(ChatActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mEventBus = Tarsier.app().getEventBus();
        mEventBus.register(this);
        mEventTriggered = false;

        ChatRepository chatRepository = Tarsier.app().getChatRepository();
        mMessageRepository = Tarsier.app().getMessageRepository();
        PeerRepository peerRepository = Tarsier.app().getPeerRepository();

        FillDBForTesting.populate(chatRepository, mMessageRepository, peerRepository);

        mChat = FillDBForTesting.chat1;

        Intent chatIntent = new Intent(Tarsier.app(), ChatActivity.class);
        chatIntent.putExtra(ChatActivity.EXTRA_CHAT_MESSAGE_KEY, mChat);
        setActivityIntent(chatIntent);

        ChatActivity activity = (ChatActivity) getActivity();
        mListView = (EndlessListView) activity.findViewById(R.id.list_view);
        mAdapter = (BubbleAdapter) mListView.getAdapter();
    }

    public void testAdapterNotEmpty() {
        try {
            if (mMessageRepository.findByChatUntil(mChat, DateUtil.getNowTimestamp(), 1).isEmpty()) {
                assertTrue(mAdapter.isEmpty());
            } else {
                assertFalse(mAdapter.isEmpty());
            }
        } catch (NoSuchModelException e) {
            e.printStackTrace();
        }
    }

    public void testAdapterCount() {
        scrollToTopToFetchAllMessages();
        assertEquals(FillDBForTesting.messagesChat1.length,
                mAdapter.getCountWithoutSeparators());
    }

    public void testMessagesFetchedInOrder() {
        scrollToTopToFetchAllMessages();

        BubbleListViewItem listViewItem;
        long currentTimestamp = DateUtil.getNowTimestamp();
        long nextTimestamp;
        for (int i=mAdapter.getCount()-1; i>=0; i--) {
            listViewItem = mAdapter.getItem(i);
            nextTimestamp = listViewItem.getDateTime();
            assertTrue(currentTimestamp >= nextTimestamp);
            currentTimestamp = nextTimestamp;
        }
    }

    public void testNewMessageSentIsDisplayed() {
        int itemCountBeforeSending = mAdapter.getCountWithoutSeparators();

        onView(withId(R.id.message_to_send)).perform(typeText(MESSAGE_1), closeSoftKeyboard());
        onView(withId(R.id.sendImageButton)).perform(click());

        int itemCountAfterSending = mAdapter.getCountWithoutSeparators();

        assertEquals(itemCountBeforeSending+1, itemCountAfterSending);

        BubbleListViewItem listItem = mAdapter.getItem(mAdapter.getCount()-1);
        if (listItem.getEndlessListViewType() != BubbleAdapter.EndlessListViewType.DATE_SEPARATOR) {
            Message sentMessage = (Message) listItem;
            assertEquals(MESSAGE_1, sentMessage.getText());
        } else {
            fail();
        }
    }

    public void testNewMessageSentStoredInDatabase() {
        onView(withId(R.id.message_to_send)).perform(typeText(MESSAGE_2), closeSoftKeyboard());
        onView(withId(R.id.sendImageButton)).perform(click());

        try {
            Message messageFromDatabase = mMessageRepository.getLastMessageOf(mChat);
            assertEquals(MESSAGE_2, messageFromDatabase.getText());
        } catch (InvalidModelException | NoSuchModelException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testNewMessageIsSentOverNetwork() {
        onView(withId(R.id.message_to_send)).perform(typeText(MESSAGE_3), closeSoftKeyboard());
        onView(withId(R.id.sendImageButton)).perform(click());

        BubbleListViewItem listItem = mAdapter.getItem(mAdapter.getCount()-1);
        if (listItem.getEndlessListViewType() != BubbleAdapter.EndlessListViewType.DATE_SEPARATOR
                && mEventTriggered) {
            Message receivedMessage = (Message) listItem;
            assertEquals(MESSAGE_3, receivedMessage.getText());
        } else {
            fail();
        }

        mEventTriggered = false;
    }

    @Subscribe
    public void onSendMessageEvent(SendMessageEvent event) {
        mEventTriggered = true;
    }

    public void testMessagesFetchedFromNetwork() {
        Message message = new Message(mChat.getId(), MESSAGE_4, DateUtil.getNowTimestamp());
        mEventBus.post(new DisplayMessageEvent(message, FillDBForTesting.peers[2], mChat));

        try {
            // Wait the event to be processed by the Chat Activity
            Thread.sleep(ONE_SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BubbleListViewItem listItem = mAdapter.getItem(mAdapter.getCount()-1);
        if (listItem.getEndlessListViewType() != BubbleAdapter.EndlessListViewType.DATE_SEPARATOR) {
            Message receivedMessage = (Message) listItem;
            assertEquals(MESSAGE_4, receivedMessage.getText());
        } else {
            fail();
        }
    }

    private void scrollToTopToFetchAllMessages() {
        while (!mListView.allMessagesLoaded()) {
            onData(instanceOf(Message.class))
                    .inAdapterView(allOf(withId(R.id.list_view), isDisplayed()))
                    .atPosition(0)
                    .check(matches(isDisplayed()));
        }
    }
}