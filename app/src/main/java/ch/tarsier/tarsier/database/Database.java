package ch.tarsier.tarsier.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Database is the class containing the SQLite database.
 *
 * @author McMoudi
 * @author romac
 */
public class Database {

    private volatile SQLiteDatabase mReadable;
    private volatile SQLiteDatabase mWritable;

    private volatile boolean mIsReady = false;

    public Database(Context context) {
        this(new DatabaseHelper(context));
    }

    public Database(DatabaseHelper databaseHelper) {
        mReadable = databaseHelper.getReadableDatabase();
        mWritable = databaseHelper.getWritableDatabase();

        mIsReady = true;
    }

    public SQLiteDatabase getReadable() {
        return mReadable;
    }

    public SQLiteDatabase getWritable() {
        return mWritable;
    }

    public boolean isReady() {
        return mIsReady;
    }
}
