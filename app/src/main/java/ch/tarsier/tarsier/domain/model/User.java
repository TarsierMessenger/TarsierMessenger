package ch.tarsier.tarsier.domain.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ch.tarsier.tarsier.Tarsier;

/**
 * @author romac
 */
public final class User extends Peer {

    @Override
    public boolean isUser() {
        return true;
    }

    @Override
    public Bitmap getPicture() {
        return BitmapFactory.decodeFile(Tarsier.app().getUserPreferences().getPicturePath());
    }
}
