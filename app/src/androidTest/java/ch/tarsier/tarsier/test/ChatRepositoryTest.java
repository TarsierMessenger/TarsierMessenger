package ch.tarsier.tarsier.test;

import android.test.AndroidTestCase;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.network.old.MyConnection;

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

    public void testFindIllegalIds() {
        long[] illegalIds = {-1, -9001, Long.MIN_VALUE};

        for (long id : illegalIds) {
            try {
                mChatRepository.findById(id);
            } catch (IllegalArgumentException e) {
                // good
                assertEquals("Chat ID is invalid.", e.getMessage());
            } catch (NoSuchModelException e) {
                fail("NoSuchModelException should not be thrown.");
            }
        }
    }

    public void testFindLegalIds() {
        long[] legalIds = {0, 1, 42, 9001, Long.MAX_VALUE};

        for (long id : legalIds) {
            try {
                mChatRepository.findById(id);
            } catch (IllegalArgumentException e) {
                fail("IllegalArgumentException should not be thrown.");
            } catch (NoSuchModelException e) {
                e.printStackTrace();
            }
        }
    }
}
