package ch.tarsier.tarsier;

import android.app.Application;

/**
 * @author Romain Ruetschi
 */
public class Tarsier extends Application {

    private static Tarsier self;

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
    }

    public static Tarsier app() {
        return self;
    }
}
