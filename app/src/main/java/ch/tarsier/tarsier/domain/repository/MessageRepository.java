package ch.tarsier.tarsier.domain.repository;

import android.content.ContentValues;
import android.database.Cursor;

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

    public Message findById(long id) throws NoSuchModelException, InvalidCursorException {
        String selection = COLUMN_ID + " = " + id;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                COLUMNS,
                selection,
                null, null, null, null
        );

        if (cursor == null) {
            //TODO check that query() return null if it failed to query
            throw new NoSuchModelException("Cursor is null");
        }

        return buildFromCursor(cursor);
    }

    public void insert(Message message) throws InsertException {
        ContentValues contentValues = getContentValuesForMessage(message);

        long rowId = getWritableDatabase().insert(
                TABLE_NAME,
                null,
                contentValues
        );

        if (rowId == -1) {
            throw new InsertException("INSERT operation failed");
        }

        message.setId(rowId);
    }

    public void update(Message message) throws InvalidModelException, UpdateException {
        if (message.getId() < 0) {
            throw new InvalidModelException("Message's id invalid");
        }

        ContentValues contentValues = getContentValuesForMessage(message);

        String selection = COLUMN_ID + " = " + message.getId();

        long rowUpdated = getWritableDatabase().update(
                TABLE_NAME,
                contentValues,
                selection,
                null
        );

        if (rowUpdated == 0) {
            throw new UpdateException("UPDATE operation failed");
        }
    }

    public void delete(Message message) throws InvalidModelException, DeleteException {
        if (message.getId() < 0) {
            throw new InvalidModelException("Message's id invalid");
        }

        String selection = COLUMN_ID + " = " + message.getId();

        long rowDeleted = getWritableDatabase().delete(
                TABLE_NAME,
                selection,
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

        int chatId = c.getInt(c.getColumnIndex(Columns.Message.COLUMN_NAME_CHAT_ID));
        String text = c.getString(c.getColumnIndex(Columns.Message.COLUMN_NAME_MSG));
        long senderId = c.getLong(c.getColumnIndex(Columns.Message.COLUMN_NAME_SENDER_ID));
        long dateTime = c.getLong(c.getColumnIndex(Columns.Message.COLUMN_NAME_DATETIME));

        long userId = Tarsier.app().getUserPreferences().getId();

        Message message = null;
        if (senderId == userId) {
            message = new Message(chatId, text, dateTime);
        } else {
            message = new Message(chatId, text, senderId, dateTime);
        }

        return message;
    }

    private ContentValues getContentValuesForMessage(Message message) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Columns.Message.COLUMN_NAME_CHAT_ID, message.getChatID());
        contentValues.put(Columns.Message.COLUMN_NAME_MSG, message.getText());
        contentValues.put(Columns.Message.COLUMN_NAME_SENDER_ID, message.getPeerId());
        contentValues.put(Columns.Message.COLUMN_NAME_DATETIME, message.getDateTime());

        return contentValues;
    }
}
