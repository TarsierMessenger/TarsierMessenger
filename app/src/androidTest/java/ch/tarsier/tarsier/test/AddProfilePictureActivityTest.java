package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;

import ch.tarsier.tarsier.AddProfilePictureActivity;
import ch.tarsier.tarsier.R;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Benjamin Paccaud on 22/10/14.
 */
public class AddProfilePictureActivityTest extends ActivityInstrumentationTestCase2<AddProfilePictureActivity> {

    @SuppressWarnings("deprecation")
    public AddProfilePictureActivityTest() {
        super("ch.tarsier.tarsier", AddProfilePictureActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testAddNewPicture() {
        onView(withId(R.id.add_new_picture)).perform(click());
        //do stuff
    }

    public void testAddExistingPicture() {
        onView(withId(R.id.add_from_existing)).perform(click());
        //do stuff
    }

}
