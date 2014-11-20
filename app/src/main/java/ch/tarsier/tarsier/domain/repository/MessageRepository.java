package ch.tarsier.tarsier.domain.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.model.Chat;
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

    private static final String DATETIME_ASCEND = Columns.Message.COLUMN_NAME_DATETIME + "ASC";
    private static final String DATETIME_DESCEND = Columns.Message.COLUMN_NAME_DATETIME + "DESC";


    public MessageRepository(Database database) {
        super(database);
    }

    public Message findById(long id) throws IllegalArgumentException, NoSuchModelException {
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

        try {
            return buildFromCursor(cursor);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        } finally {
            cursor.close();
        }
    }

    public void insert(Message message) throws InvalidModelException, InsertException {
        if (message == null) {
            throw new InvalidModelException("Message is null.");
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
        if (message == null) {
            throw new InvalidModelException("Message is null.");
        }

        if (message.getId() < 0) {
            throw new InvalidModelException("Message ID is invalid.");
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
        if (message == null) {
            throw new InvalidModelException("Message is null.");
        }

        if (message.getId() < 0) {
            throw new InvalidModelException("Message ID is invalid.");
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

    public List<Message> findByChat(Chat chat) throws IllegalArgumentException, NoSuchModelException {
        final int all = -1;
        return findByChat(chat, all);
    }

    public List<Message> findByChat(Chat chat, int number) throws IllegalArgumentException, NoSuchModelException {
        if (chat.getId() < 0) {
            throw new IllegalArgumentException("Chat ID is invalid.");
        }

        String whereClause = Columns.Message.COLUMN_NAME_CHAT_ID + " = " + chat.getId();

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null, null
        );

        try {
            return buildListFromCursor(cursor, number);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        }
    }

    /**
     * @param chat   the chat from which the messages shall be retrieved
     * @param since  retrieve message older than this timestamp
     * @param number number of messages to retrieve
     * @return a list of message
     */
    public List<Message> findByChat(Chat chat, long since, int number)
            throws IllegalArgumentException, NoSuchModelException {

        if (chat.getId() < 0) {
            throw new IllegalArgumentException("Chat ID is invalid.");
        }

        String whereClause = Columns.Message.COLUMN_NAME_CHAT_ID + " = " + chat.getId();

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                null,
                whereClause,
                null, null, null,
                DATETIME_DESCEND);

        if (!cursor.moveToFirst()) {
            throw new NoSuchModelException("Cannot move to first element of the cursor.");
        }

        long cTime;
        do {
            cTime = cursor.getLong(cursor.getColumnIndex(Columns.Message.COLUMN_NAME_DATETIME));
        } while (cursor.moveToNext() && since < cTime);
        //don't forget the descending order. since should be > cTime, amIRight?

        List<Message> msgs;
        try {
            msgs = buildListFromCursor(cursor, number);
        } catch (InvalidCursorException e) {
            throw new NoSuchModelException(e);
        }

        Collections.reverse(msgs);
        return msgs;
    }

    private Message buildFromCursor(Cursor c) throws InvalidCursorException {
        if (c == null) {
            throw new InvalidCursorException("Cursor is null.");
        }

        if (!c.moveToFirst()) {
            throw new InvalidCursorException("Cannot move to first element of the cursor.");
        }

        try {
            int chatId = c.getInt(c.getColumnIndexOrThrow(Columns.Message.COLUMN_NAME_CHAT_ID));
            String text = c.getString(c.getColumnIndexOrThrow(Columns.Message.COLUMN_NAME_MSG));
            byte[] senderPublicKey = c.getBlob(c.getColumnIndexOrThrow(Columns.Message.COLUMN_NAME_SENDER_PUBLIC_KEY));
            long dateTime = c.getLong(c.getColumnIndexOrThrow(Columns.Message.COLUMN_NAME_DATETIME));

            Message message = new Message(chatId, text, senderPublicKey, dateTime);
            message.setId(c.getLong(c.getColumnIndexOrThrow(Columns.Message._ID)));

            return message;

        } catch (CursorIndexOutOfBoundsException e) {
            throw new InvalidCursorException(e);
        }
    }

    /**
     * @param c      the cursor containing our messages
     * @param number the number of messages to retrieve, -1 for all
     * @return a list of messages
     * @throws InvalidCursorException
     */
    private List<Message> buildListFromCursor(Cursor c, int number) throws InvalidCursorException {
        List<Message> result = new ArrayList<Message>();
        int i = 0;
        do {
            result.add(buildFromCursor(c));
            i++;
        } while (c.moveToNext() && (number == -1 || i < number));

        return result;
    }

    private ContentValues getValuesForMessage(Message message) {
        ContentValues values = new ContentValues();

        values.put(Columns.Message.COLUMN_NAME_CHAT_ID, message.getChatId());
        values.put(Columns.Message.COLUMN_NAME_MSG, message.getText());
        values.put(Columns.Message.COLUMN_NAME_SENDER_PUBLIC_KEY, message.getSenderPublicKey());
        values.put(Columns.Message.COLUMN_NAME_DATETIME, message.getDateTime());

        return values;
    }
}
