package ch.tarsier.tarsier.domain.model;

/**
 * @author romac
 */
public final class User extends Peer {

    @Override
    public boolean isUser() {
        return true;
    }

}
