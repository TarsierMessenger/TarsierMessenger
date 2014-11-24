package ch.tarsier.tarsier.test.repository;

import android.test.AndroidTestCase;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.repository.PeerRepository;

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
        mDummyPeer = new Peer();
    }
}
