package ch.tarsier.tarsier;

import android.app.Application;

/**
 * @author xawill
 */
public class App extends Application {
    private static App instance;

    public static App getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
