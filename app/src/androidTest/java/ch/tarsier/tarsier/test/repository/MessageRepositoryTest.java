package ch.tarsier.tarsier.test.repository;

import android.test.AndroidTestCase;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.exception.DeleteException;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.exception.UpdateException;

/**
 * MessageRepositoryTest tests the MessageRepository.
 *
 * @see ch.tarsier.tarsier.domain.repository.MessageRepository
 * @author gluthier
 */
public class MessageRepositoryTest extends AndroidTestCase {

    private static final long FIRST_DECEMBER_2014_MID_DAY = 1417392000;
    private static final long NINE_THOUSAND = 9000;
    private static final long RANDOM_ID = 10;

    private MessageRepository mMessageRepository;
    private ChatRepository mChatRepository;
    private PeerRepository mPeerRepository;
    private Message mDummyMessage;
    private Chat mDummyChat;
    private Peer mDummyPeer;
    private byte[] mUserPublicKey;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mMessageRepository = Tarsier.app().getMessageRepository();
        mChatRepository = Tarsier.app().getChatRepository();
        mPeerRepository = Tarsier.app().getPeerRepository();

        mUserPublicKey = Tarsier.app().getUserPreferences().getKeyPair().getPublicKey();
        mDummyMessage = new Message(1, "test", mUserPublicKey, 1);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        deleteDummyMessage();
    }


    // test findById(long id) alone
    public void testFindIllegalIds() {
        long[] illegalIds = {-1, -NINE_THOUSAND, Long.MIN_VALUE};

        for (long id : illegalIds) {
            try {
                mMessageRepository.findById(id);
                fail("Expecting IllegalArgumentException but none was thrown.");
            } catch (IllegalArgumentException e) {
                // good
                assertEquals("Message ID is invalid.", e.getMessage());
            } catch (NoSuchModelException e) {
                fail("Expecting IllegalArgumentException to be thrown first: " + e.getMessage());
            }
        }
    }

    public void testFindLegalIds() {
        long[] legalIds = {0, 1, NINE_THOUSAND, Long.MAX_VALUE};

        for (long id : legalIds) {
            try {
                mMessageRepository.findById(id);
                // id 0, 1 may already be filled by previous tests
            } catch (IllegalArgumentException e) {
                fail("IllegalArgumentException should not be thrown: " + e.getMessage());
            } catch (NoSuchModelException e) {
                // good
            }
        }
    }

    // test insert(Message message) alone
    public void testInsertNullMessage() {
        try {
            mMessageRepository.insert(null);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Message is null.", e.getMessage());
        } catch (InsertException e) {
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }

    public void testInsertDummyMessage() {
        try {
            mMessageRepository.insert(mDummyMessage);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (InsertException e) {
            fail("InsertException should not be thrown: " + e.getMessage());
        }
    }

    // test update(Message message) alone
    public void testUpdateNullMessage() {
        try {
            mMessageRepository.update(null);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Message is null.", e.getMessage());
        } catch (UpdateException e) {
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }

    public void testUpdateNotExistingMessage() {
        try {
            mMessageRepository.update(mDummyMessage);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Message ID is invalid.", e.getMessage());
        } catch (UpdateException e) {
            fail("UpdateException should not be thrown: " + e.getMessage());
        }
    }

    // test delete(Message message) alone
    public void testDeleteNullMessage() {
        try {
            mMessageRepository.delete(null);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Message is null.", e.getMessage());
        } catch (DeleteException e) {
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }

    public void testDeleteMessageWithBadId() {
        try {
            mMessageRepository.delete(mDummyMessage);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Message ID is invalid.", e.getMessage());
        } catch (DeleteException e) {
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }

    // test findAll() alone
    public void testFindAllWithNoMessages() {
        try {
            mMessageRepository.findAll();
        } catch (NoSuchModelException e) {
            fail("NoSuchModelException should not be thrown: " + e.getMessage());
        }
    }

    // test getLastMessageOf(Chat chat) alone
    public void testGetLastMessageOfNullChat() {
        try {
            mMessageRepository.getLastMessageOf(null);
        } catch (NoSuchModelException e) {
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        } catch (InvalidModelException e) {
            // good
            assertEquals("Chat is null.", e.getMessage());
        }
    }

    // test methods together
    public void testInsertAndFindIdOfTheMessageInserted() {
        insertDummyMessage();

        Message dummyMessageFromDb = null;
        try {
            dummyMessageFromDb = mMessageRepository.findById(mDummyMessage.getId());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should ne be thrown: " + e.getMessage());
        } catch (NoSuchModelException e) {
            fail("NoSuchModelException should not be thrown: " + e.getMessage());
        }

        assertNotNull(dummyMessageFromDb);

        assertEquals(mDummyMessage.getChatId(), dummyMessageFromDb.getChatId());
        assertEquals(mDummyMessage.getText(), dummyMessageFromDb.getText());
        assertEquals(mDummyMessage.getDateTime(), dummyMessageFromDb.getDateTime());
    }

    public void testInsertAndUpdateDummyMessage() {
        insertDummyMessage();

        mDummyMessage.setChatId(RANDOM_ID);
        mDummyMessage.setText("this is new");
        mDummyMessage.setDateTime(NINE_THOUSAND);

        try {
            mMessageRepository.update(mDummyMessage);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (UpdateException e) {
            fail("UpdateException should not be thrown: " + e.getMessage());
        }

        assertNotSame(-1, mDummyMessage.getId());

        assertEquals(RANDOM_ID, mDummyMessage.getChatId());
        assertEquals("this is new", mDummyMessage.getText());
        assertEquals(NINE_THOUSAND, mDummyMessage.getDateTime());

        Message dummyMessageFromDb = null;
        try {
            dummyMessageFromDb = mMessageRepository.findById(mDummyMessage.getId());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should ne be thrown: " + e.getMessage());
        } catch (NoSuchModelException e) {
            fail("NoSuchModelException should not be thrown: " + e.getMessage());
        }

        assertNotNull(dummyMessageFromDb);

        assertEquals(mDummyMessage.getChatId(), dummyMessageFromDb.getChatId());
        assertEquals(mDummyMessage.getText(), dummyMessageFromDb.getText());
        assertEquals(mDummyMessage.getDateTime(), dummyMessageFromDb.getDateTime());
    }

    public void testInsertAndDeleteDummyMessage() {
        insertDummyMessage();

        try {
            mMessageRepository.delete(mDummyMessage);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (DeleteException e) {
            fail("DeleteException should not be thrown: " + e.getMessage());
        }

        assertEquals(-1, mDummyMessage.getId());
    }

    public void testInsertAndGetLastMessageOfDummyChat() {
        insertDummyMessage();

        Message messageFromDb = null;
        try {
            messageFromDb = mMessageRepository.getLastMessageOf(mDummyChat);
        } catch (NoSuchModelException e) {
            fail("NoSuchModelException should not be thrown: " + e.getMessage());
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        }

        assertNotNull(messageFromDb);
        assertEquals(mDummyMessage.getChatId(), messageFromDb.getChatId());
        assertEquals(mDummyMessage.getDateTime(), messageFromDb.getDateTime());
        assertEquals(mDummyMessage.getId(), messageFromDb.getId());
        assertEquals(mDummyMessage.getText(), messageFromDb.getText());
    }


    private void insertDummyMessage() {
        mDummyPeer = new Peer("Quentin");
        mDummyPeer.setStatusMessage("au max");
        mDummyPeer.setPublicKey(new PublicKey(new byte[]{1, 1}));


        try {
            mPeerRepository.insert(mDummyPeer);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (InsertException e) {
            fail("InsertException should not be thrown: " + e.getMessage());
        }

        mDummyChat = new Chat();
        mDummyChat.setHost(mDummyPeer);

        try {
            mChatRepository.insert(mDummyChat);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (InsertException e) {
            fail("InsertException should not be thrown: " + e.getMessage());
        }

        assertNotSame(-1, mDummyChat.getId());

        // makes sure that mDummyMessage is "clean"
        mDummyMessage = new Message(mDummyChat.getId(), "nice message", mUserPublicKey, FIRST_DECEMBER_2014_MID_DAY);

        try {
            mMessageRepository.insert(mDummyMessage);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (InsertException e) {
            fail("InsertException should not be thrown: " + e.getMessage());
        }

        assertNotSame(-1, mDummyMessage.getId());
    }

    private void deleteDummyMessage() {
        try {
            if (mDummyChat != null) {
                mChatRepository.delete(mDummyChat);
            }
            if (mDummyMessage != null) {
                mMessageRepository.delete(mDummyMessage);
            }
            if (mDummyPeer != null) {
                mPeerRepository.delete(mDummyPeer);
            }
        } catch (InvalidModelException | DeleteException e) {
            e.printStackTrace();
        }
    }
}
