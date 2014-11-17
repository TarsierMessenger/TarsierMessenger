package ch.tarsier.tarsier.domain.model;

/**
 * @author romac
 */
public final class User extends Peer {

    public User(String name) {
        super(name);
    }

    @Override
    public boolean isUser() {
        return true;
    }

}
