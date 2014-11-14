package ch.tarsier.tarsier.domain.model;

import android.net.Uri;

import ch.tarsier.tarsier.R;

/**
 * @author Romain Ruetschi
 */
public class User {

    private String mName;
    private String mStatusMessage;
    private Uri mPictureUri;
    private boolean mOnline;

    public User(String name, String statusMessage /*, Uri pictureUri, boolean online */) {
        String path = "android.resource://ch.tarsier.tarsier/" + R.drawable.ic_launcher;
        boolean online = Math.random() < 0.5;

        this.mName = name;
        this.mStatusMessage = statusMessage;
        this.mPictureUri = Uri.parse(path);
        this.mOnline = online;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getStatusMessage() {
        return mStatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.mStatusMessage = statusMessage;
    }

    public void setOnline(boolean online) {
        mOnline = online;
    }

    public boolean isOnline() {
        return mOnline;
    }

    public Uri getPictureUri() {
        return mPictureUri;
    }

    public void setPictureUri(Uri pictureUri) {
        mPictureUri = pictureUri;
    }
}
