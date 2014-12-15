package ch.tarsier.tarsier.test.activity;

import android.test.ActivityInstrumentationTestCase2;
import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.test.TarsierTestCase;
import ch.tarsier.tarsier.ui.activity.ChatActivity;
import ch.tarsier.tarsier.ui.activity.ChatListActivity;
import ch.tarsier.tarsier.ui.adapter.BubbleAdapter;
import ch.tarsier.tarsier.ui.adapter.ChatListAdapter;
import ch.tarsier.tarsier.ui.view.ChatListView;
import ch.tarsier.tarsier.ui.view.EndlessListView;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * @author xawill
 */
public class ChatActivityTest extends TarsierTestCase<ChatActivity> {

    private ChatActivity mActivity;
    private BubbleAdapter mAdapter;

    public ChatActivityTest() {
        super(ChatActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        super.mockRepositories();
        mActivity = (ChatActivity) getActivity();
        EndlessListView listView = (EndlessListView) mActivity.findViewById(R.id.list_view);
        mAdapter = (BubbleAdapter) listView.getAdapter();
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