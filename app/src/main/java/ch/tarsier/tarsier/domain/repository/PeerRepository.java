package ch.tarsier.tarsier.domain.repository;

import android.content.ContentValues;
import android.database.Cursor;
import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;

/**
 * @author xawill
 */
public class PeerRepository extends AbstractRepository {

    private static final String TABLE_NAME = Columns.Peer.TABLE_NAME;
    private static final String[] COLUMNS = new String[] {
        Columns.Peer._ID,
        Columns.Peer.COLUMN_NAME_PUBLIC_KEY,
        Columns.Peer.COLUMN_NAME_USERNAME,
        Columns.Peer.COLUMN_NAME_STATUS_MESSAGE,
        Columns.Peer.COLUMN_NAME_PICTURE_PATH,
        Columns.Peer.COLUMN_NAME_IS_ONLINE
    };

    public PeerRepository(Database database) {
        super(database);
    }

    public Peer findById(long id) {
        String selection = Columns.Peer._ID + " = " + id;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                COLUMNS,
                selection,
                null, null, null, null
        );

        //Empty cursor
        if (cursor.getCount() <= 0) {
            //TODO : throw NoSuchModelException
        }

        return buildFromCursor(cursor);
    }

    public Peer findByPublicKey(PublicKey publicKey) {
        String selection = Columns.Peer.COLUMN_NAME_PUBLIC_KEY + " = " + publicKey;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                COLUMNS,
                selection,
                null, null, null, null
        );

        //Empty cursor
        if (cursor.getCount() <= 0) {
            //TODO : throw NoSuchModelException
        }

        return buildFromCursor(cursor);
    }

    public void insert(Peer peer) {
        ContentValues values = getPeerValues(peer);

        long rowId = getWritableDatabase().insert(
            TABLE_NAME,
            null,
            values
        );

        //TODO : Uncomment when InsertException is created
        /*if (rowId == -1) {
            throw new InsertException("INSERT operation failed");
        }*/

        peer.setId(rowId);
    }

    public void update(Peer peer) {
        if (peer.getId() < 0) {
            //TODO : throw InvalidModelException
        }

        ContentValues values = getPeerValues(peer);

        String selection = Columns.Peer._ID + " = " + peer.getId();

        long updatedRows = getWritableDatabase().update(
            TABLE_NAME,
            values,
            selection,
            null
        );

        if (updatedRows == 0) {
            //TODO : throw UpdateException
        }
    }

    public void delete(Peer peer) {
        if (peer.getId() < 0) {
            //TODO : throw InvalidModelException
        }

        String selection = Columns.Peer._ID + " = " + peer.getId();

        long deletedRows = getWritableDatabase().delete(
                TABLE_NAME,
                selection,
                null
        );

        if (deletedRows == 0) {
            //TODO : throw UpdateException
        }
    }

    private Peer buildFromCursor(Cursor c) {
        if (c == null) {
            //TODO : throw InvalidCursorException
        }

        long id = c.getLong(c.getColumnIndex(Columns.Peer._ID));
        byte[] publicKeyBytes = c.getBlob(c.getColumnIndex(Columns.Peer.COLUMN_NAME_PUBLIC_KEY));
        PublicKey publicKey = new PublicKey(publicKeyBytes);
        String userName = c.getString(c.getColumnIndex(Columns.Peer.COLUMN_NAME_USERNAME));
        String statusMessage = c.getString(c.getColumnIndex(Columns.Peer.COLUMN_NAME_STATUS_MESSAGE));
        String picturePath = c.getString(c.getColumnIndex(Columns.Peer.COLUMN_NAME_PICTURE_PATH));
        boolean online = c.getInt(c.getColumnIndex(Columns.Peer.COLUMN_NAME_IS_ONLINE)) != 0;

        Peer peer = new Peer();
        peer.setId(id);
        peer.setPublicKey(publicKey);
        peer.setUserName(userName);
        peer.setStatusMessage(statusMessage);
        peer.setPicturePath(picturePath);
        peer.setOnline(online);

        return peer;
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
