package ch.tarsier.tarsier.test.repository;

import android.test.AndroidTestCase;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.exception.DeleteException;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.exception.UpdateException;

/**
 * @author gluthier
 */
public class ChatRepositoryTest extends AndroidTestCase {

    private ChatRepository mChatRepository;
    private PeerRepository mPeerRepository;
    private Chat mDummyChat;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Tarsier.app().reset();
        mChatRepository = Tarsier.app().getChatRepository();
        mPeerRepository = Tarsier.app().getPeerRepository();

        mDummyChat = new Chat();
        mDummyChat.setPrivate(false);
        mDummyChat.setHost(new Peer("Olivier"));
        mDummyChat.setTitle("Public chat title");
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
                fail("NoSuchModelException should not be thrown: " + e.getMessage());
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
                fail("IllegalArgumentException should not be thrown: " + e.getMessage());
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
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }

    public void testInsertDummyChat() {
        try {
            mChatRepository.insert(mDummyChat);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (InsertException e) {
            fail("InsertException should not be thrown: " + e.getMessage());
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
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }

    public void testUpdateNotExistingChat() {
        try {
            mChatRepository.update(mDummyChat);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Chat ID is invalid.", e.getMessage());
        } catch (UpdateException e) {
            fail("Expecting InvalidModelException to be thorwn first: " + e.getMessage());
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
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }

    public void testDeleteChatWithBadId() {
        try {
            mChatRepository.delete(mDummyChat);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Chat ID is invalid.", e.getMessage());
        } catch (DeleteException e) {
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }


    // test methods together
    public void testInsertAndFindIdOfTheChatInserted() {
        insertDummyChat();

        Chat dummyChatFormDb = null;
        try {
            dummyChatFormDb = mChatRepository.findById(mDummyChat.getId());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should ne be thrown: " + e.getMessage());
        } catch (NoSuchModelException e) {
            fail("NoSuchModelException should not be thrown: " + e.getMessage());
        }
        assertNotNull(dummyChatFormDb);

        assertEquals("Quentin", dummyChatFormDb.getHost().getUserName());
        assertEquals("Public chat title", dummyChatFormDb.getTitle());
        assertEquals(false, dummyChatFormDb.isPrivate());
    }

    public void testInsertAndUpdateDummyChat() {
        insertDummyChat();

        Peer newPeer = new Peer("Jean");
        newPeer.setStatusMessage("trankil");
        newPeer.setPublicKey(new PublicKey(new byte[]{0, 0}));

        try {
            mPeerRepository.insert(newPeer);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (InsertException e) {
            fail("InsertException should not be thrown: " + e.getMessage());
        }

        mDummyChat.setTitle("New title");
        mDummyChat.setHost(newPeer);

        try {
            mChatRepository.update(mDummyChat);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (UpdateException e) {
            fail("UpdateException should not be thrown: " + e.getMessage());
        }

        assertNotSame(-1, mDummyChat.getId());

        assertEquals("Jean", mDummyChat.getHost().getUserName());
        assertEquals("New title", mDummyChat.getTitle());

        Chat dummyChatFormDb = null;
        try {
            dummyChatFormDb = mChatRepository.findById(mDummyChat.getId());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should ne be thrown: " + e.getMessage());
        } catch (NoSuchModelException e) {
            fail("NoSuchModelException should not be thrown: " + e.getMessage());
        }
        assertNotNull(dummyChatFormDb);

        assertEquals("Jean", dummyChatFormDb.getHost().getUserName());
        assertEquals("New title", dummyChatFormDb.getTitle());
    }


    public void testInsertAndDeleteDummyMessage() {
        insertDummyChat();

        try {
            mChatRepository.delete(mDummyChat);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (DeleteException e) {
            fail("DeleteException should not be thrown: " + e.getMessage());
        }

        assertEquals(-1, mDummyChat.getId());
    }


    private void insertDummyChat() {
        // makes sure that mDummyChat is "clean"
        Peer host = new Peer("Quentin");
        host.setStatusMessage("au max");
        host.setPublicKey(new PublicKey(new byte[]{1, 1}));


        try {
            mPeerRepository.insert(host);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (InsertException e) {
            fail("InsertException should not be thrown: " + e.getMessage());
        }

        mDummyChat = new Chat();
        mDummyChat.setPrivate(false);
        mDummyChat.setHost(host);
        mDummyChat.setTitle("Public chat title");

        try {
            mChatRepository.insert(mDummyChat);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (InsertException e) {
            fail("InsertException should not be thrown: " + e.getMessage());
        }

        assertNotSame(-1, mDummyChat.getId());
    }
}
