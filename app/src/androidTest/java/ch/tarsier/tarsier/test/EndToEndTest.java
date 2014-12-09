package ch.tarsier.tarsier.test;

import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.prefs.UserPreferences;
import ch.tarsier.tarsier.ui.activity.HomeActivity;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withChild;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author benpac
 */
public class EndToEndTest extends TarsierTestCase<HomeActivity> {

    private UserPreferences mUserPrefMock;
    private final static String EMPTY_STRING = "";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mUserPrefMock = mock(UserPreferences.class);
        when(mUserPrefMock.getStatusMessage()).thenReturn(EMPTY_STRING);
        when(mUserPrefMock.getUsername()).thenReturn(EMPTY_STRING);
        Tarsier.app().setUserPreferences(mUserPrefMock);
        //GET activity AFTER mocking components...
        getActivity();
    }

    public EndToEndTest(Class activityClass) {
        super(activityClass);
    }

    public void testEndToEnd() {
        //setting username and status
        onView(withId(R.id.username_home)).perform(clearText(), typeText("Benpac"), closeSoftKeyboard());
        onView(withId(R.id.status_message_home)).perform(clearText(), typeText("I love SwEng!"), closeSoftKeyboard());
        onView(withId(R.id.lets_chat)).perform(click());
        //entering nearby
        
    }
}
