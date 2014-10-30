package ch.tarsier.tarsier.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

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


    public ArrayList<Chat> getChats(boolean wantChatrooms) {

        ArrayList<Chat> chatRooms = new ArrayList<Chat>();
        String[] projection = {ChatsContract.ChatRooms._ID, ChatsContract.ChatRooms.COLUMN_NAME_TITLE, ChatsContract.ChatRooms.COLUMN_NAME_HOST};
        String table;
        String sortOrder;

            table = ChatsContract.ChatRooms.TABLE_NAME;

            sortOrder = ChatsContract.ChatRooms.COLUMN_NAME_TITLE + "DESC";


        //TODO implement the new DB style, chats & chatrooms being in the same table from now on.

        Cursor c = readableDB.query(table, projection, null, null, null, null, sortOrder);

        //The cursor is full of my data, I now need to extract it in a more conventional form
        c.moveToFirst();
        do {
            chatRooms.add(new Chat(c.getInt(c.getColumnIndex(ChatsContract.ChatRooms._ID)), c.getString(c.getColumnIndex(ChatsContract.ChatRooms.COLUMN_NAME_TITLE)), c.getString(c.getColumnIndex(ChatsContract.ChatRooms.COLUMN_NAME_HOST)),wantChatrooms));
        } while (c.moveToNext());


        return chatRooms;
    }

    private Cursor getMsg(int id) {

        String sortOrder = ChatsContract.Messages.COLUMN_NAME_DATETIME + "ASC";

        String[] projection = {ChatsContract.Messages._ID, ChatsContract.Messages.COLUMN_NAME_MSG, ChatsContract.Messages.COLUMN_NAME_DATETIME, ChatsContract.Messages.COLUMN_NAME_SENDER};


        //got to do a SQL "WHERE", as projectionArgs, to select the messages with the id @id
        String selection = ChatsContract.Messages.COLUMN_NAME_CHATID + " = '" + id + "'";

        return readableDB.query(ChatsContract.Messages.TABLE_NAME, projection, selection, null, null, null, sortOrder);
    }

    //TODO fix this ID thingy, once we got an ID to rule them all
    public ArrayList<Message> getMessages(int chatID) {
        ArrayList<Message> messages = new ArrayList<Message>();


        Cursor c = getMsg(chatID);
        c.moveToFirst();

        do {
            messages.add(new Message(c.getInt(c.getColumnIndex(ChatsContract.Messages._ID)), c.getInt(c.getColumnIndex(ChatsContract.Messages.COLUMN_NAME_CHATID)), c.getString(c.getColumnIndex(ChatsContract.Messages.COLUMN_NAME_MSG)), c.getString(c.getColumnIndex(ChatsContract.Messages.COLUMN_NAME_SENDER)),c.getLong(c.getColumnIndex(ChatsContract.Messages.COLUMN_NAME_DATETIME))));
        } while (c.moveToNext());

        return messages;
    }

    public Message getLastMessage(int id) {

        Cursor c = getMsg(id);
        c.moveToLast();

        return new Message(c.getInt(c.getColumnIndex(ChatsContract.Messages._ID)), c.getInt(c.getColumnIndex(ChatsContract.Messages.COLUMN_NAME_CHATID)), c.getString(c.getColumnIndex(ChatsContract.Messages.COLUMN_NAME_MSG)), c.getString(c.getColumnIndex(ChatsContract.Messages.COLUMN_NAME_SENDER)), c.getLong(c.getColumnIndex(ChatsContract.Messages.COLUMN_NAME_DATETIME)));
    }

    public void setMessage(Message msg) { //TODO setMessage(msg)
        ContentValues values = new ContentValues();

        values.put(ChatsContract.Messages.COLUMN_NAME_CHATID,msg.getChatID());
        values.put(ChatsContract.Messages.COLUMN_NAME_MSG,msg.getContent());
        values.put(ChatsContract.Messages.COLUMN_NAME_SENDER, msg.getAuthor());
        values.put(ChatsContract.Messages.COLUMN_NAME_DATETIME,msg.getDateTime());



    }

    public void setChatroom(Chat chat) {
        ContentValues values = new ContentValues();

        values.put(ChatsContract.ChatRooms.COLUMN_NAME_TITLE,chat.getTitle());
        values.put(ChatsContract.ChatRooms.COLUMN_NAME_HOST,chat.getHost());
        values.put(ChatsContract.ChatRooms.COLUMN_NAME_TYPE,chat.isGroup());
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
