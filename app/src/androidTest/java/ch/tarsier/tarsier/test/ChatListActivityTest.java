package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;

import ch.tarsier.tarsier.ui.activity.ChatListActivity;

/**
 * @author gluthier
 */
public class ChatListActivityTest extends ActivityInstrumentationTestCase2<ChatListActivity> {

    public ChatListActivityTest() {
        super(ChatListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
