package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;

import ch.tarsier.tarsier.NewDiscussionActivity;
import ch.tarsier.tarsier.R;

import static ch.tarsier.tarsier.test.matchers.HasErrorMatcher.hasError;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isChecked;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

/**
 * Created by gluthier
 */
public class NewDiscussionActivityTest extends ActivityInstrumentationTestCase2<NewDiscussionActivity> {

    public NewDiscussionActivityTest() {
        super(NewDiscussionActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testCheckboxShouldBeCheckedByDefault() {
        onView(withId(R.id.join_on_invitation)).check(matches(isChecked()));
    }

    public void testCreateRoomShouldBeClickable() {
        onView(withId(R.id.create_room)).check(matches(isClickable()));
    }

    public void testTypingTooLongChatRoomName() {
        onView(withId(R.id.chat_room_name)).perform(typeText("This a really too long name for a chat room!"));
        onView(withId(R.id.create_room)).perform(click());
        //TODO
        //onView(withId(R.id.chat_room_name)).check(matches(hasError(R.string.error_username_length)));
    }

    public void testTypingAndPressBack() {
        onView(withId(R.id.chat_room_name)).perform(typeText("Chat name example"));
        onView(withId(R.id.create_room)).perform(click());
        //We should be on the new activity
        //TODO R.id.new_name
        // onView(withId(R.id.new_name)).check(matches(withText("Chat name example")));
        pressBack();
        onView(withId(R.id.chat_room_name)).check(matches(withText("Chat name example")));
    }
}
