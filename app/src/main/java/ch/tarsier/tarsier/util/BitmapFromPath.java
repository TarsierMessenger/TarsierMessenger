package ch.tarsier.tarsier.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ch.tarsier.tarsier.R;

/**
 * BitmapFromPath is the class that provides a methods to get a Bitmap from a path given.
 *
 * @author benpac on 26/11/14.
 */
public class BitmapFromPath {

    public static Bitmap getBitmapFromPath(Context context, String filePath) {
        if (context == null) {
            throw new IllegalArgumentException("Context is null.");
        }

        if (filePath == null || filePath.equals("")) {
            throw new IllegalArgumentException("filepath is null or empty.");
        }

        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.add_picture);
        }

        return bitmap;
    }

}
