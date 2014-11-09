package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;

import ch.tarsier.tarsier.HomeActivity;
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

/**
 * @author Benjamin Paccaud
 */
public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testStartButtonClickability() {
        onView(withId(R.id.lets_chat)).perform(click());
        //nothing should happen
        onView(withId(R.id.username)).perform(typeText("benpac"));
        onView(withId(R.id.lets_chat)).perform(click());
        //nothing should happen
        onView(withId(R.id.status_message)).perform(typeText("my status"), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).check(matches(isClickable()));
        onView(withId(R.id.username)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).perform(click());
        // nothing should happen
        onView(withId(R.id.username)).perform(typeText("Benpac"), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).check(matches(isClickable()));
        onView(withId(R.id.lets_chat)).perform(click());

    }

    public void testAddPictureClick() {
        onView(withId(R.id.picture)).check(matches(isClickable()));
        onView(withId(R.id.picture)).perform(click());

        // we are on AddProfilePictureActivity
        onView(withId(R.id.add_from_existing)).check(matches(isClickable()));
        onView(withId(R.id.add_new_picture)).check(matches(isClickable()));

        // click and check intent for existing picture
        // onView(withId(R.id.add_from_existing)).perform(click());
        // get from gallery
        // pressBack();

        // click and check intent for new picture
        // onView(withId(R.id.add_from_existing)).perform(click());
        // get from camera
        // pressBack();

        // back to Home
        pressBack();
    }

    public void testUsernameTooShort() {
        onView(withId(R.id.username)).perform(typeText("benpac"), closeSoftKeyboard());
        onView(withId(R.id.username)).perform(clearText());
        onView(withId(R.id.username)).check(matches(hasError(R.string.error_username_length)));
        onView(withId(R.id.username)).perform(clearText(), typeText("  "), closeSoftKeyboard());
        onView(withId(R.id.username)).check(matches(hasError(R.string.error_username_length)));
    }

    public void testUsernameTooLong() {
        onView(withId(R.id.username)).perform(typeText("Benpac benpac benpac this is longer that the accepted value of 36 caracters"),
                closeSoftKeyboard());
        onView(withId(R.id.username)).check(matches(hasError(R.string.error_username_length)));
    }

    public void testStatusTooShort() {
        onView(withId(R.id.status_message)).perform(typeText("benpac"), closeSoftKeyboard());
        onView(withId(R.id.status_message)).perform(clearText());
        onView(withId(R.id.status_message)).check(matches(hasError(R.string.error_status_message_length)));
        onView(withId(R.id.username)).perform(clearText(), typeText("  "), closeSoftKeyboard());
        onView(withId(R.id.username)).check(matches(hasError(R.string.error_status_message_length)));
    }

    public void testStatusTooLong() {
        onView(withId(R.id.status_message)).perform(typeText("A very long status message that should not pass "
                                                            + "as it clearly has more than 50 caracters"), closeSoftKeyboard());
        onView(withId(R.id.status_message)).check(matches(hasError(R.string.error_status_message_length)));
    }


}
