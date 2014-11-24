package ch.tarsier.tarsier.test;

import android.test.AndroidTestCase;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.repository.ChatRepository;

/**
 * @author gluthier
 */
public class ChatRepositoryTest extends AndroidTestCase {

    private ChatRepository mChatRepository;
    private Chat mDummyPrivateChat;
    private Chat mDummyPublicChat;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Tarsier.app().reset();
        mChatRepository = Tarsier.app().getChatRepository();
        Peer host = new Peer("Olivier", 1);

        mDummyPrivateChat = new Chat();
        mDummyPrivateChat.setPrivate(true);
        mDummyPrivateChat.setHost(host);

        mDummyPublicChat = new Chat();
        mDummyPublicChat.setPrivate(false);
        mDummyPublicChat.setHost(host);
        mDummyPublicChat.setTitle("Public chat title");
    }
}
