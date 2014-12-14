package ch.tarsier.tarsier.test.util;

import android.app.Activity;
import android.os.Environment;

import ch.tarsier.tarsier.test.TarsierTestCase;
import ch.tarsier.tarsier.ui.activity.HomeActivity;
import ch.tarsier.tarsier.util.BitmapFromPath;

/**
 * BitmapFromPathTest tests the BitmapFromPath class.
 *
 * @see ch.tarsier.tarsier.util.BitmapFromPath
 * @author gluthier
 */
public class BitmapFromPathTest extends TarsierTestCase<HomeActivity> {

    private static final String FILEPATH = Environment.getExternalStorageDirectory()
            + "/profile_picture_temp.png";

    private Activity mActivity;

    public BitmapFromPathTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    public void testGetBitmapWithNullContext() {
        try {
            BitmapFromPath.getBitmapFromPath(null, "not empty filepath");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("Context is null.", e.getMessage());
        }
    }

    public void testGetBitmapFromPathWithGoodArguments() {
        try {
            BitmapFromPath.getBitmapFromPath(mActivity.getBaseContext(), FILEPATH);
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }
}
