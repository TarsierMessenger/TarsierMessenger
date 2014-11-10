package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;
import ch.tarsier.tarsier.R;

import ch.tarsier.tarsier.DiscussionsActivity;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isClickable;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

/**
 * Created by gluthier
 */
public class DiscussionsActivityTest extends ActivityInstrumentationTestCase2<DiscussionsActivity> {

    public DiscussionsActivityTest() {
        super(DiscussionsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testListIsClickable() {
        onView(withId(R.id.list_discussions)).check(matches(isClickable()));
    }

}
