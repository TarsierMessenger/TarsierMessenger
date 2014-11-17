package ch.tarsier.tarsier.domain.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.exception.DeleteException;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.exception.UpdateException;

/**
 * @author gluthier
 * @author McMoudi
 */
public class MessageRepository extends AbstractRepository {

    private static final String TABLE_NAME = Columns.Message.TABLE_NAME;

    private static final String COLUMN_ID = "_id";

    private static final String[] COLUMNS = new String[] {
        Columns.Message._ID,
        Columns.Message.COLUMN_NAME_MSG,
        Columns.Message.COLUMN_NAME_DATETIME,
        Columns.Message.COLUMN_NAME_SENDER_ID,
        Columns.Message.COLUMN_NAME_CHAT_ID
    };

    public MessageRepository(Database database) {
        super(database);
    }

    public Message findById(long id) throws IllegalArgumentException, NoSuchModelException {
        if (id < 1) {
            throw new IllegalArgumentException("Message ID cannot be < 1");
        }

        String whereClause = COLUMN_ID + " = " + id;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                COLUMNS,
                whereClause,
                null, null, null, null
        );

        if (cursor == null) {
            //TODO check that query() return null if it failed to query
            throw new NoSuchModelException("Cursor is null");
        }

        try {
            return buildFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        }
    }

    public void insert(Message message) throws InvalidModelException, InsertException {
        ContentValues values = getValuesForMessage(message);

        long rowId = getWritableDatabase().insert(
                TABLE_NAME,
                null,
                values
        );

        if (rowId == -1) {
            throw new InsertException("INSERT operation failed");
        }

        message.setId(rowId);
    }

    public void update(Message message) throws InvalidModelException, UpdateException {
        if (message.getId() < 1) {
            throw new InvalidModelException("Message's id invalid");
        }

        ContentValues values = getValuesForMessage(message);

        String whereClause = COLUMN_ID + " = " + message.getId();

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

    public void delete(Message message) throws InvalidModelException, DeleteException {
        if (message.getId() < 1) {
            throw new InvalidModelException("Message ID is invalid");
        }

        String whereClause = COLUMN_ID + " = " + message.getId();

        long rowDeleted = getWritableDatabase().delete(
                TABLE_NAME,
                whereClause,
                null
        );

        if (rowDeleted == 0) {
            throw new DeleteException("DELETE operation failed");
        }
    }

    private Message buildFromCursor(Cursor c) throws InvalidCursorException {
        if (c == null) {
            throw new InvalidCursorException("Cursor is null");
        }

        try {
            int chatId = c.getInt(c.getColumnIndex(Columns.Message.COLUMN_NAME_CHAT_ID));
            String text = c.getString(c.getColumnIndex(Columns.Message.COLUMN_NAME_MSG));
            long senderId = c.getLong(c.getColumnIndex(Columns.Message.COLUMN_NAME_SENDER_ID));
            long dateTime = c.getLong(c.getColumnIndex(Columns.Message.COLUMN_NAME_DATETIME));

            long userId = Tarsier.app().getUserPreferences().getId();

            Message message;
            if (senderId == userId) {
                message = new Message(chatId, text, dateTime);
            } else {
                message = new Message(chatId, text, senderId, dateTime);
            }

            return message;

        } catch (CursorIndexOutOfBoundsException e) {
            throw new InvalidCursorException(e);
        }
    }

    private ContentValues getValuesForMessage(Message message) {
        ContentValues values = new ContentValues();

        values.put(Columns.Message.COLUMN_NAME_CHAT_ID, message.getChatId());
        values.put(Columns.Message.COLUMN_NAME_MSG, message.getText());
        values.put(Columns.Message.COLUMN_NAME_SENDER_ID, message.getPeerId());
        values.put(Columns.Message.COLUMN_NAME_DATETIME, message.getDateTime());

        return values;
    }
}
