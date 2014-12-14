package ch.tarsier.tarsier.test.activity;

import android.app.Activity;

import junit.framework.Assert;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.test.FillDBForTesting;
import ch.tarsier.tarsier.test.TarsierTestCase;
import ch.tarsier.tarsier.ui.activity.ChatListActivity;
import ch.tarsier.tarsier.ui.adapter.ChatListAdapter;
import ch.tarsier.tarsier.ui.view.ChatListView;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isFocusable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

/**
 * ChatListActivityTest tests the ChatListActivity class.
 *
 * @see ch.tarsier.tarsier.ui.activity.ChatListActivity
 * @author gluthier
 */
public class ChatListActivityTest extends TarsierTestCase<ChatListActivity> {

    private Activity mActivity;
    private ChatListAdapter mChatListAdapter;

    public ChatListActivityTest() {
        super(ChatListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        super.mockRepositories();
        mActivity = getActivity();
        mChatListAdapter = getAdapter();
    }


    public void testChatListProperties() {
        onView(withId(R.id.chat_list))
                .check(matches(isClickable()));
        onView(withId(R.id.chat_list))
                .check(matches(isDisplayed()));
        onView(withId(R.id.chat_list))
                .check(matches(isFocusable()));
    }

    public void testAdapterNotEmpty() {
        assertFalse(mChatListAdapter.isEmpty());
    }

    public void testGetCountAdapter() {
        Assert.assertEquals(FillDBForTesting.allChats.size(), mChatListAdapter.getCount());
    }

    public void testGetItemAdapter() {
        assertEquals(FillDBForTesting.chat1, mChatListAdapter.getItem(0));
        assertEquals(FillDBForTesting.chat2, mChatListAdapter.getItem(1));
    }

    public void testGetItemIdAdapter() {
        assertEquals(FillDBForTesting.chat1.getId(), mChatListAdapter.getItemId(0));
        assertEquals(FillDBForTesting.chat2.getId(), mChatListAdapter.getItemId(1));
    }

    public void testChatInformationsArePrinted() {
        assertChatInformationsAreTheSame(FillDBForTesting.chat1, mChatListAdapter.getItem(0));
        assertChatInformationsAreTheSame(FillDBForTesting.chat2, mChatListAdapter.getItem(1));
    }


    private ChatListAdapter getAdapter() {
        ChatListView chatListView = (ChatListView) mActivity.findViewById(R.id.chat_list);
        return chatListView.getChatListAdapter();
    }

    private void assertChatInformationsAreTheSame(Chat expected, Chat actual) {
        assertSame(expected.getId(), actual.getId());
        assertSame(expected.getTitle(), actual.getTitle());
        assertSame(expected.isPrivate(), actual.isPrivate());
        assertSame(expected.getHost(), actual.getHost());
        assertEquals(expected.getPicture().getByteCount(), actual.getPicture().getByteCount());
    }
}
