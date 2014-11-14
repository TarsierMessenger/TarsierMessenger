package ch.tarsier.tarsier.storage;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;

/**
 * Created by McMoudi
 */
public class StorageAccess {

    private ChatsDBHelper mCDBHelper;
    private SQLiteDatabase mReadableDB;
    private SQLiteDatabase mWritableDB;
    private boolean mIsReady;

    public StorageAccess(Context context) {
        this(new ChatsDBHelper(context));
    }

    public StorageAccess(ChatsDBHelper chatsDBHelper) {
        mCDBHelper = chatsDBHelper;
        mIsReady = false;

        new DatabaseAccess().execute();
    }

    public ArrayList<Chat> getChats(boolean wantChatrooms) {
        isReady();

        ArrayList<Chat> chatRooms = new ArrayList<Chat>();
        String[] projection = {
            ChatsContract.Discussion._ID,
            ChatsContract.Discussion.COLUMN_NAME_TITLE,
            ChatsContract.Discussion.COLUMN_NAME_HOST
        };
        String table;
        String sortOrder;

        table = ChatsContract.Discussion.TABLE_NAME;

        sortOrder = ChatsContract.Discussion.COLUMN_NAME_TITLE + "DESC";

        Cursor c = mReadableDB.query(table, projection, null, null, null, null, sortOrder);

        //The cursor is full of my data,c.getString(c.getColumnIndex(ChatsContract.Discussion.COLUMN_NAME_TITLE)),  I now need to extract it in a more conventional form
        c.moveToFirst();
        do {
            if (wantChatrooms) {
                chatRooms.add(new Chat(c.getInt(c.getColumnIndex(ChatsContract.Discussion._ID)),
                        c.getString(c.getColumnIndex(ChatsContract.Discussion.COLUMN_NAME_TITLE)),
                        c.getString(c.getColumnIndex(ChatsContract.Discussion.COLUMN_NAME_HOST))));
            } else {
                chatRooms.add(new Chat(c.getInt(c.getColumnIndex(ChatsContract.Discussion._ID)),
                        c.getString(c.getColumnIndex(ChatsContract.Discussion.COLUMN_NAME_HOST))));
            }
        } while (c.moveToNext());


        return chatRooms;
    }

    private Cursor getMsg(int id) {
        isReady();
        String sortOrder = ChatsContract.Message.COLUMN_NAME_DATETIME + "ASC";

        String[] projection = {
            ChatsContract.Message._ID,
            ChatsContract.Message.COLUMN_NAME_MSG,
            ChatsContract.Message.COLUMN_NAME_DATETIME,
            ChatsContract.Message.COLUMN_NAME_SENDER_ID
        };

        //got to do a SQL "WHERE", as projectionArgs, to select the messages with the id @id
        String selection = ChatsContract.Message.COLUMN_NAME_CHAT_ID + " = '" + id + "'";

        return mReadableDB.query(ChatsContract.Message.TABLE_NAME, projection, selection,
                null, null, null, sortOrder);
    }

    //FIXME fix this ID thingy, once we got an ID to rule them all

