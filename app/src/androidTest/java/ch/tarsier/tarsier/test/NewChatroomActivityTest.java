package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;

import ch.tarsier.tarsier.R;

import static ch.tarsier.tarsier.test.matchers.HasErrorMatcher.hasError;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

/**
 * @author gluthier
 */
public class NewChatroomActivityTest extends ActivityInstrumentationTestCase2<NewChatroomActivity> {

    public NewChatroomActivityTest() {
        super(NewChatroomActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testCreateRoomButtonShouldBeClickable() {
        onView(withId(R.id.create_chatroom))
                .check(matches(isClickable()));
    }

    public void testChatroomNameRejectedIfTooShort() {
        onView(withId(R.id.chatroom_name))
                .perform(click(), clearText(), closeSoftKeyboard());

        onView(withId(R.id.create_chatroom))
                .perform(click());

        onView(withId(R.id.chatroom_name))
                .check(matches(hasError(R.string.error_chat_room_name_length)));
    }

    public void testChatroomNameRejectedIfTooLong() {
        String text = "This chatroom name is longer than 36 characters";

        onView(withId(R.id.chatroom_name))
                .perform(click(), clearText())
                .perform(typeText(text), closeSoftKeyboard());

        onView(withId(R.id.create_chatroom))
                .perform(click());

        onView(withId(R.id.chatroom_name))
                .check(matches(hasError(R.string.error_chat_room_name_length)));
    }
}
