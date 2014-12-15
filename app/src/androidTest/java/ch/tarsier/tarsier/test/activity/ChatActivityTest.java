package ch.tarsier.tarsier.test.activity;

import android.content.Intent;

import junit.framework.Assert;

import org.mockito.Mockito;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.test.FillDBForTesting;
import ch.tarsier.tarsier.test.TarsierTestCase;
import ch.tarsier.tarsier.ui.activity.ChatActivity;
import ch.tarsier.tarsier.ui.adapter.BubbleAdapter;
import ch.tarsier.tarsier.ui.view.EndlessListView;
import ch.tarsier.tarsier.util.DateUtil;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.scrollTo;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
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

    public ChatActivityTest() {
        super(ChatActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ChatRepository chatRepository = Tarsier.app().getChatRepository();
        MessageRepository messageRepository = Tarsier.app().getMessageRepository();
        PeerRepository peerRepository = Tarsier.app().getPeerRepository();

        FillDBForTesting.populate(chatRepository, messageRepository, peerRepository);

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
            if(messageRepositoryMock.findByChatUntil(mChat, DateUtil.getNowTimestamp(), 1).isEmpty()) {
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
            onData(is(instanceOf(ChatActivity.class)))
                    .inAdapterView(withId(R.id.list_view))
                    .atPosition(mAdapter.getCount())
                    .perform(scrollTo());
        }
        Assert.assertEquals(FillDBForTesting.messagesChat1.length, mAdapter.getCount());
    }

    public void testMessagesFetchedInOrder() {

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

    }
}