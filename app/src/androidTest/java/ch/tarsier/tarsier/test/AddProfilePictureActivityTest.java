package ch.tarsier.tarsier.test;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;

import ch.tarsier.tarsier.ui.activity.AddProfilePictureActivity;

/**
 * @author Benjamin Paccaud
 */
public class AddProfilePictureActivityTest extends ActivityInstrumentationTestCase2<AddProfilePictureActivity> {

    private static final int BITMAP_RECT_HEIGHT = 640;
    private static final int BITMAP_RECT_WIDTH = 480;


    public AddProfilePictureActivityTest() {
        super(AddProfilePictureActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testAddNewPicture() {
        // onView(withId(R.id.add_new_picture)).perform(click());
        // do stuff
        // pressBack();
    }

    public void testAddExistingPicture() {
        // onView(withId(R.id.add_from_existing)).perform(click());
        // do stuff
        // pressBack();
    }

}
