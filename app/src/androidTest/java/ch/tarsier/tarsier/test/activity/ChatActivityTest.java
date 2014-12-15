package ch.tarsier.tarsier.test.activity;

import android.content.Intent;
import android.util.Log;

import junit.framework.Assert;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.test.FillDBForTesting;
import ch.tarsier.tarsier.test.TarsierTestCase;
import ch.tarsier.tarsier.ui.activity.ChatActivity;
import ch.tarsier.tarsier.ui.adapter.BubbleAdapter;
import ch.tarsier.tarsier.ui.view.BubbleListViewItem;
import ch.tarsier.tarsier.ui.view.EndlessListView;
import ch.tarsier.tarsier.util.DateUtil;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.scrollTo;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

/**
 * @author xawill
 */
public class ChatActivityTest extends TarsierTestCase<ChatActivity> {

    private ChatActivity mActivity;
    private BubbleAdapter mAdapter;
    private EndlessListView mListView;
    private Chat mChat;

    private ChatRepository mChatRepository;
    private MessageRepository mMessageRepository;
    private PeerRepository mPeerRepository;

    public ChatActivityTest() {
        super(ChatActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mChatRepository = Tarsier.app().getChatRepository();
        mMessageRepository = Tarsier.app().getMessageRepository();
        mPeerRepository = Tarsier.app().getPeerRepository();

        FillDBForTesting.populate(mChatRepository, mMessageRepository, mPeerRepository);

        mChat = FillDBForTesting.chat1;

        Intent chatIntent = new Intent(Tarsier.app(), ChatActivity.class);
        chatIntent.putExtra(ChatActivity.EXTRA_CHAT_MESSAGE_KEY, mChat);
        setActivityIntent(chatIntent);

        mActivity = (ChatActivity) getActivity();
        mListView = (EndlessListView) mActivity.findViewById(R.id.list_view);
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
        while (!mListView.allMessagesLoaded()) {
            onData(instanceOf(Message.class))
                    .inAdapterView(allOf(withId(R.id.list_view), isDisplayed()))
                    .atPosition(0)
                    .check(matches(isDisplayed()));
        }
        assertEquals(FillDBForTesting.messagesChat1.length,
                    mAdapter.getCount() - mAdapter.getNumberOfDateSeparators());
    }

    /*public void testMessagesFetchedInOrder() {

    }

    public void testDateSeparatorsAreCorrectlyInserted() {

    }

    public void testOnlyFetchMessagesWhenNecessary() {

    }

    public void testNewMessageSentIsDisplayed() {

    }

    public void testNewMessageSentStoredInDatabase() {

    }

    public void testNewMessageSentIsSentOverNetwork() {

    }

    public void testDisplayNewMessagesFromNetwork() {

    }*/
}