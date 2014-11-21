package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by benjamin on 18/11/14.
 */
public class TarsierTestCase<T> extends ActivityInstrumentationTestCase2 {
    public TarsierTestCase(Class activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // XXX: Hack required to make Mockito work on Android
        System.setProperty("dexmaker.dexcache",
                getInstrumentation().getTargetContext().getCacheDir().getPath());
    }
}
