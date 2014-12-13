package ch.tarsier.tarsier.test.repository;

import android.test.AndroidTestCase;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.exception.DeleteException;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.exception.UpdateException;

/**
 * @author gluthier
 */
public class MessageRepositoryTest extends AndroidTestCase {

    private final static byte[] USER_PUBLIC_KEY = new byte[] {1, 3, 3, 7};

    private MessageRepository mMessageRepository;
    private Message mDummyMessage;
    private byte[] mUserPublicKey;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Tarsier.app().setUserPreferences(null);
        mMessageRepository = Tarsier.app().getMessageRepository();
        mUserPublicKey = Tarsier.app().getUserPreferences().getKeyPair().getPublicKey();
        mDummyMessage = new Message(1, "test", mUserPublicKey, 1000);
    }


    // test findById(long id) alone
    public void testFindIllegalIds() {
        long[] illegalIds = {-1, -9001, Long.MIN_VALUE};

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
        long[] legalIds = {0, 1, 9001, Long.MAX_VALUE};

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

        assertEquals(1, dummyMessageFromDb.getChatId());
        assertEquals("test", dummyMessageFromDb.getText());
        assertEquals(1000, dummyMessageFromDb.getDateTime());
    }

    public void testInsertAndUpdateDummyMessage() {
        insertDummyMessage();

        mDummyMessage.setChatId(10);
        mDummyMessage.setText("this is new");
        mDummyMessage.setDateTime(2000);

        try {
            mMessageRepository.update(mDummyMessage);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (UpdateException e) {
            fail("UpdateException should not be thrown: " + e.getMessage());
        }

        assertNotSame(-1, mDummyMessage.getId());

        assertEquals(10, mDummyMessage.getChatId());
        assertEquals("this is new", mDummyMessage.getText());
        assertEquals(2000, mDummyMessage.getDateTime());

        Message dummyMessageFromDb = null;
        try {
            dummyMessageFromDb = mMessageRepository.findById(mDummyMessage.getId());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should ne be thrown: " + e.getMessage());
        } catch (NoSuchModelException e) {
            fail("NoSuchModelException should not be thrown: " + e.getMessage());
        }

        assertNotNull(dummyMessageFromDb);

        assertEquals(10, dummyMessageFromDb.getChatId());
        assertEquals("this is new", dummyMessageFromDb.getText());
        assertEquals(2000, dummyMessageFromDb.getDateTime());
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


    private void insertDummyMessage() {
        // makes sure that mDummyMessage is "clean"
        mDummyMessage = new Message(1, "test", mUserPublicKey, 1000);

        try {
            mMessageRepository.insert(mDummyMessage);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (InsertException e) {
            fail("InsertException should not be thrown: " + e.getMessage());
        }

        assertNotSame(-1, mDummyMessage.getId());
    }
}
