package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;
import ch.tarsier.tarsier.R;

import ch.tarsier.tarsier.ui.activity.ChatListActivity;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

/**
 * @author gluthier
 */
public class ChatListActivityTest extends ActivityInstrumentationTestCase2<ChatListActivity> {

    public ChatListActivityTest() {
        super(ChatListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
