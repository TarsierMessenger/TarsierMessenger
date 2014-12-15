package ch.tarsier.tarsier.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import ch.tarsier.tarsier.Tarsier;

/**
 * AbstractPreferences is the abstract class that represent the preferences.
 *
 * @author romac
 */
public abstract class AbstractPreferences {

    private Tarsier mApp;
    private SharedPreferences mShared;

    protected abstract String getPreferencesFile();

    public AbstractPreferences() {
        mApp = Tarsier.app();
        mShared = mApp.getSharedPreferences(getPreferencesFile(), Context.MODE_PRIVATE);
    }

    protected String getString(int key) {
        String keyString = mApp.getString(key);
        return mShared.getString(keyString, "");
    }

    protected String getString(int key, String defaultValue) {
        return mShared.getString(Tarsier.app().getString(key), defaultValue);
    }

    protected void setString(int key, String data) {
        SharedPreferences.Editor editor = mShared.edit();
        editor.putString(mApp.getString(key), data);
        editor.apply();
    }

    protected boolean getBoolean(int key, boolean defaultValue) {
        return mShared.getBoolean(mApp.getString(key), defaultValue);
    }

    protected void setBoolean(int key, boolean value) {
        SharedPreferences.Editor editor = mShared.edit();
        editor.putBoolean(mApp.getString(key), value);
        editor.apply();
    }
}
