package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;

import ch.tarsier.tarsier.ui.activity.ProfileActivity;
// import ch.tarsier.tarsier.R;

// import static ch.tarsier.tarsier.test.matchers.HasErrorMatcher.hasError;
// import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
// import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
// import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
// import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
// import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
// import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
// import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

/**
 * @author romac
 */
public class ProfileActivityTest extends
        ActivityInstrumentationTestCase2<ProfileActivity> {

    public ProfileActivityTest() {
        super(ProfileActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    /*public void testUsernameRejectedIfTooShort() {
        onView(withId(R.id.username))
            .perform(click(), clearText(), closeSoftKeyboard());

        onView(withId(R.id.action_save_profile))
            .perform(click());

        onView(withId(R.id.username))
            .check(matches(hasError(R.string.error_username_length)));
    }

    public void testUsernameRejectedIfTooLong() {
        String text = "This username is longer than 36 characters";

        onView(withId(R.id.username))
                .perform(click(), clearText())
                .perform(typeText(text), closeSoftKeyboard());

        onView(withId(R.id.action_save_profile))
                .perform(click());

        onView(withId(R.id.username))
                .check(matches(hasError(R.string.error_username_length)));
    }

    public void testStatusMessageRejectedIfTooShort() {
        onView(withId(R.id.username))
                .perform(click(), typeText("romac"), closeSoftKeyboard());

        onView(withId(R.id.status_message))
                .perform(click(), clearText(), closeSoftKeyboard());

        onView(withId(R.id.action_save_profile))
                .perform(click());

        onView(withId(R.id.status_message))
                .check(matches(hasError(R.string.error_status_message_length)));
    }

    public void testStatusMessageRejectedIfTooLong() {
        String text = "This status message is longer than 50 characters. "
                    + "It really is longer than 50, hence the need for multiple lines.";

        onView(withId(R.id.username))
                .perform(click(), typeText("romac"), closeSoftKeyboard());

        onView(withId(R.id.status_message))
                .perform(click(), clearText())
                .perform(typeText(text), closeSoftKeyboard());

        onView(withId(R.id.action_save_profile))
                .perform(click());

        onView(withId(R.id.status_message))
                .check(matches(hasError(R.string.error_status_message_length)));
    }*/

}
