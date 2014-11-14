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

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testStartButtonClickability() {
<<<<<<< HEAD
        onView(withId(R.id.lets_chat)).perform(click());
        //nothing should happen
        onView(withId(R.id.username)).perform(clearText(), typeText("benpac"));
        onView(withId(R.id.lets_chat)).perform(click());
        //nothing should happen
        onView(withId(R.id.status_message)).perform(clearText(), typeText("my status"), closeSoftKeyboard());
=======
        emptyUsernameAndStatus();

        onView(withId(R.id.lets_chat)).check(matches(not(isClickable())));

        onView(withId(R.id.username)).perform(typeText("benpac"));
        onView(withId(R.id.lets_chat)).check(matches(not(isClickable())));

        onView(withId(R.id.status_message)).perform(typeText("my status"), closeSoftKeyboard());
>>>>>>> add cropping of rectangle if crop intent not present on device.
        onView(withId(R.id.lets_chat)).check(matches(isClickable()));

        onView(withId(R.id.username)).perform(clearText(), closeSoftKeyboard());
<<<<<<< HEAD
        onView(withId(R.id.lets_chat)).perform(click());
        // nothing should happen
        onView(withId(R.id.username)).perform(clearText(), typeText("Benpac"), closeSoftKeyboard());
=======
        onView(withId(R.id.lets_chat)).check(matches(not(isClickable())));

        onView(withId(R.id.username)).perform(typeText("Benpac"), closeSoftKeyboard());
>>>>>>> add cropping of rectangle if crop intent not present on device.
        onView(withId(R.id.lets_chat)).check(matches(isClickable()));

        onView(withId(R.id.status_message)).perform(clearText(), typeText("more than 50 characters string stringslalalalalalalala"), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).check(matches(not(isClickable())));

        onView(withId(R.id.status_message)).perform(clearText(), typeText("my status"), closeSoftKeyboard());
        onView(withId(R.id.username)).perform(clearText(), typeText("more than 36 characters string strings"), closeSoftKeyboard());
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
<<<<<<< HEAD
        onView(withId(R.id.username)).perform(clearText());
=======
        emptyUsernameAndStatus();
>>>>>>> add cropping of rectangle if crop intent not present on device.
        onView(withId(R.id.username)).perform(typeText("benpac"), closeSoftKeyboard());
        onView(withId(R.id.username)).perform(clearText());
        onView(withId(R.id.username)).check(matches(hasError(R.string.error_username_length)));
        onView(withId(R.id.username)).perform(clearText(), typeText("  "), closeSoftKeyboard());
        onView(withId(R.id.username)).check(matches(hasError(R.string.error_username_whitespace)));
    }

    public void testUsernameTooLong() {
<<<<<<< HEAD
        onView(withId(R.id.username)).perform(
                clearText(),
                typeText("This is longer that the accepted value of 36 characters"),
=======
        emptyUsernameAndStatus();
        onView(withId(R.id.username)).perform(typeText("Benpac benpac benpac this is longer that the accepted value of 36 caracters"),
>>>>>>> add cropping of rectangle if crop intent not present on device.
                closeSoftKeyboard());
        onView(withId(R.id.username)).check(matches(hasError(R.string.error_username_length)));
    }

    public void testStatusTooShort() {
<<<<<<< HEAD
        onView(withId(R.id.status_message)).perform(clearText(), typeText("benpac"), closeSoftKeyboard());
=======
        emptyUsernameAndStatus();
        onView(withId(R.id.status_message)).perform(typeText("benpac"), closeSoftKeyboard());
>>>>>>> add cropping of rectangle if crop intent not present on device.
        onView(withId(R.id.status_message)).perform(clearText());
        onView(withId(R.id.status_message)).check(matches(hasError(R.string.error_status_message_length)));
        onView(withId(R.id.status_message)).perform(clearText(), typeText("  "), closeSoftKeyboard());
        onView(withId(R.id.status_message)).check(matches(hasError(R.string.error_status_message_whitespace)));
    }

    public void testStatusTooLong() {
<<<<<<< HEAD
        onView(withId(R.id.status_message)).perform(
                clearText(),
                typeText("A very long status message that should not pass "
                       + "as it clearly has more than 50 characters"),
                closeSoftKeyboard());
=======
        emptyUsernameAndStatus();
        onView(withId(R.id.status_message)).perform(typeText("A very long status message that should not pass "
                                                            + "as it clearly has more than 50 caracters"), closeSoftKeyboard());
>>>>>>> add cropping of rectangle if crop intent not present on device.
        onView(withId(R.id.status_message)).check(matches(hasError(R.string.error_status_message_length)));
    }

    private void emptyUsernameAndStatus() {
        onView(withId(R.id.status_message)).perform(clearText());
        onView(withId(R.id.username)).perform(clearText());
    }


}
