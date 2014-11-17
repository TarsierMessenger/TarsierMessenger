package ch.tarsier.tarsier.exception;

import android.database.CursorIndexOutOfBoundsException;

/**
 * @author gluthier
 */
public class InvalidCursorException extends Exception {

    public InvalidCursorException(String detailMessage) {
        super(detailMessage);
    }

    public InvalidCursorException(Exception e) {
        super(e);
    }
}
