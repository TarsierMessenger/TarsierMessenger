package ch.tarsier.tarsier.storage;

import android.graphics.Bitmap;

/**
 * @author xawill
 */
public class Peer {
    private int mPeerId;
    private String mName;
    private Bitmap mPicture;

    public Peer(String name, Bitmap picture) {
        //TODO uniquely generate mPeerID
        this.mName = name;
        this.mPicture = picture;
    }

    public String getName() {
        return mName;
    }

    public int getPeerId() {
        return mPeerId;
    }

    public Bitmap getPicture() {
        return mPicture;
    }
}
