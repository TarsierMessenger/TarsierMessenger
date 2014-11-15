package ch.tarsier.tarsier;

import android.app.Application;

import ch.tarsier.tarsier.prefs.UserPreferences;
import ch.tarsier.tarsier.storage.StorageAccess;

/**
 * @author Romain Ruetschi
 */
public class Tarsier extends Application {

    private static Tarsier app;

    private StorageAccess mStorage;
    private UserPreferences mUserPreferences;

    public static Tarsier app() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public StorageAccess getStorage() {
        if (mStorage == null) {
            mStorage = new StorageAccess(getApplicationContext());
        }

        return mStorage;
    }

    public UserPreferences getUserPreferences() {
        if (mUserPreferences == null) {
            mUserPreferences = new UserPreferences();
        }

        return mUserPreferences;
    }

}
