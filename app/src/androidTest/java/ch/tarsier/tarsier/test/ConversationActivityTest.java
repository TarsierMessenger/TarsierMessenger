package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;
import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.ui.activity.ConversationActivity;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Created by Marin on 12.11.2014.
 */
public class ConversationActivityTest extends ActivityInstrumentationTestCase2<ConversationActivity> {

    public ConversationActivityTest() {
        super(ConversationActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }

    /* test the sendImageButton if is clickable when something is to be sent or not*/
    public void testSendMessageButtonClickable() {
        String messageSent="This is a new message to be sent.";
        onView(withId(R.id.sendImageButton)).check(matches(not(isClickable())));
        onView(withId(R.id.message_to_send)).perform(typeText(messageSent));
        onView(withId(R.id.sendImageButton)).check(matches(isClickable()));
        onView(withId(R.id.message_to_send)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.sendImageButton)).check(matches(not(isClickable())));
    }

    /*
    test if the scrolling fetch the last messages

    public void testScrollToFetchMessages() {

    }
    */
}
