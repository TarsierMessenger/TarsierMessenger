package ch.tarsier.tarsier.domain.repository;

import android.database.Cursor;

import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.model.Chat;
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

        return fromCursor(cursor);
    }

    public Peer findByPublicKey(PublicKey publicKey) {
        String selection = Columns.Peer.COLUMN_NAME_PUBLIC_KEY + " = " + publicKey;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                COLUMNS,
                selection,
                null, null, null, null
        );

        return fromCursor(cursor);
    }

    public void insert(Peer peer) {
        // TODO: Implement Peer.insert()
    }

    public void update(Peer peer) {
        // TODO: Implement Peer.update()
    }

    public void delete(Peer peer) {
        // TODO: Implement Peer.delete()
    }

    private Peer fromCursor(Cursor c) {
        long id = c.getLong(c.getColumnIndex(Columns.Peer._ID));
        byte[] publicKeyBytes = c.getBlob(c.getColumnIndex(Columns.Peer.COLUMN_NAME_PUBLIC_KEY));
        PublicKey publicKey = new PublicKey(publicKeyBytes);
        String userName = c.getString(c.getColumnIndex(Columns.Peer.COLUMN_NAME_USERNAME));
        String statusMessage = c.getString(c.getColumnIndex(Columns.Peer.COLUMN_NAME_STATUS_MESSAGE));;
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
}
