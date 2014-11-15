package ch.tarsier.tarsier.storage;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.ArrayList;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.database.Columns;
import ch.tarsier.tarsier.database.DatabaseHelper;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.PeerId;

/**
 * @author McMoudi
 */
public class StorageAccess {

    private DatabaseHelper mCDBHelper;
    private SQLiteDatabase mReadableDB;
    private SQLiteDatabase mWritableDB;
    private boolean mIsReady;

    public StorageAccess(Context context) {
        this(new DatabaseHelper(context));
    }

    public StorageAccess(DatabaseHelper databaseHelper) {
        mCDBHelper = databaseHelper;
        mIsReady = false;

        new DatabaseAccess().execute();
    }

    /*
    public ArrayList<Chat> getChats(boolean wantChatrooms) {
        isReady();

        ArrayList<Chat> chatRooms = new ArrayList<Chat>();
        String[] projection = {
            Columns.Discussion._ID,
            Columns.Discussion.COLUMN_NAME_TITLE,
            Columns.Discussion.COLUMN_NAME_HOST
        };
        String table;
        String sortOrder;

        table = Columns.Discussion.TABLE_NAME;

        sortOrder = Columns.Discussion.COLUMN_NAME_TITLE + "DESC";

        Cursor c = mReadableDB.query(table, projection, null, null, null, null, sortOrder);

        //The cursor is full of my data,c.getString(c.getColumnIndex(Columns.Discussion.COLUMN_NAME_TITLE)),  I now need to extract it in a more conventional form
        c.moveToFirst();
        do {
            if (wantChatrooms) {
                chatRooms.add(new Chat(c.getInt(c.getColumnIndex(Columns.Discussion._ID)),
                        c.getString(c.getColumnIndex(Columns.Discussion.COLUMN_NAME_TITLE)),
                        c.getString(c.getColumnIndex(Columns.Discussion.COLUMN_NAME_HOST))));
            } else {
                chatRooms.add(new Chat(c.getInt(c.getColumnIndex(Columns.Discussion._ID)),
                        c.getString(c.getColumnIndex(Columns.Discussion.COLUMN_NAME_HOST))));
            }
        } while (c.moveToNext());


        return chatRooms;
    }
    */

    private Cursor getMsg(int id) {
        isReady();
        String sortOrder = Columns.Message.COLUMN_NAME_DATETIME + "ASC";

        String[] projection = new String[] {
            Columns.Message._ID,
            Columns.Message.COLUMN_NAME_MSG,
            Columns.Message.COLUMN_NAME_DATETIME,
            Columns.Message.COLUMN_NAME_SENDER_ID
        };

        //got to do a SQL "WHERE", as projectionArgs, to select the messages with the id @id
        String selection = Columns.Message.COLUMN_NAME_CHAT_ID + " = '" + id + "'";

        return mReadableDB.query(Columns.Message.TABLE_NAME, projection, selection,
                null, null, null, sortOrder);
    }

    private Cursor getPeerCursor(byte[] id) {
        isReady();

        String sortOrder = Columns.Message.COLUMN_NAME_DATETIME + "ASC";

        String[] projection = new String[] {
                Columns.Peer._ID,
                Columns.Peer.COLUMN_NAME_USERNAME,
                Columns.Peer.COLUMN_NAME_PUBLIC_KEY,
                Columns.Peer.COLUMN_NAME_PICTURE_PATH
        };

        // got to do a SQL "WHERE", as projectionArgs, to select the messages with the id @id
        // FIXME: The following won't work - romac
        String selection = Columns.Peer.COLUMN_NAME_PUBLIC_KEY + " = '" + id + "'";

        return mReadableDB.query(Columns.Peer.TABLE_NAME, projection, selection,
                null, null, null, sortOrder);
    }

    /**
     * Retrieves a peer from its public key
     *
     * @param peerId the peer's public key
     * @return a Peer
     */
    public Peer getPeer(byte[] peerId) {
        Cursor c = getPeerCursor(peerId);
        c.moveToFirst();

        // Bitmap picture = getPeerPicture(c.getString(c.getColumnIndex(Columns.Peer.COLUMN_NAME_PICTURE_PATH)));
        byte[] publicKey = c.getBlob(c.getColumnIndex(Columns.Peer.COLUMN_NAME_PUBLIC_KEY));
        PeerId peerIdObj = new PeerId(publicKey);

        return new Peer(
            c.getString(c.getColumnIndex(Columns.Peer.COLUMN_NAME_USERNAME)),
            peerIdObj
        );
    }

    /*
    public void addPeer(Peer peer) {
        isReady();
        ContentValues values = new ContentValues();

        values.put(Columns.Peer.COLUMN_NAME_USERNAME, peer.getName());
        values.put(Columns.Peer.COLUMN_NAME_PUBLIC_KEY, peer.getPublicKey());
        // values.put(Columns.Peer.COLUMN_NAME_PICTURE_PATH, addPeerPicture(peer.getPicture()));

        mWritableDB.insert(Columns.Peer.TABLE_NAME, null, values);
    }
    */


    //FIXME fix this ID thingy, once we got an ID to rule them all

