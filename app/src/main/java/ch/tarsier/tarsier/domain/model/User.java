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

    @Override
    public String getPicturePath() {
        if (super.getPicturePath() == null) {
            setPicturePath(Tarsier.app().getUserPreferences().getPicturePath());
        }

        return super.getPicturePath();
    }

}
