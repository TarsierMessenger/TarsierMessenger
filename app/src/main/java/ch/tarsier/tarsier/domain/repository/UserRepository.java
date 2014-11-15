package ch.tarsier.tarsier.domain.repository;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.User;
import ch.tarsier.tarsier.prefs.UserPreferences;
import ch.tarsier.tarsier.storage.StorageAccess;

/**
 * @author romac
 */
public class UserRepository {

    private final UserPreferences mUserPreferences;

    private User mUser;

    public UserRepository() {
        mUserPreferences = Tarsier.app().getUserPreferences();
    }

    public User getUser() {
        if (mUser == null) {
            mUser = new User();

            mUser.setName(mUserPreferences.getUsername());
            mUser.setStatusMessage(mUserPreferences.getStatusMessage());
            mUser.setPicturePath(mUserPreferences.getPicturePath());
            mUser.setOnline(true);
        }

        return mUser;
    }

}
