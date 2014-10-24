package ch.tarsier.tarsier.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by McMoudi
 */
public class StorageAccess {

    private ChatsDBHelper cDBHelper;
    private SQLiteDatabase readableDB;
    private SQLiteDatabase writableDB;

    public StorageAccess(Context context) {
        cDBHelper = new ChatsDBHelper(context);
        new DatabaseAccess().execute();
    }

    private SQLiteDatabase getDB() {
        return null;//FIXME
    }

    public ArrayList<Chat> getChats(boolean wantChatrooms) {

        ArrayList<Chat> chatRooms = new ArrayList<Chat>();
        String[] projection = new String[3];
        String table;
        String sortOrder;
        if (wantChatrooms) {
            table = ChatsContract.ChatRooms.TABLE_NAME;
            projection[0] = ChatsContract.ChatRooms._ID;
            projection[1] = ChatsContract.ChatRooms.COLUMN_NAME_TITLE;
            projection[2] = ChatsContract.ChatRooms.COLUMN_NAME_HOST;
            sortOrder = ChatsContract.ChatRooms.COLUMN_NAME_TITLE + "DESC";

        } else {
            table = ChatsContract.Chats.TABLE_NAME;
            projection[0] = ChatsContract.Chats._ID;
            projection[1] = ChatsContract.Chats.COLUMN_NAME_TITLE;
            projection[2] = ChatsContract.Chats.COLUMN_NAME_HOST;
            sortOrder = ChatsContract.Chats.COLUMN_NAME_TITLE + "DESC";
        }

        //The cursor is full of my data, I now need to extract it in a more conventional form
        Cursor c = readableDB.query(table, projection, null, null, null, null, sortOrder);
        c.moveToFirst();

        //FIXME column indexes kind of hardcoded ( no use of 'wantchatrooms)
        do {
            chatRooms.add(new Chat(c.getInt(c.getColumnIndex(ChatsContract.ChatRooms._ID)), c.getString(c.getColumnIndex(ChatsContract.ChatRooms.COLUMN_NAME_TITLE)), c.getString(c.getColumnIndex(ChatsContract.ChatRooms.COLUMN_NAME_HOST))));
        } while (c.moveToNext());


        return chatRooms;
    }

    private class DatabaseAccess extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            readableDB = cDBHelper.getReadableDatabase();
            writableDB = cDBHelper.getWritableDatabase();
            return null;
        }

    }
}
