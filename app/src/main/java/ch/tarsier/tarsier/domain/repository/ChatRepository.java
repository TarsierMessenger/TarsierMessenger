package ch.tarsier.tarsier.domain.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;

import java.util.List;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.exception.DeleteException;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.exception.UpdateException;

/**
 * @author romac
 * @author McMoudi
 */
public class ChatRepository extends AbstractRepository<Chat> {

    private static final String TABLE_NAME = Columns.Chat.TABLE_NAME;
    private static final String ID_DESCEND = Columns.Chat._ID + " DESC";
    private static final String TAG = "ChatRepository";

    private PeerRepository mPeerRepository;

    public ChatRepository(Database database) {
        super(database);

        mPeerRepository = Tarsier.app().getPeerRepository();
    }

    public void insert(Chat chat) throws InvalidModelException, InsertException {
        // cannot use validate(Chat chat) because before inserting a Chat, it's id is < 0
        if (chat == null) {
            throw new InvalidModelException("Chat is null.");
        }

        if (exists(chat)) {
            return;
        }

        ContentValues values = getValuesForChat(chat);

        long rowId = getWritableDatabase().insert(
                TABLE_NAME,
                null,
                values
        );

        if (rowId == -1) {
            throw new InsertException("INSERT operation failed.");
        }

        chat.setId(rowId);
    }

    public void update(Chat chat) throws InvalidModelException, UpdateException {
        validate(chat);

        if (!exists(chat)) {
            return;
        }

        ContentValues values = getValuesForChat(chat);

        String whereClause = Columns.Chat._ID + " = " + chat.getId();

        long rowUpdated = getWritableDatabase().update(
                TABLE_NAME,
                values,
                whereClause,
                null
        );

        if (rowUpdated == 0) {
            throw new UpdateException("UPDATE operation failed.");
        }
    }

    public void delete(Chat chat) throws InvalidModelException, DeleteException {
        validate(chat);

        if (!exists(chat)) {
            return;
        }

        String whereClause = Columns.Chat._ID + " = " + chat.getId();

        long rowDeleted = getWritableDatabase().delete(
                TABLE_NAME,
                whereClause,
                null
        );

        if (rowDeleted == 0) {
            throw new DeleteException("DELETE operation failed.");
        }

        chat.setId(-1);
    }

    public List<Chat> findAll() throws NoSuchModelException {

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

    public Chat findById(long id) throws IllegalArgumentException, NoSuchModelException {
        if (id < 0) {
            throw new IllegalArgumentException("Chat ID is invalid.");
        }

        String whereClause = Columns.Chat._ID + " = " + id;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null, null,
                "1"
        );

        if (!cursor.moveToFirst()) {
            throw new NoSuchModelException("Couldn't find a Chat with id " + id);
        }

        try {
            return buildFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        } finally {
            cursor.close();
        }
    }

    public Chat getChatWithPeer(Peer peer) throws InvalidModelException, NoSuchModelException {
        if (peer == null) {
            throw new InvalidModelException("Peer is null.");
        }

        if (peer.getId() < 0) {
            throw new InvalidModelException("Peer ID is invalid.");
        }

        String whereClause = Columns.Chat.COLUMN_NAME_HOST_ID + " = " + peer.getId();

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null, null,
                "1"
        );

        if (!cursor.moveToFirst()) {
            throw new NoSuchModelException("Couldn't find a Chat with PeerId " + peer.getId());
        }

        try {
            return buildFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        } finally {
            cursor.close();
        }
    }

    @Override
    protected Chat buildFromCursor(Cursor c) throws InvalidCursorException {
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
            long id = c.getLong(c.getColumnIndexOrThrow(Columns.Chat._ID));
            String title = c.getString(c.getColumnIndexOrThrow(Columns.Chat.COLUMN_NAME_TITLE));
            long hostId = c.getLong(c.getColumnIndexOrThrow(Columns.Chat.COLUMN_NAME_HOST_ID));
            boolean isPrivate = c.getInt(c.getColumnIndexOrThrow(
                    Columns.Chat.COLUMN_NAME_IS_PRIVATE)) != 0;

            // if the hostId is invalid, we discard the chat
            if (hostId < 0) {
                return null;
            }

            Peer host = mPeerRepository.findById(hostId);

            Chat chat = new Chat();
            chat.setId(id);
            chat.setTitle(title);
            chat.setHost(host);
            chat.setPrivate(isPrivate);

            return chat;

        } catch (CursorIndexOutOfBoundsException e) {
            throw new InvalidCursorException(e);
        } catch (NoSuchModelException e) {
            throw new InvalidCursorException(e);
        }
    }

    private ContentValues getValuesForChat(Chat chat) {
        ContentValues values = new ContentValues();

        values.put(Columns.Chat.COLUMN_NAME_TITLE, chat.getTitle());
        values.put(Columns.Chat.COLUMN_NAME_HOST_ID, chat.getHost().getId());
        values.put(Columns.Chat.COLUMN_NAME_IS_PRIVATE, chat.isPrivate());

        return values;
    }

    private boolean exists(Chat chat) {
        Log.d(TAG, "Check if the chat exists");

        if (chat.getId() < 0) {
            return false;
        }

        String whereClause = Columns.Chat._ID + " = " + chat.getId();

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null, null,
                "1"
        );

        return cursor.moveToFirst();
    }

    // Check if the Chat model is valid
    private void validate(Chat chat) throws InvalidModelException {
        Log.d(TAG, "validate the chat");

        if (chat == null) {
            throw new InvalidModelException("Chat is null.");
        }

        if (chat.getId() < 0) {
            throw new InvalidModelException("Chat ID is invalid.");
        }
    }
}
