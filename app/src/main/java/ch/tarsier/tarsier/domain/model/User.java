package ch.tarsier.tarsier.domain.model;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.storage.StorageAccess;

/**
 * @author Romain Ruetschi
 */
public final class User extends Peer {

    private static User instance = null;

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }

        return instance;
    }

    private User() {
        StorageAccess storage = Tarsier.app().getStorage();

        this.setName(storage.getMyUsername());
        this.setStatusMessage(storage.getMyMood());
        this.setPicturePath(storage.getMyPicturePath());
        this.setOnline(true);
    }

}
