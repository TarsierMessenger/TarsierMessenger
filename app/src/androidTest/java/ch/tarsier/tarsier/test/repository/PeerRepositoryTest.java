package ch.tarsier.tarsier.test.repository;

import android.test.AndroidTestCase;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.exception.DeleteException;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.exception.UpdateException;

/**
 * @author gluthier
 */
public class PeerRepositoryTest extends AndroidTestCase {

    private PeerRepository mPeerRepository;
    private Peer mDummyPeer;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Tarsier.app().reset();
        mPeerRepository = Tarsier.app().getPeerRepository();

        mDummyPeer = new Peer("Alfred");
        mDummyPeer.setStatusMessage("posey");
        mDummyPeer.setPublicKey(new PublicKey(new byte[]{0, 1}));
    }


    // test findById(long id) alone
    public void testFindIllegalIds() {
        long[] illegalIds = {-1, -9001, Long.MIN_VALUE};

        for (long id : illegalIds) {
            try {
                mPeerRepository.findById(id);
                fail("Expecting IllegalArgumentException to be thrown.");
            } catch (IllegalArgumentException e) {
                // good
                assertEquals("Peer ID is invalid.", e.getMessage());
            } catch (NoSuchModelException e) {
                fail("Expecting IllegalArgumentException to be thrown first: " + e.getMessage());
            }
        }
    }

    public void testFindLegalIds() {
        long[] legalIds = {0, 1, 9001, Long.MAX_VALUE};

        for (long id : legalIds) {
            try {
                mPeerRepository.findById(id);
                // id 0, 1 may already be filled by previous tests
            } catch (IllegalArgumentException e) {
                fail("IllegalArgumentException should not be thrown: " + e.getMessage());
            } catch (NoSuchModelException e) {
                // good
            }
        }
    }


    // test insert(Peer peer) alone
    public void testInsertNullPeer() {
        try {
            mPeerRepository.insert(null);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Peer is null.", e.getMessage());
        } catch (InsertException e) {
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }

    public void testInsertDummyPeer() {
        try {
            mPeerRepository.insert(mDummyPeer);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (InsertException e) {
            fail("InsertException should not be thrown: " + e.getMessage());
        }
    }


    // test update(Peer peer) alone
    public void testUpdateNullPeer() {
        try {
            mPeerRepository.update(null);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Peer is null.", e.getMessage());
        } catch (UpdateException e) {
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }

    public void testUpdateNotExistingPeer() {
        try {
            mPeerRepository.update(mDummyPeer);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Peer ID is invalid.", e.getMessage());
        } catch (UpdateException e) {
            fail("UpdateException should not be thrown: " + e.getMessage());
        }
    }


    // test delete(Peer peer) alone
    public void testDeleteNullPeer() {
        try {
            mPeerRepository.delete(null);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Peer is null.", e.getMessage());
        } catch (DeleteException e) {
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }

    public void testDeletePeerWithBadId() {
        try {
            mPeerRepository.delete(mDummyPeer);
            fail("Expecting InvalidModelException to be thrown.");
        } catch (InvalidModelException e) {
            // good
            assertEquals("Peer ID is invalid.", e.getMessage());
        } catch (DeleteException e) {
            fail("Expecting InvalidModelException to be thrown first: " + e.getMessage());
        }
    }


    // test methods together
    public void testInsertAndFindIdOfThePeerInserted() {
        insertDummyPeer();

        Peer dummyPeerFromDb = null;
        try {
            dummyPeerFromDb = mPeerRepository.findById(mDummyPeer.getId());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should ne be thrown: " + e.getMessage());
        } catch (NoSuchModelException e) {
            fail("NoSuchModelException should not be thrown: " + e.getMessage());
        }

        PublicKey key = new PublicKey(new byte[]{0, 1});

        assertNotNull(dummyPeerFromDb);

        assertEquals("Alfred", dummyPeerFromDb.getUserName());
        assertEquals("posey", dummyPeerFromDb.getStatusMessage());
        assertEquals(key, dummyPeerFromDb.getPublicKey());
    }

    public void testInsertAndUpdateDummyPeer() {
        insertDummyPeer();

        PublicKey newKey = new PublicKey(new byte[]{1, 0});

        mDummyPeer.setUserName("Claude");
        mDummyPeer.setStatusMessage("happy");
        mDummyPeer.setPublicKey(newKey);

        try {
            mPeerRepository.update(mDummyPeer);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (UpdateException e) {
            fail("UpdateException should not be thrown: " + e.getMessage());
        }

        assertNotSame(-1, mDummyPeer.getId());

        assertEquals("Claude", mDummyPeer.getUserName());
        assertEquals("happy", mDummyPeer.getStatusMessage());
        assertEquals(newKey, mDummyPeer.getPublicKey());

        Peer dummyPeerFromDb = null;
        try {
            dummyPeerFromDb = mPeerRepository.findById(mDummyPeer.getId());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should ne be thrown: " + e.getMessage());
        } catch (NoSuchModelException e) {
            fail("NoSuchModelException should not be thrown: " + e.getMessage());
        }

        assertNotNull(dummyPeerFromDb);

        assertEquals("Claude", dummyPeerFromDb.getUserName());
        assertEquals("happy", dummyPeerFromDb.getStatusMessage());
        assertEquals(newKey, dummyPeerFromDb.getPublicKey());
    }

    public void testInsertAndDeleteDummyPeer() {
        insertDummyPeer();

        try {
            mPeerRepository.delete(mDummyPeer);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (DeleteException e) {
            fail("DeleteException should not be thrown: " + e.getMessage());
        }

        assertEquals(-1, mDummyPeer.getId());
    }


    private void insertDummyPeer() {
        // makes sure thet mDummyPeer is "clean"
        mDummyPeer = new Peer("Alfred");
        mDummyPeer.setStatusMessage("posey");
        mDummyPeer.setPublicKey(new PublicKey(new byte[]{0, 1}));

        try {
            mPeerRepository.insert(mDummyPeer);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown: " + e.getMessage());
        } catch (InsertException e) {
            fail("InsertException should not be thrown: " + e.getMessage());
        }

        assertNotSame(-1, mDummyPeer.getId());
    }
}
