package ch.tarsier.tarsier.test;

import android.test.AndroidTestCase;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.exception.DeleteException;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.exception.UpdateException;
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


    // test findById(long id) alone
    public void testFindIllegalIds() {
        long[] illegalIds = {-1, -9001, Long.MIN_VALUE};

        for (long id : illegalIds) {
            try {
                mChatRepository.findById(id);
                fail("Expecting IllegalArgumentException to be thrown.");
            } catch (IllegalArgumentException e) {
                // good
                assertEquals("Chat ID is invalid.", e.getMessage());
            } catch (NoSuchModelException e) {
                fail("NoSuchModelException should not be thrown.");
            }
        }
    }

    public void testFindLegalIds() {
        long[] legalIds = {0, 1, 9001, Long.MAX_VALUE};

        for (long id : legalIds) {
            try {
                mChatRepository.findById(id);
                // id 0, 1 may already be filled by previous tests
            } catch (IllegalArgumentException e) {
                fail("IllegalArgumentException should not be thrown.");
            } catch (NoSuchModelException e) {
                // good
            }
        }
    }


    // test insert(Chat chat) alone
    public void testInsertNullChat() {
        try {
            mChatRepository.insert(null);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Chat is null.", e.getMessage());
        } catch (InsertException e) {
            fail("Expecting InvalidModelException to be thrown first.");
        }
    }

    public void testInsertDummyPrivateChat() {
        try {
            mChatRepository.insert(mDummyPrivateChat);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown.");
        } catch (InsertException e) {
            fail("InsertException should not be thrown.");
        }
    }

    public void testInsertDummyPublicChat() {
        try {
            mChatRepository.insert(mDummyPublicChat);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown.");
        } catch (InsertException e) {
            fail("InsertException should not be thrown.");
        }
    }


    // test update(Chat chat) alone
    public void testUpdateNullChat() {
        try {
            mChatRepository.update(null);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Chat is null.", e.getMessage());
        } catch (UpdateException e) {
            fail("Expecting InvalidModelException to be thrown first.");
        }
    }

    public void testUpdateNotExistingPrivateChat() {
        try {
            mChatRepository.update(mDummyPrivateChat);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Chat ID is invalid.", e.getMessage());
        } catch (UpdateException e) {
            fail("Expecting InvalidModelException to be thorwn first.");
        }
    }

    public void testUpdateNotExistingPublicChat() {
        try {
            mChatRepository.update(mDummyPublicChat);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Chat ID is invalid.", e.getMessage());
        } catch (UpdateException e) {
            fail("Expecting InvalidModelException to be thorwn first.");
        }
    }


    // test delete(Chat chat) alone
    public void testDeleteNullChat() {
        try {
            mChatRepository.delete(null);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Chat is null.", e.getMessage());
        } catch (DeleteException e) {
            fail("Expecting InvalidModelException to be thrown first.");
        }
    }

    public void testDeletePrivateChatWithBadId() {
        try {
            mChatRepository.delete(mDummyPrivateChat);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Chat ID is invalid.", e.getMessage());
        } catch (DeleteException e) {
            fail("Expecting InvalidModelException to be thrown first.");
        }
    }

    public void testDeletePublicChatWithBadId() {
        try {
            mChatRepository.delete(mDummyPublicChat);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Chat ID is invalid.", e.getMessage());
        } catch (DeleteException e) {
            fail("Expecting InvalidModelException to be thrown first.");
        }
    }

    // test methods together
}
