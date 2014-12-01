package ch.tarsier.tarsier.domain.model;

import ch.tarsier.tarsier.Tarsier;

/**
 * @author romac
 */
public final class User extends Peer {

    @Override
    public boolean isUser() {
        return true;
    }

}
