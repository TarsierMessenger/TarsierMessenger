package ch.tarsier.tarsier;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.MoreAsserts;
import android.widget.TextView;

/**
 * Created by benjamin on 17/10/14.
 */
public class ApplicationUITest extends ActivityInstrumentationTestCase2<HomeActivity> {
    public ApplicationUITest() {
        super(HomeActivity.class);
    }

    public void testUIDummy() {
        Activity mActivity = getActivity();
        TextView tw = (TextView) mActivity.findViewById(R.id.helloWorld);
        assertEquals("Hello world!", tw.getText());
    }
}
