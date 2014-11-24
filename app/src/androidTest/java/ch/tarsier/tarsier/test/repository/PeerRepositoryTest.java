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

        Tarsier.app().reset();
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
                fail("Expecting IllegalArgumentException to be thrown first.");
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
                fail("IllegalArgumentException should not be thrown.");
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
            fail("Expecting InvalidModelException to be thrown first.");
        }
    }

    public void testInsertDummyPeer() {
        try {
            mPeerRepository.insert(mDummyPeer);
        } catch (InvalidModelException e) {
            fail("InvalidModelException should not be thrown.");
        } catch (InsertException e) {
            fail("InsertException should not be thrown.");
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
            fail("Expecting InvalidModelException to be thrown first.");
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
            fail("UpdateException should not be thrown.");
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
            fail("Expecting InvalidModelException to be thrown first.");
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
            fail("Expecting InvalidModelException to be thrown first.");
        }
    }


    // test methods together
}
