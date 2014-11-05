package ch.tarsier.tarsier.storage;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;

import ch.tarsier.tarsier.R;

/**
 * Created by McMoudi
 */
public class StorageAccess {

    private ChatsDBHelper mCDBHelper;
    private SQLiteDatabase mReadableDB;
    private SQLiteDatabase mWritableDB;
    private boolean mIsReady;
    private Context mContext;

    public StorageAccess(Context context) {
        mCDBHelper = new ChatsDBHelper(context);
        mIsReady = false;
        mContext = context;
        new DatabaseAccess().execute();
    }


    public ArrayList<Chat> getChats(boolean wantChatrooms) {
        isReady();


        ArrayList<Chat> chatRooms = new ArrayList<Chat>();
        String[] projection = {ChatsContract.Discussion._ID, ChatsContract.Discussion.COLUMN_NAME_TITLE, ChatsContract.Discussion.COLUMN_NAME_HOST};
        String table;
        String sortOrder;

        table = ChatsContract.Discussion.TABLE_NAME;

        sortOrder = ChatsContract.Discussion.COLUMN_NAME_TITLE + "DESC";

        Cursor c = mReadableDB.query(table, projection, null, null, null, null, sortOrder);

        //The cursor is full of my data,c.getString(c.getColumnIndex(ChatsContract.Discussion.COLUMN_NAME_TITLE)),  I now need to extract it in a more conventional form
        c.moveToFirst();
        do {
            if (wantChatrooms) {
                chatRooms.add(new Chat(c.getInt(c.getColumnIndex(ChatsContract.Discussion._ID)), c.getString(c.getColumnIndex(ChatsContract.Discussion.COLUMN_NAME_TITLE)), c.getString(c.getColumnIndex(ChatsContract.Discussion.COLUMN_NAME_HOST))));
            } else {
                chatRooms.add(new Chat(c.getInt(c.getColumnIndex(ChatsContract.Discussion._ID)), c.getString(c.getColumnIndex(ChatsContract.Discussion.COLUMN_NAME_HOST))));
            }
        } while (c.moveToNext());


        return chatRooms;
    }

    private Cursor getMsg(int id) {
        isReady();
        String sortOrder = ChatsContract.Message.COLUMN_NAME_DATETIME + "ASC";

        String[] projection = {ChatsContract.Message._ID, ChatsContract.Message.COLUMN_NAME_MSG, ChatsContract.Message.COLUMN_NAME_DATETIME, ChatsContract.Message.COLUMN_NAME_SENDER};


        //got to do a SQL "WHERE", as projectionArgs, to select the messages with the id @id
        String selection = ChatsContract.Message.COLUMN_NAME_CHATID + " = '" + id + "'";

        return mReadableDB.query(ChatsContract.Message.TABLE_NAME, projection, selection, null, null, null, sortOrder);
    }

    //FIXME fix this ID thingy, once we got an ID to rule them all

    /**
     * @param chatID
     * @param messagesRetrieved number of messages retrieved, starting from latest, 0 means all
     * @return an ArrayList of Message
     */
    public ArrayList<Message> getMessages(int chatID, int messagesRetrieved) {
        ArrayList<Message> messages = new ArrayList<Message>();

        Cursor c = getMsg(chatID);
        c.moveToLast();


        do {
            String sender = c.getString(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_SENDER));
            messages.add(new Message(c.getInt(c.getColumnIndex(ChatsContract.Message._ID)), c.getInt(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_CHATID)), c.getString(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_MSG)), sender, c.getLong(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_DATETIME)), (sender == getMyId())));
        } while (c.moveToPrevious());

        return messages;
    }

    public Message getLastMessage(int id) {
        Cursor c = getMsg(id);
        c.moveToLast();

        String sender = c.getString(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_SENDER));

        return new Message(c.getInt(c.getColumnIndex(ChatsContract.Message._ID)), c.getInt(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_CHATID)), c.getString(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_MSG)), sender, c.getLong(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_DATETIME)), (sender == getMyId()));

    }

    public void setMessage(Message msg) {
        isReady();
        ContentValues values = new ContentValues();

        values.put(ChatsContract.Message.COLUMN_NAME_CHATID, msg.getChatID());
        values.put(ChatsContract.Message.COLUMN_NAME_MSG, msg.getContent());
        values.put(ChatsContract.Message.COLUMN_NAME_SENDER, msg.getAuthor());
        values.put(ChatsContract.Message.COLUMN_NAME_DATETIME, msg.getDateTime());

        mWritableDB.insert(ChatsContract.Message.TABLE_NAME, null, values);

    }

    public void setChatroom(Chat chat) {
        isReady();
        ContentValues values = new ContentValues();

        values.put(ChatsContract.Discussion.COLUMN_NAME_TITLE, chat.getTitle());
        values.put(ChatsContract.Discussion.COLUMN_NAME_HOST, chat.getHost());
        values.put(ChatsContract.Discussion.COLUMN_NAME_TYPE, chat.isPrivate());

        mWritableDB.insert(ChatsContract.Discussion.TABLE_NAME, null, values);
    }

    private void isReady() { while (!mIsReady) {} }

    private String getMyPersonalSharedPref(int key) {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mContext.getString(R.string.personnal_file_key), mContext.MODE_PRIVATE);

        return sharedPref.getString(mContext.getString(key), "");

    }

    public String getMyId() { return getMyPersonalSharedPref(R.string.personnal_file_key_myid); }
    public String getMyUsername() { return getMyPersonalSharedPref(R.string.personnal_file_key_myusername); }
    public String getMyMood() { return getMyPersonalSharedPref(R.string.personnal_file_key_mymood); }

    private void setMyPersonalSharedPref(int key, String data) {

        SharedPreferences sharedPref = mContext.getSharedPreferences(mContext.getString(R.string.personnal_file_key), mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mContext.getString(key), data);
        editor.commit();

    }

    public void setMyId(String id){ setMyPersonalSharedPref(R.string.personnal_file_key_myid, id); }
    public void setMyUsername(String username){ setMyPersonalSharedPref(R.string.personnal_file_key_myusername,username);}
    public void setMyMood(String mood){ setMyPersonalSharedPref(R.string.personnal_file_key_mymood, mood);}

    private class DatabaseAccess extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            mReadableDB = mCDBHelper.getReadableDatabase();
            mWritableDB = mCDBHelper.getWritableDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mIsReady = true;
        }

    }
}
