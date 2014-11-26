package ch.tarsier.tarsier.domain.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
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
public class ChatRepository extends AbstractRepository {

    private static final String TABLE_NAME = Columns.Chat.TABLE_NAME;

    private static final String ID_DESCEND = Columns.Message._ID + " DESC";

    private PeerRepository mPeerRepository;

    public ChatRepository(Database database) {
        super(database);

        mPeerRepository = Tarsier.app().getPeerRepository();
    }

    public Chat findById(long id) throws IllegalArgumentException, NoSuchModelException, InvalidCursorException {
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
            throw new InvalidCursorException("Cannot move to first element of the cursor.");
        }

        try {
            return buildFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        } finally {
            cursor.close();
        }
    }

    public void insert(Chat chat) throws InvalidModelException, InsertException {
        if (chat == null) {
            throw new InvalidModelException("Chat is null.");
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
        if (chat == null) {
            throw new InvalidModelException("Chat is null.");
        }

        if (chat.getId() < 0) {
            throw new InvalidModelException("Chat ID is invalid.");
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
        if (chat == null) {
            throw new InvalidModelException("Chat is null.");
        }

        if (chat.getId() < 0) {
            throw new InvalidModelException("Chat ID is invalid.");
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

    public List<Chat> fetchAllChats() throws InvalidCursorException {

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null, null, null, null, null,
                ID_DESCEND,
                null
        );

        List<Chat> chatList = new ArrayList<Chat>();

        if (!cursor.moveToFirst()) {
            throw new InvalidCursorException("Cannot move to first element of the cursor.");
        }

        do {
            try {
                chatList.add(buildFromCursor(cursor));
            } catch (InvalidCursorException e) {
                e.printStackTrace();
            } catch (NoSuchModelException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());

        cursor.close();

        return chatList;
    }

    private Chat buildFromCursor(Cursor c) throws InvalidCursorException, NoSuchModelException {
        if (c == null) {
            throw new InvalidCursorException("Cursor is null.");
        }

        try {
            long id = c.getLong(c.getColumnIndexOrThrow(Columns.Chat._ID));
            String title = c.getString(c.getColumnIndexOrThrow(Columns.Chat.COLUMN_NAME_TITLE));
            long hostId = c.getLong(c.getColumnIndexOrThrow(Columns.Chat.COLUMN_NAME_HOST_ID));
            boolean isPrivate = c.getInt(c.getColumnIndexOrThrow(
                    Columns.Chat.COLUMN_NAME_IS_PRIVATE)) != 0;

            Peer host = mPeerRepository.findById(hostId);

            Chat chat = new Chat();
            chat.setId(id);
            chat.setTitle(title);
            chat.setHost(host);
            chat.setPrivate(isPrivate);

            return chat;

        } catch (CursorIndexOutOfBoundsException e) {
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


}
