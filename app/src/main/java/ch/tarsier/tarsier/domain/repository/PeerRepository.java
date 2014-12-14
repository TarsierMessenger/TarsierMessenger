package ch.tarsier.tarsier.domain.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import java.util.List;

import ch.tarsier.tarsier.Tarsier;
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
 * PeerRepository is the class that interact with the database
 * for the queries concerning the Peer model.
 *
 * @author xawill
 */
public class PeerRepository extends AbstractRepository<Peer> {

    private static final String TABLE_NAME = Columns.Peer.TABLE_NAME;
    private static final String ID_DESCEND = Columns.Peer._ID + " DESC";

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

        if (!cursor.moveToFirst()) {
            throw new NoSuchModelException("Could not find Peer with id " + id);
        }

        try {
            return buildFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        } finally {
            cursor.close();
        }
    }

    public Peer findByPublicKey(byte[] publicKey)
            throws IllegalArgumentException, NoSuchModelException {

        if (publicKey == null) {
            throw new IllegalArgumentException("PublicKey is null.");
        }

        return findByPublicKey(new PublicKey(publicKey));
    }

    public Peer findByPublicKey(PublicKey publicKey)
            throws IllegalArgumentException, NoSuchModelException {

        if (publicKey == null) {
            throw new IllegalArgumentException("PublicKey is null.");
        }

        String whereClause = Columns.Peer.COLUMN_NAME_PUBLIC_KEY + " = '" + publicKey.base64Encoded() + "'";

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null, null,
                "1"
        );

        if (!cursor.moveToFirst()) {
            throw new NoSuchModelException(
                    "Cannot find a peer with public key "
                            + publicKey.base64Encoded()
                            + "\n user publicKey : "
                            + new PublicKey(Tarsier.app()
                            .getUserPreferences()
                            .getKeyPair()
                            .getPublicKey())
                            .base64Encoded());
        }

        try {
            return buildFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        } finally {
            cursor.close();
        }
    }

    public void insert(Peer peer) throws InvalidModelException, InsertException {
        // cannot use validate(Peer peer) because before inserting a Peer, it's id is < 0
        if (peer == null) {
            throw new InvalidModelException("Peer is null.");
        }

        if (exists(peer)) {
            return;
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

    public void insertIfNotExistsWithPublicKey(Peer peer) throws InvalidModelException, InsertException {
        if (peer == null) {
            throw new InvalidModelException("Peer is null.");
        }

        try {
            Peer existingPeer = findByPublicKey(peer.getPublicKey());
            peer.setId(existingPeer.getId());
            update(peer);
        } catch (NoSuchModelException e) {
            insert(peer);
        } catch (UpdateException e) {
            e.printStackTrace();
        }
    }

    public void update(Peer peer) throws InvalidModelException, UpdateException {
        validate(peer);

        if (!exists(peer)) {
            return;
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
        validate(peer);

        if (!exists(peer)) {
            return;
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

    // return null if there are no chats in the database
    public List<Peer> findAll() throws NoSuchModelException {

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null, null, null, null, null,
                ID_DESCEND,
                null
        );

        try {
            return buildListFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        } finally {
            cursor.close();
        }
    }

    @Override
    protected Peer buildFromCursor(Cursor c) throws InvalidCursorException {
        if (c == null) {
            throw new InvalidCursorException("Cursor is null.");
        }

        if (c.isAfterLast()) {
            throw new InvalidCursorException("Cursor is after last row.");
        }

        if (c.isBeforeFirst()) {
            c.moveToFirst();
        }

        try {
            long id = c.getLong(c.getColumnIndexOrThrow(Columns.Peer._ID));
            String base64PublicKey = c.getString(c.getColumnIndexOrThrow(Columns.Peer.COLUMN_NAME_PUBLIC_KEY));
            String userName = c.getString(c.getColumnIndexOrThrow(Columns.Peer.COLUMN_NAME_USERNAME));
            String statusMessage = c.getString(c.getColumnIndexOrThrow(Columns.Peer.COLUMN_NAME_STATUS_MESSAGE));
            String picturePath = c.getString(c.getColumnIndexOrThrow(Columns.Peer.COLUMN_NAME_PICTURE_PATH));
            boolean online = c.getInt(c.getColumnIndexOrThrow(Columns.Peer.COLUMN_NAME_IS_ONLINE)) != 0;

            Peer peer = new Peer();
            peer.setId(id);
            peer.setPublicKey(new PublicKey(base64PublicKey));
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

        values.put(Columns.Peer.COLUMN_NAME_PUBLIC_KEY, peer.getPublicKey().base64Encoded());
        values.put(Columns.Peer.COLUMN_NAME_USERNAME, peer.getUserName());
        values.put(Columns.Peer.COLUMN_NAME_STATUS_MESSAGE, peer.getStatusMessage());
        values.put(Columns.Peer.COLUMN_NAME_PICTURE_PATH, peer.getPicturePath());
        values.put(Columns.Peer.COLUMN_NAME_IS_ONLINE, peer.isOnline());

        return values;
    }

    private boolean exists(Peer peer) {
        if (peer.getId() < 0) {
            return false;
        }

        String whereClause = Columns.Peer._ID + " = " + peer.getId();

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null, null,
                "1"
        );

        return cursor.moveToFirst();
    }

    // Check if the Peer model is valid
    private void validate(Peer peer) throws InvalidModelException {
        if (peer == null) {
            throw new InvalidModelException("Peer is null.");
        }

        if (peer.getId() < 0) {
            throw new InvalidModelException("Peer ID is invalid.");
        }
    }
}