    /**
     * @param chatID the id of the discussion in which we want to retrieve the messages
     * @param messagesToFetch number of messages retrieved, starting from latest, 0 means all
     * @return an ArrayList of Message
     */
    public ArrayList<Message> getMessages(int chatID, int messagesToFetch) {
        if (messagesToFetch < -1) {
            //FIXME @xawill : Should not throw an exception. Just ignore the call (do nothing, return null)
            throw new IllegalArgumentException("gimme some positive int for getMessages!");
        }

        ArrayList<Message> messages = new ArrayList<Message>();

        Cursor c = getMsg(chatID);
        c.moveToLast();

        int i = 0;
        do {
            int senderId = c.getInt(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_SENDER_ID));
            messages.add(new Message(
                    c.getInt(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_CHAT_ID)),
                    c.getString(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_MSG)),
                    senderId,
                    c.getLong(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_DATETIME))));
            i++;
        } while (c.moveToPrevious() && (messagesToFetch == -1 || i < messagesToFetch));

        return messages;
    }

    /**
     * //TODO
     *
     * @param messagesToFetch the number of elements to return
     * @param timestamp the time from which we shall return messages
     * @return the messagesToFetch before timestamp, if it can, Can return arraylist with  < messagesToFetch elements
     */
    public ArrayList<Message> getMessages(int chatID, int messagesToFetch, long timestamp) { //TODO
        ArrayList<Message> messages = new ArrayList<Message>();
        Cursor c = getMsg(chatID);

        c.moveToLast();
        while (c.moveToPrevious() && (timestamp < c.getInt(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_DATETIME))));

        int i = 0;
        if (c.getPosition() >= 0) {
            do {
                int senderId = c.getInt(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_SENDER_ID));
                messages.add(new Message(
                        c.getInt(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_CHAT_ID)),
                        c.getString(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_MSG)),
                        senderId,
                        c.getLong(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_DATETIME))));
                i++;
            } while (c.moveToPrevious() && (messagesToFetch == -1 || i < messagesToFetch));
        }

        return messages;
    }


    public Message getLastMessage(int chatID) {
        Cursor c = getMsg(chatID);
        c.moveToLast();

        int senderID = c.getInt(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_SENDER_ID));

        return new Message(c.getInt(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_CHAT_ID)),
                c.getString(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_MSG)),
                senderID,
                c.getLong(c.getColumnIndex(ChatsContract.Message.COLUMN_NAME_DATETIME)));
    }

    public void addMessage(Message msg) {
        isReady();
        ContentValues values = new ContentValues();

        values.put(ChatsContract.Message.COLUMN_NAME_CHAT_ID, msg.getChatID());
        values.put(ChatsContract.Message.COLUMN_NAME_MSG, msg.getText());
        values.put(ChatsContract.Message.COLUMN_NAME_SENDER_ID, msg.getAuthor());
        values.put(ChatsContract.Message.COLUMN_NAME_DATETIME, msg.getDateTime());

        mWritableDB.insert(ChatsContract.Message.TABLE_NAME, null, values);

    }

    public void addChatroom(Chat chat) {
        isReady();
        ContentValues values = new ContentValues();

        values.put(ChatsContract.Discussion.COLUMN_NAME_TITLE, chat.getTitle());
        values.put(ChatsContract.Discussion.COLUMN_NAME_HOST, chat.getHost());
        values.put(ChatsContract.Discussion.COLUMN_NAME_TYPE, chat.isPrivate());

        mWritableDB.insert(ChatsContract.Discussion.TABLE_NAME, null, values);
    }

    public Peer getPeer(int peerId) {
        // TODO implement
        return null;
    }

    private void isReady() {
        while (!mIsReady) {
        }
    }

    private String getMyPersonalSharedPref(int key) {
        SharedPreferences sharedPref = Tarsier.app().getSharedPreferences(
                Tarsier.app().getString(R.string.personnal_file_key), Context.MODE_PRIVATE);

        return sharedPref.getString(Tarsier.app().getString(key), "");
    }

    public int getMyId() {
        return Integer.parseInt(getMyPersonalSharedPref(R.string.personnal_file_key_myid));
    }

    public String getMyUsername() {
        return getMyPersonalSharedPref(R.string.personnal_file_key_myusername);
    }

    public String getMyMood() {
        return getMyPersonalSharedPref(R.string.personnal_file_key_mymood);
    }

    private void setMyPersonalSharedPref(int key, String data) {
        SharedPreferences sharedPref = Tarsier.app().getSharedPreferences(
                Tarsier.app().getString(R.string.personnal_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Tarsier.app().getString(key), data);
        editor.apply();
    }

    public void setMyId(String id) {
        setMyPersonalSharedPref(R.string.personnal_file_key_myid, id);
    }

    public void setMyUsername(String username) {
        setMyPersonalSharedPref(R.string.personnal_file_key_myusername, username);
    }

    public void setMyMood(String mood) {
        setMyPersonalSharedPref(R.string.personnal_file_key_mymood, mood);
    }

    private String getMyPreferences(int key) {
        SharedPreferences sharedPref = Tarsier.app().getSharedPreferences(
                Tarsier.app().getString(R.string.personnal_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(Tarsier.app().getString(key), "");
    }

    //FIXME once the database in encrypted, if it is one day
    public String hasPassword() {
        return "";
    }

    public String getBackground() {
        return getMyPreferences(R.string.preferences_file_key_background);
    }

    public String getSound() {
        return getMyPreferences(R.string.preferences_file_key_sound);
    }

    public String getVibration() {
        return getMyPreferences(R.string.preferences_file_key_vibration);
    }

    /**
     * AsyncTask to access the database on a different thread
     */
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
