package ch.tarsier.tarsier.test;

import android.test.AndroidTestCase;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.exception.DeleteException;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.exception.UpdateException;

/**
 * @author gluthier
 */
public class MessageRepositoryTest extends AndroidTestCase {

    private MessageRepository mMessageRepository;
    private Message mDummyMessage;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Tarsier.app().reset();
        mMessageRepository = Tarsier.app().getMessageRepository();
        mDummyMessage = new Message(1, "test", 2, 1000);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();


    }


    // test public Message findById(long id) alone
    public void testFindIllegalIds() {
        long[] illegalIds = {0, -1, -9001, Long.MIN_VALUE};

        for (long id : illegalIds) {
            try {
                mMessageRepository.findById(id);
                fail("Expecting IllegalArgumentException but none was thrown.");
            } catch (IllegalArgumentException e) {
                // good
                assertEquals("Message ID cannot be < 1", e.getMessage());
            }catch (NoSuchModelException e) {
                fail("Expecting IllegalArgumentException to be thrown first.");
            }
        }
    }

    public void testFindLegalIds() {
        long[] legalIds = {1, 42, 9001, Long.MAX_VALUE};

        for (long id : legalIds) {
            try {
                mMessageRepository.findById(id);
            } catch (IllegalArgumentException e) {
                fail("IllegalArgumentException should not be thrown.");
            } catch (NoSuchModelException e) {
                // good
            }
        }
    }


    // test public void insert(Message message) alone
    public void testInsertNullMessage() {
        try {
            mMessageRepository.insert(null);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Message should not be null.", e.getMessage());
        } catch (InsertException e) {
            fail("Expecting InvalidModelException to be thrown first.");
        }
    }

    public void testInsertDummyMessage() {
        try {
            mMessageRepository.insert(mDummyMessage);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown.");
        } catch (InsertException e) {
            fail("InsertException should not be thrown.");
        }
    }


    // test public void update(Message message) alone
    public void testUpdateNullMessage() {
        try {
            mMessageRepository.update(null);
        } catch (InvalidModelException e) {
            // good
            assertEquals("Message should not be null.", e.getMessage());
        } catch (UpdateException e) {
            fail("Expecting InvalidModelException to be thrown first.");
        }
    }

    public void testUpdateNotExistingMessage() {
        try {
            mMessageRepository.update(mDummyMessage);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown.");
        } catch (UpdateException e) {
            // good
        }
    }


    // test public void delete(Message message) alone
    public void testDeleteNullMessage() {
        try {
            mMessageRepository.delete(null);
        } catch (InvalidModelException e) {
            // good
            assertEquals("Message should not be null.", e.getMessage());
        } catch (DeleteException e) {
            fail("Expecting InvalidModelException to be thrown first.");
        }
    }

    public void testDeleteMessageWithBadId() {
        try {
            mMessageRepository.delete(mDummyMessage);
        } catch (InvalidModelException e) {
            // good
            assertEquals("Message ID is invalid", e.getMessage());
        } catch (DeleteException e) {
            fail("Expecting InvalidModelException to be thrown first.");
        }
    }

    // test methods together
    public void testInsertAndFindIdOfTheMessageInserted() {
        insertDummyMessage();

        Message dummyMessageFromDb = null;
        try {
            dummyMessageFromDb = mMessageRepository.findById(mDummyMessage.getId());
        } catch (NoSuchModelException e) {
            fail("NoSuchModelException should not be thrown");
        }

        assertNotNull(dummyMessageFromDb);

        assertEquals(1, dummyMessageFromDb.getChatId());
        assertEquals("test", dummyMessageFromDb.getText());
        assertEquals(2, dummyMessageFromDb.getPeerId());
        assertEquals(1000, dummyMessageFromDb.getDateTime());
    }

    public void testInsertAndUpdateDummyMessage() {
        insertDummyMessage();

        mDummyMessage.setChatId(10);
        mDummyMessage.setText("this is new");
        mDummyMessage.setPeerId(20);
        mDummyMessage.setDateTime(2000);

        try {
            mMessageRepository.update(mDummyMessage);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown.");
        } catch (UpdateException e) {
            fail("UpdateException should not be thrown.");
        }

        assertNotSame(-1, mDummyMessage.getId());

        assertEquals(10, mDummyMessage.getChatId());
        assertEquals("this is new", mDummyMessage.getText());
        assertEquals(20, mDummyMessage.getPeerId());
        assertEquals(2000, mDummyMessage.getDateTime());

        Message dummyMessageFromDb = null;
        try {
            dummyMessageFromDb = mMessageRepository.findById(mDummyMessage.getId());
        } catch (NoSuchModelException e) {
            fail("NoSuchModelException should not be thrown");
        }

        assertNotNull(dummyMessageFromDb);

        assertEquals(10, dummyMessageFromDb.getChatId());
        assertEquals("this is new", dummyMessageFromDb.getText());
        assertEquals(20, dummyMessageFromDb.getPeerId());
        assertEquals(2000, dummyMessageFromDb.getDateTime());
    }

    public void testInsertAndDeleteDummyMessage() {
        insertDummyMessage();

        try {
            mMessageRepository.delete(mDummyMessage);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown.");
        } catch (DeleteException e) {
            fail("DeleteException should not be thrown.");
        }

        assertEquals(-1, mDummyMessage.getId());
    }


    private void insertDummyMessage() {
        // makes sure that mDummyMessage is "clean"
        mDummyMessage = new Message(1, "test", 2, 1000);

        try {
            mMessageRepository.insert(mDummyMessage);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown.");
        } catch (InsertException e) {
            fail("InsertException should not be thrown.");
        }

        assertNotSame(-1, mDummyMessage.getId());
    }
}
