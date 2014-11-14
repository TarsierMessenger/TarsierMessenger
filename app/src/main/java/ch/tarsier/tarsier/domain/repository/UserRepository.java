package ch.tarsier.tarsier.domain.repository;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.User;
import ch.tarsier.tarsier.storage.StorageAccess;

/**
 * @author romac
 */
public class UserRepository {

    private final StorageAccess mStorage;

    private User mUser;

    public UserRepository() {
        mStorage = Tarsier.app().getStorage();
    }

    public User getUser() {
        if (mUser == null) {
            mUser = new User();

            mUser.setName(mStorage.getMyUsername());
            mUser.setStatusMessage(mStorage.getMyMood());
            mUser.setPicturePath(mStorage.getMyPicturePath());
            mUser.setOnline(true);
        }

        return mUser;
    }

}
