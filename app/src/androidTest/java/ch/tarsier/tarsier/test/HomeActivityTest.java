package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;

import ch.tarsier.tarsier.ui.activity.HomeActivity;
import ch.tarsier.tarsier.R;

import static ch.tarsier.tarsier.test.matchers.HasErrorMatcher.hasError;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * @author Benjamin Paccaud
 */
public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    private static final String STATUS_TOO_LONG = "A very long status message that should not pass "
                                                   + "as it clearly has more than 50 characters";
    private static final String WHITE_SPACE = "  \n ";
    private static final String USERNAME_TOO_LONG = "This is longer that the accepted value of 36 characters";
    private static final String USERNAME_OK = "Benpac";
    private static final String STATUS_OK = "My status message is ok";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testStartButtonClickability() {
        emptyUsernameAndStatus();

        onView(withId(R.id.lets_chat)).check(matches(not(isClickable())));

        onView(withId(R.id.username_home)).perform(typeText(USERNAME_OK), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).check(matches(not(isClickable())));

        onView(withId(R.id.status_message_home)).perform(typeText(STATUS_OK), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).check(matches(isClickable()));

        onView(withId(R.id.username_home)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).check(matches(not(isClickable())));

        onView(withId(R.id.username_home)).perform(typeText(USERNAME_OK), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).check(matches(isClickable()));

        onView(withId(R.id.status_message_home)).perform(clearText(),
                                                    typeText(STATUS_TOO_LONG),
                                                    closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).check(matches(not(isClickable())));

        onView(withId(R.id.status_message_home)).perform(clearText(), typeText(STATUS_OK), closeSoftKeyboard());
        onView(withId(R.id.username_home)).perform(clearText(), typeText(USERNAME_TOO_LONG), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).check(matches(not(isClickable())));
    }

    public void testAddPictureClick() {
        onView(withId(R.id.picture)).check(matches(isClickable()));
        onView(withId(R.id.picture)).perform(click());

        // we are on AddProfilePictureActivity
        onView(withId(R.id.add_from_existing)).check(matches(isClickable()));
        onView(withId(R.id.add_new_picture)).check(matches(isClickable()));

        // back to Home
        pressBack();
    }

    public void testUsernameTooShort() {

        emptyUsernameAndStatus();
        onView(withId(R.id.username_home)).perform(typeText(USERNAME_OK), closeSoftKeyboard());
        onView(withId(R.id.username_home)).perform(clearText());
        onView(withId(R.id.username_home)).check(matches(hasError(R.string.error_username_length)));
        onView(withId(R.id.username_home)).perform(clearText(), typeText(WHITE_SPACE), closeSoftKeyboard());
        onView(withId(R.id.username_home)).check(matches(hasError(R.string.error_username_whitespace)));
    }

    public void testUsernameTooLong() {
        emptyUsernameAndStatus();
        onView(withId(R.id.username_home)).perform(
                typeText(USERNAME_TOO_LONG),
                closeSoftKeyboard());
        onView(withId(R.id.username_home)).check(matches(hasError(R.string.error_username_length)));
    }

    public void testStatusTooShort() {
        emptyUsernameAndStatus();
        onView(withId(R.id.status_message_home)).perform(typeText(USERNAME_OK), closeSoftKeyboard());
        onView(withId(R.id.status_message_home)).perform(clearText());
        onView(withId(R.id.status_message_home)).check(matches(hasError(R.string.error_status_message_length)));
        onView(withId(R.id.status_message_home)).perform(clearText(), typeText(WHITE_SPACE), closeSoftKeyboard());
        onView(withId(R.id.status_message_home)).check(matches(hasError(R.string.error_status_message_whitespace)));
    }

    public void testStatusTooLong() {
        onView(withId(R.id.status_message_home)).perform(
                clearText(),
                typeText(STATUS_TOO_LONG),
                closeSoftKeyboard());
        onView(withId(R.id.status_message_home)).check(matches(hasError(R.string.error_status_message_length)));
    }

    private void emptyUsernameAndStatus() {
        onView(withId(R.id.status_message_home)).perform(clearText());
        onView(withId(R.id.username_home)).perform(clearText());
    }
}