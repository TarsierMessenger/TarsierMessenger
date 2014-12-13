package ch.tarsier.tarsier.test;

import ch.tarsier.tarsier.ui.activity.ChatListActivity;

/**
 * @author gluthier
 */
public class ChatListActivityTest extends TarsierTestCase<ChatListActivity> {

    public ChatListActivityTest() {
        super(ChatListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockRepositories();
        getActivity();
    }

    
}
