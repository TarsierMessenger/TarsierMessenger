package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;

import ch.tarsier.tarsier.ui.activity.ConversationActivity;

/**
 * Created by Marin on 12.11.2014.
 */
public class ConversationActivityTest extends ActivityInstrumentationTestCase2<ConversationActivity> {
    public ConversationActivityTest(Class<ConversationActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }
}
