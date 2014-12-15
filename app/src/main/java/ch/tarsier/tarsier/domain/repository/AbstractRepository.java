package ch.tarsier.tarsier.domain.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.exception.InvalidCursorException;

/**
 * A repository takes care of fetching, inserting, updating and deleting
 * data from the database, for a specific model.
 *
 * Methods for retrieving data are usually prefixed with `find`.
 *
 * @author romac
 * @param <T> The model that this repository manipulates.
 */
abstract class AbstractRepository<T> {

    private Database mDatabase;

    public AbstractRepository(Database database) {
        mDatabase = database;
    }

    public SQLiteDatabase getReadableDatabase() {
        return mDatabase.getReadable();
    }

    public SQLiteDatabase getWritableDatabase() {
        return mDatabase.getWritable();
    }

    abstract protected T buildFromCursor(Cursor c) throws InvalidCursorException;

    protected List<T> buildListFromCursor(Cursor c) throws InvalidCursorException {
        List<T> result = new ArrayList<T>();

        if (c.isBeforeFirst()) {
            c.moveToFirst();
        }

        while (!c.isAfterLast()) {
            T elem = buildFromCursor(c);

            if (elem != null) {
                result.add(buildFromCursor(c));
            }

            c.moveToNext();
        }

        return result;
    }
}
