package ch.tarsier.tarsier;

import android.app.Application;

import ch.tarsier.tarsier.storage.StorageAccess;

/**
 * @author Romain Ruetschi
 */
public class Tarsier extends Application {

    private static Tarsier app;

    public static Tarsier app() {
        return app;
    }

    private StorageAccess mStorage;

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
}
