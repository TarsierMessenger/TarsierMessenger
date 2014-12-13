package ch.tarsier.tarsier.test;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.ui.activity.ChatListActivity;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.verify;

/**
 * @author gluthier
 */
public class ChatListActivityTest extends TarsierTestCase<ChatListActivity> {

    public ChatListActivityTest() {
        super(ChatListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockRepositories();
        getActivity();
    }

    public void testChatListIsClickable() {
        onView(withId(R.id.chat_list))
                .check(matches(isClickable()));
    }
}
