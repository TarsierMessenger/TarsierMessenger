package ch.tarsier.tarsier.domain.repository;

import android.database.Cursor;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.model.Message;

/**
 * @author gluthier
 */
public class MessageRepository extends AbstractRepository {

    private static final String TABLE_NAME = Columns.Message.TABLE_NAME;

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

    public Message findById(long id) {
        String selection = Columns.Message.COLUMN_NAME_CHAT_ID + " = " + id;

        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                COLUMNS,
                selection,
                null, null, null, null
        );

        return buildFromCursor(cursor);
    }

    public void insert(Message message) {
        //TODO
    }

    public void update(Message message) {
        //TODO
    }

    public void delete(Message message) {
        //TODO
    }

    private Message buildFromCursor(Cursor c) {
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
}
