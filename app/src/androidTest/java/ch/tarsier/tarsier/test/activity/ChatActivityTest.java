package ch.tarsier.tarsier.test.activity;

import android.content.Intent;
import com.squareup.otto.Bus;
import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.event.DisplayMessageEvent;
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

    private BubbleAdapter mAdapter;
    private EndlessListView mListView;
    private Chat mChat;

    private MessageRepository mMessageRepository;

    private Bus mEventBus;

    private static final String MESSAGE_TO_SEND = "This is a new message to send in the Chat " +
            "Activity. This should appear at the end of the screen, be stored in the database and " +
            "be sent over the network. SEND";
    private static final String MESSAGE_NETWORK = "This is a new message to send in the Chat " +
            "Activity. This should appear at the end of the screen, be stored in the database and " +
            "be sent over the network. NETWORK";

    public ChatActivityTest() {
        super(ChatActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mEventBus = Tarsier.app().getEventBus();

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
            if(mMessageRepository.findByChatUntil(mChat, DateUtil.getNowTimestamp(), 1).isEmpty()) {
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
        for(int i=mAdapter.getCount()-1; i>=0; i--) {
            listViewItem = mAdapter.getItem(i);
            nextTimestamp = listViewItem.getDateTime();
            assertTrue(currentTimestamp >= nextTimestamp);
            currentTimestamp = nextTimestamp;
        }
    }

    public void testNewMessageSentIsDisplayed() {
        int itemCountBeforeSending = mAdapter.getCountWithoutSeparators();
        sendNewMessage();
        int itemCountAfterSending = mAdapter.getCountWithoutSeparators();

        assertEquals(itemCountBeforeSending+1, itemCountAfterSending);

        BubbleListViewItem listItem = mAdapter.getItem(mAdapter.getCount()-1);
        if(listItem.getEndlessListViewType() != BubbleAdapter.EndlessListViewType.DATE_SEPARATOR) {
            Message sentMessage = (Message) listItem;
            assertEquals(MESSAGE_TO_SEND, sentMessage.getText());
        } else {
            fail();
        }
    }

    public void testNewMessageSentStoredInDatabase() {
        sendNewMessage();

        try {
            Message messageFromDatabase = mMessageRepository.getLastMessageOf(mChat);
            assertEquals(MESSAGE_TO_SEND, messageFromDatabase.getText());
        } catch (InvalidModelException | NoSuchModelException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testNewMessageIsSentOverNetwork() {

    }

    public void testMessagesFetchedFromNetwork() {
        Message message = new Message(mChat.getId(), MESSAGE_NETWORK, DateUtil.getNowTimestamp());
        mEventBus.post(new DisplayMessageEvent(message, FillDBForTesting.peers[2], mChat));

        try {
            // Wait the event to be processed by the Chat Activity
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BubbleListViewItem listItem = mAdapter.getItem(mAdapter.getCount()-1);
        if(listItem.getEndlessListViewType() != BubbleAdapter.EndlessListViewType.DATE_SEPARATOR) {
            Message receivedMessage = (Message) listItem;
            assertEquals(MESSAGE_NETWORK, receivedMessage.getText());
        } else {
            fail();
        }
    }

    public void testOnlyFetchMessagesWhenNecessary() {

    }

    private void scrollToTopToFetchAllMessages() {
        while (!mListView.allMessagesLoaded()) {
            onData(instanceOf(Message.class))
                    .inAdapterView(allOf(withId(R.id.list_view), isDisplayed()))
                    .atPosition(0)
                    .check(matches(isDisplayed()));
        }
    }

    private void sendNewMessage() {
        onView(withId(R.id.message_to_send)).perform(typeText(MESSAGE_TO_SEND), closeSoftKeyboard());
        onView(withId(R.id.sendImageButton)).perform(click());
    }
}