    /**
     * @param chatID the id of the discussion in which we want to retrieve the messages
     * @param messagesToFetch number of messages retrieved, starting from latest, 0 means all
     * @return an ArrayList of Message
     */
    /*
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
            PeerId peerId = new PeerId(c.getBlob(
                c.getColumnIndex(Columns.Message.COLUMN_NAME_SENDER_ID))
            );

            messages.add(new Message(
                    c.getInt(c.getColumnIndex(Columns.Message.COLUMN_NAME_CHAT_ID)),
                    c.getString(c.getColumnIndex(Columns.Message.COLUMN_NAME_MSG)),
                    peerId,
                    c.getLong(c.getColumnIndex(Columns.Message.COLUMN_NAME_DATETIME))));
            i++;
        } while (c.moveToPrevious() && (messagesToFetch == -1 || i < messagesToFetch));

        return messages;
    }
    */

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
        while (c.moveToPrevious() && (timestamp < c.getInt(c.getColumnIndex(Columns.Message.COLUMN_NAME_DATETIME))));

        int i = 0;
        if (c.getPosition() >= 0) {
            do {
                PeerId peerId = new PeerId(c.getBlob(
                        c.getColumnIndex(Columns.Message.COLUMN_NAME_SENDER_ID))
                );

                messages.add(new Message(
                        c.getInt(c.getColumnIndex(Columns.Message.COLUMN_NAME_CHAT_ID)),
                        c.getString(c.getColumnIndex(Columns.Message.COLUMN_NAME_MSG)),
                        peerId,
                        c.getLong(c.getColumnIndex(Columns.Message.COLUMN_NAME_DATETIME))));
                i++;
            } while (c.moveToPrevious() && (messagesToFetch == -1 || i < messagesToFetch));
        }

        return messages;
    }


    /*
    public Message getLastMessage(int chatID) {
        Cursor c = getMsg(chatID);
        c.moveToLast();

        PeerId peerId = new PeerId(c.getBlob(
                c.getColumnIndex(Columns.Message.COLUMN_NAME_SENDER_ID))
        );

        return new Message(c.getInt(c.getColumnIndex(Columns.Message.COLUMN_NAME_CHAT_ID)),
                c.getString(c.getColumnIndex(Columns.Message.COLUMN_NAME_MSG)),
                peerId,
                c.getLong(c.getColumnIndex(Columns.Message.COLUMN_NAME_DATETIME)));
    }
    */

    public void addMessage(Message msg) {
        isReady();
        ContentValues values = new ContentValues();

        values.put(Columns.Message.COLUMN_NAME_CHAT_ID, msg.getChatID());
        values.put(Columns.Message.COLUMN_NAME_MSG, msg.getText());
        values.put(Columns.Message.COLUMN_NAME_SENDER_ID, msg.getPeerId().getBytes());
        values.put(Columns.Message.COLUMN_NAME_DATETIME, msg.getDateTime());

        mWritableDB.insert(Columns.Message.TABLE_NAME, null, values);

    }

    /*
    public void addChatroom(Chat chat) {
        isReady();
        ContentValues values = new ContentValues();

        values.put(Columns.Discussion.COLUMN_NAME_TITLE, chat.getTitle());
        values.put(Columns.Discussion.COLUMN_NAME_HOST, chat.getHost());
        values.put(Columns.Discussion.COLUMN_NAME_TYPE, chat.isPrivate());

        mWritableDB.insert(Columns.Discussion.TABLE_NAME, null, values);
    }
    */

    private void isReady() {
        while (!mIsReady) {
        }
    }

    private String getMyPersonalSharedPref(int key) {
        SharedPreferences sharedPref = Tarsier.app().getSharedPreferences(
                Tarsier.app().getString(R.string.personnal_file_key), Context.MODE_PRIVATE);

        return sharedPref.getString(Tarsier.app().getString(key), "");
    }

    /*
    public int getMyId() {
        return Integer.parseInt(getMyPersonalSharedPref(R.string.personnal_file_key_myid));
    }

    public String getMyUsername() {
        return getMyPersonalSharedPref(R.string.personnal_file_key_myusername);
    }

    public String getMyMood() {
        return getMyPersonalSharedPref(R.string.personnal_file_key_mymood);
    }
    */

    private void setMyPersonalSharedPref(int key, String data) {
        SharedPreferences sharedPref = Tarsier.app().getSharedPreferences(
                Tarsier.app().getString(R.string.personnal_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Tarsier.app().getString(key), data);
        editor.apply();
    }

    /*
    public void setMyId(String id) {
        setMyPersonalSharedPref(R.string.personnal_file_key_myid, id);
    }

    public void setMyUsername(String username) {
        setMyPersonalSharedPref(R.string.personnal_file_key_myusername, username);
    }

    public void setMyMood(String mood) {
        setMyPersonalSharedPref(R.string.personnal_file_key_mymood, mood);
    }
    */

    public String getMyPicturePath() {
        return Tarsier.app().getUserPreferences().getPicturePath();
    }

    public Uri getMyPictureUri() {
        return Uri.parse(getMyPicturePath());
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
