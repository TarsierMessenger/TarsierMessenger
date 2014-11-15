package ch.tarsier.tarsier.domain.repository;

import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;

/**
 * @author romac
 */
public class PeerRepository extends AbstractRepository {

    public PeerRepository(Database database) {
        super(database);
    }

    public Peer findById(long id) {
        // TODO: Implement Peer.findById()
        return null;
    }

    public Peer findByPublicKey(PublicKey publicKey) {
        // TODO: Implement Peer.findById()
        return null;
    }

    public void insert(Peer peer) {
        // TODO: Implement Peer.insert()
    }

    public void update(Peer peer) {
        // TODO: Implement Peer.update()
    }

    public void delete(Peer peer) {
        // TODO: Implement Peer.delete()
    }

}
