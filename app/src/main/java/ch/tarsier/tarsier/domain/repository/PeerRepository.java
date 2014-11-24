package ch.tarsier.tarsier.domain.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.exception.DeleteException;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.exception.UpdateException;

/**
 * @author xawill
 */
public class PeerRepository extends AbstractRepository {

    private static final String TABLE_NAME = Columns.Peer.TABLE_NAME;

    public PeerRepository(Database database) {
        super(database);
    }

    public Peer findById(long id) throws IllegalArgumentException, NoSuchModelException {
        if (id < 0) {
            throw new IllegalArgumentException("Peer ID is invalid.");
        }

        String whereClause = Columns.Peer._ID + " = " + id;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null, null,
                "1"
        );

        try {
            return buildFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        } finally {
            cursor.close();
        }
    }

    public Peer findByPublicKey(PublicKey publicKey)
            throws IllegalArgumentException, NoSuchModelException {

        if (publicKey == null) {
            throw new IllegalArgumentException("PublicKey is null.");
        }

        String whereClause = Columns.Peer.COLUMN_NAME_PUBLIC_KEY + " = " + publicKey;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null, null,
                "1"
        );

        try {
            return buildFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        } finally {
            cursor.close();
        }
    }

    public void insert(Peer peer) throws InvalidModelException, InsertException {
        if (peer == null) {
            throw new InvalidModelException("Peer is null.");
        }

        ContentValues values = getPeerValues(peer);

        long rowId = getWritableDatabase().insert(
            TABLE_NAME,
            null,
            values
        );

        if (rowId == -1) {
            throw new InsertException("INSERT operation failed.");
        }

        peer.setId(rowId);
    }

    public void update(Peer peer) throws InvalidModelException, UpdateException {
        if (peer == null) {
            throw new InvalidModelException("Peer is null.");
        }

        if (peer.getId() < 0) {
            throw new InvalidModelException("Peer ID is invalid.");
        }

        ContentValues values = getPeerValues(peer);

        String whereClause = Columns.Peer._ID + " = " + peer.getId();

        long updatedRows = getWritableDatabase().update(
            TABLE_NAME,
            values,
            whereClause,
            null
        );

        if (updatedRows == 0) {
            throw new UpdateException("UPDATE operation failed.");
        }
    }

    public void delete(Peer peer) throws InvalidModelException, DeleteException {
        if (peer == null) {
            throw new InvalidModelException("Peer is null.");
        }

        if (peer.getId() < 0) {
            throw new InvalidModelException("Peer ID is invalid.");
        }

        String whereClause = Columns.Peer._ID + " = " + peer.getId();

        long deletedRows = getWritableDatabase().delete(
                TABLE_NAME,
                whereClause,
                null
        );

        if (deletedRows == 0) {
            throw new DeleteException("DELETE operation failed.");
        }

        peer.setId(-1);
    }

    private Peer buildFromCursor(Cursor c) throws InvalidCursorException, NoSuchModelException {
        if (c == null) {
            throw new InvalidCursorException("Cursor is null.");
        }

        if (!c.moveToFirst()) {
            throw new InvalidCursorException("Cannot move to first element of the cursor.");
        }

        try {
            long id = c.getLong(c.getColumnIndexOrThrow(Columns.Peer._ID));
            byte[] publicKeyBytes = c.getBlob(c.getColumnIndexOrThrow(Columns.Peer.COLUMN_NAME_PUBLIC_KEY));
            PublicKey publicKey = new PublicKey(publicKeyBytes);
            String userName = c.getString(c.getColumnIndexOrThrow(Columns.Peer.COLUMN_NAME_USERNAME));
            String statusMessage = c.getString(c.getColumnIndexOrThrow(Columns.Peer.COLUMN_NAME_STATUS_MESSAGE));
            String picturePath = c.getString(c.getColumnIndexOrThrow(Columns.Peer.COLUMN_NAME_PICTURE_PATH));
            boolean online = c.getInt(c.getColumnIndexOrThrow(Columns.Peer.COLUMN_NAME_IS_ONLINE)) != 0;

            Peer peer = new Peer();
            peer.setId(id);
            peer.setPublicKey(publicKey);
            peer.setUserName(userName);
            peer.setStatusMessage(statusMessage);
            peer.setPicturePath(picturePath);
            peer.setOnline(online);

            return peer;

        } catch (CursorIndexOutOfBoundsException e) {
            throw new InvalidCursorException(e);
        }
    }

    private ContentValues getPeerValues(Peer peer) {
        ContentValues values = new ContentValues();

        values.put(Columns.Peer.COLUMN_NAME_PUBLIC_KEY, peer.getPublicKey().getBytes());
        values.put(Columns.Peer.COLUMN_NAME_USERNAME, peer.getUserName());
        values.put(Columns.Peer.COLUMN_NAME_STATUS_MESSAGE, peer.getStatusMessage());
        values.put(Columns.Peer.COLUMN_NAME_PICTURE_PATH, peer.getPicturePath());
        values.put(Columns.Peer.COLUMN_NAME_IS_ONLINE, peer.isOnline());

        return values;
    }
}
