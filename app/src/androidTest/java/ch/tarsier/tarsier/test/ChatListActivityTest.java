package ch.tarsier.tarsier.test;

import android.app.Activity;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.ui.activity.ChatListActivity;
import ch.tarsier.tarsier.ui.adapter.ChatListAdapter;
import ch.tarsier.tarsier.ui.view.ChatListView;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

/**
 * @author gluthier
 */
public class ChatListActivityTest extends TarsierTestCase<ChatListActivity> {

    private Activity mActivity;

    public ChatListActivityTest() {
        super(ChatListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        super.mockRepositories();
        mActivity = getActivity();
    }

    public void testChatListIsClickable() {
        onView(withId(R.id.chat_list))
                .check(matches(isClickable()));
    }

    public void testGoodInformationsArePrinted() {
        ChatListView chatListView = (ChatListView) mActivity.findViewById(R.id.chat_list);
        ChatListAdapter chatListAdapter = chatListView.getChatListAdapter();

        assertChatInformationsAreTheSame(FillDBForTesting.chat1, chatListAdapter.getItem(0));
        assertChatInformationsAreTheSame(FillDBForTesting.chat2, chatListAdapter.getItem(1));
    }


    private void assertChatInformationsAreTheSame(Chat expected, Chat actual) {
        assertSame(expected.getId(), actual.getId());
        assertSame(expected.getTitle(), actual.getTitle());
        assertSame(expected.isPrivate(), actual.isPrivate());
        assertSame(expected.getHost(), actual.getHost());
        assertEquals(expected.getPicture().getByteCount(), actual.getPicture().getByteCount());
    }
}
