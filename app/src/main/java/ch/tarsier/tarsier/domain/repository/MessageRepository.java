package ch.tarsier.tarsier.domain.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import java.util.List;

import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
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
public class MessageRepository extends AbstractRepository<Message> {

    private static final String TABLE_NAME = Columns.Message.TABLE_NAME;

    private static final String DATETIME_DESCEND = Columns.Message.COLUMN_NAME_DATETIME + " DESC";

    public static final int ALL = Integer.MIN_VALUE;

    public MessageRepository(Database database) {
        super(database);
    }

    public void insert(Message message) throws InvalidModelException, InsertException {
        // cannot use validate(Message message) because before inserting a Message, it's id is < 0
        if (message == null) {
            throw new InvalidModelException("Message is null.");
        }

        if (exists(message)) {
            return;
        }

        ContentValues values = getValuesForMessage(message);

        long rowId = getWritableDatabase().insert(
                TABLE_NAME,
                null,
                values
        );

        if (rowId == -1) {
            throw new InsertException("INSERT operation failed.");
        }

        message.setId(rowId);
    }

    public void update(Message message) throws InvalidModelException, UpdateException {
        validate(message);

        if (!exists(message)) {
            return;
        }

        ContentValues values = getValuesForMessage(message);

        String whereClause = Columns.Message._ID + " = " + message.getId();

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

    public void delete(Message message) throws InvalidModelException, DeleteException {
        validate(message);

        if (!exists(message)) {
            return;
        }

        String whereClause = Columns.Message._ID + " = " + message.getId();

        long rowDeleted = getWritableDatabase().delete(
                TABLE_NAME,
                whereClause,
                null
        );

        if (rowDeleted == 0) {
            throw new DeleteException("DELETE operation failed.");
        }

        message.setId(-1);
    }

    public Message findById(long id)
            throws IllegalArgumentException, NoSuchModelException {

        if (id < 0) {
            throw new IllegalArgumentException("Message ID is invalid.");
        }

        String whereClause = Columns.Message._ID + " = " + id;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null, null,
                "1"
        );

        if (!cursor.moveToFirst()) {
            throw new NoSuchModelException("Couldn't find Message with id " + id);
        }

        try {
            return buildFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        } finally {
            cursor.close();
        }
    }

    public List<Message> findByChat(Chat chat)
            throws IllegalArgumentException, NoSuchModelException {

        return findByChat(chat, ALL);
    }

    public List<Message> findByChat(Chat chat, int max)
            throws IllegalArgumentException, NoSuchModelException {

        return findByChatUntil(chat, 0, max);
    }

    /**
     * @param chat   the chat from which the messages shall be retrieved
     * @param until  retrieve messages older than this timestamp
     * @param max    number of messages to retrieve
     * @return a list of message
     */
    public List<Message> findByChatUntil(Chat chat, long until, int max)
            throws IllegalArgumentException, NoSuchModelException {

        if (chat.getId() < 0) {
            throw new IllegalArgumentException("Chat ID is invalid.");
        }

        String whereClause = Columns.Message.COLUMN_NAME_CHAT_ID + " = " + chat.getId()
                 + " AND " + Columns.Message.COLUMN_NAME_DATETIME + " < " + until;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null,
                DATETIME_DESCEND,
                Integer.toString(max));

        try {
            return buildListFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        } finally {
            cursor.close();
        }
    }

    public Message getLastMessageOf(Chat chat) throws NoSuchModelException, InvalidModelException {
        if (chat == null) {
            throw new InvalidModelException("Chat is null.");
        }

        if (chat.getId() < 0) {
            throw new IllegalArgumentException("Chat ID is invalid.");
        }

        String whereClause = Columns.Message.COLUMN_NAME_CHAT_ID + " = " + chat.getId();

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null,
                DATETIME_DESCEND,
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

    public List<Message> findAll() throws NoSuchModelException {

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null, null, null, null, null,
                DATETIME_DESCEND,
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
    protected Message buildFromCursor(Cursor c) throws InvalidCursorException {
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
            int chatId = c.getInt(c.getColumnIndexOrThrow(Columns.Message.COLUMN_NAME_CHAT_ID));
            String text = c.getString(c.getColumnIndexOrThrow(Columns.Message.COLUMN_NAME_MSG));
            String base64PublicKey = c.getString(
                    c.getColumnIndexOrThrow(Columns.Message.COLUMN_NAME_SENDER_PUBLIC_KEY));
            boolean isSentByUser = c.getInt(c.getColumnIndexOrThrow(
                    Columns.Message.COLUMN_NAME_SENT_BY_USER)) != 0;
            long dateTime = c.getLong(c.getColumnIndexOrThrow(Columns.Message.COLUMN_NAME_DATETIME));

            Message message = null;
            if (isSentByUser) {
                message = new Message(chatId, text, dateTime);
            } else {
                message = new Message(chatId, text, new PublicKey(base64PublicKey), dateTime);
            }
            message.setId(c.getLong(c.getColumnIndexOrThrow(Columns.Message._ID)));

            return message;

        } catch (CursorIndexOutOfBoundsException e) {
            throw new InvalidCursorException(e);
        }
    }

    private ContentValues getValuesForMessage(Message message) {
        ContentValues values = new ContentValues();

        values.put(Columns.Message.COLUMN_NAME_CHAT_ID, message.getChatId());
        values.put(Columns.Message.COLUMN_NAME_MSG, message.getText());
        values.put(Columns.Message.COLUMN_NAME_SENDER_PUBLIC_KEY, message.getSenderPublicKey().base64Encoded());
        values.put(Columns.Message.COLUMN_NAME_SENT_BY_USER, message.isSentByUser());
        values.put(Columns.Message.COLUMN_NAME_DATETIME, message.getDateTime());

        return values;
    }

    private boolean exists(Message message) {
        if (message.getId() < 0) {
            return false;
        }

        String whereClause = Columns.Message._ID + " = " + message.getId();

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null, null,
                "1"
        );

        return cursor.moveToFirst();
    }

    // Check if the Message model is valid
    private void validate(Message message) throws InvalidModelException {
        if (message == null) {
            throw new InvalidModelException("Message is null.");
        }

        if (message.getId() < 0) {
            throw new InvalidModelException("Message ID is invalid.");
        }
    }
}
