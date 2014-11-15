package ch.tarsier.tarsier.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

/**
 * @author McMoudi
 * @author romac
 */
public class Database {

    private volatile DatabaseHelper mDatabaseHelper;
    private volatile SQLiteDatabase mReadable;
    private volatile SQLiteDatabase mWritable;

    private volatile boolean mIsReady = false;

    public Database(Context context) {
        this(new DatabaseHelper(context));
    }

    public Database(DatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;

        new AccessDatabase().execute();
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

    /**
     * @author McMoudi
     */
    private class AccessDatabase extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            mReadable = mDatabaseHelper.getReadableDatabase();
            mWritable = mDatabaseHelper.getWritableDatabase();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mIsReady = true;
        }
    }

}
