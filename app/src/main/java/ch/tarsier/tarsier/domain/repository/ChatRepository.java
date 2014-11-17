package ch.tarsier.tarsier.domain.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

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

    private static final String[] COLUMNS = new String[] {
            Columns.Chat._ID,
            Columns.Chat.COLUMN_NAME_TITLE,
            Columns.Chat.COLUMN_NAME_HOST_ID,
            Columns.Chat.COLUMN_NAME_IS_PRIVATE
    };

    private static final String TABLE_NAME = Columns.Chat.TABLE_NAME;

    private PeerRepository mPeerRepository;

    public ChatRepository(Database database) {
        super(database);

        mPeerRepository = Tarsier.app().getPeerRepository();
    }

    public Chat findById(long id) throws NoSuchModelException {
        String selection = Columns.Message.COLUMN_NAME_CHAT_ID + " = " + id;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                COLUMNS,
                selection,
                null, null, null, null
        );

        if (cursor == null || cursor.getCount() == 0) {
            throw new NoSuchModelException("No Chat with id " + id + " found.");
        }

        try {
            return buildFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        }
    }

    public void insert(Chat chat) throws InvalidModelException, InsertException {
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
        if (chat.getId() <= 0) {
            throw new InvalidModelException("Chat ID is invalid");
        }

        ContentValues values = getValuesForChat(chat);

        String whereClause = Columns.Chat._ID + " = " + chat.getId()
                           + " LIMIT 1";

        long rowUpdated = getWritableDatabase().update(
                TABLE_NAME,
                values,
                whereClause,
                null
        );

        if (rowUpdated == 0) {
            throw new UpdateException("UPDATE operation failed");
        }
    }

    public void delete(Chat chat) throws InvalidModelException, DeleteException {
        if (chat.getId() < 0) {
            throw new InvalidModelException("Chat ID is invalid");
        }

        String whereClause = Columns.Chat._ID + " = " + chat.getId()
                           + " LIMIT 1";

        long rowDeleted = getWritableDatabase().delete(
                TABLE_NAME,
                whereClause,
                null
        );

        if (rowDeleted == 0) {
            throw new DeleteException("DELETE operation failed");
        }
    }

    private Chat buildFromCursor(Cursor c) throws InvalidCursorException {
        try {
            long id = c.getLong(c.getColumnIndex(Columns.Chat._ID));
            String title = c.getString(c.getColumnIndex(Columns.Chat.COLUMN_NAME_TITLE));
            long hostId = c.getLong(c.getColumnIndex(Columns.Chat.COLUMN_NAME_HOST_ID));
            boolean isPrivate = c.getInt(c.getColumnIndex(Columns.Chat.COLUMN_NAME_IS_PRIVATE)) != 0;

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
