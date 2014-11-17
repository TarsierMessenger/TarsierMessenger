package ch.tarsier.tarsier.domain.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.exception.NoSuchModelException;

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

    public Chat findById(long id) throws NoSuchModelException, InvalidCursorException {
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

        return fromCursor(cursor);
    }

    public void insert(Chat Chat) {
        // TODO: Implement Chat.insert()
    }

    public void update(Chat Chat) {
        // TODO: Implement Chat.update()
    }

    public void delete(Chat Chat) {
        // TODO: Implement Chat.delete()
    }

    private Chat fromCursor(Cursor c) {
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
    }


}
