package ch.tarsier.tarsier.domain.repository;

import android.database.sqlite.SQLiteDatabase;

import ch.tarsier.tarsier.database.Database;

/**
 * @author romac
 */
abstract class AbstractRepository {

    private Database mDatabase;

    public AbstractRepository(Database database) {
        mDatabase = database;
    }

    public SQLiteDatabase getReadableDatabase() {
        while (!mDatabase.isReady()) { }

        return mDatabase.getReadable();
    }

    public SQLiteDatabase getWritableDatabase() {
        while (!mDatabase.isReady()) { }

        return mDatabase.getWritable();
    }
}
