package ch.tarsier.tarsier.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by McMoudi on 24/10/14.
 */
public class ChatsDBHelper extends SQLiteOpenHelper{

    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "tarsier.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";


    private static final String SQL_CREATE_CHATROOMS = "CREATE TABLE " + ChatsContract.ChatRooms.TABLE_NAME + " (" +
            ChatsContract.ChatRooms._ID + " INTEGER PRIMARY KEY," +
            ChatsContract.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            ChatsContract.ChatRooms.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            ChatsContract.ChatRooms.COLUMN_NAME_HOST + TEXT_TYPE + COMMA_SEP + " )";


    private static final String SQL_CREATE_CHATS = "CREATE TABLE " + ChatsContract.Chats.TABLE_NAME + " (" +
            ChatsContract.Chats._ID + " INTEGER PRIMARY KEY," +
            ChatsContract.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            ChatsContract.Chats.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            ChatsContract.Chats.COLUMN_NAME_HOST + TEXT_TYPE + COMMA_SEP + " )";

    private static final String SQL_CREATE_MESSAGES = "CREATE TABLE" + ChatsContract.Messages.TABLE_NAME + " (" +
            ChatsContract.Messages._ID + " INTEGER PRIMARY KEY," +
            ChatsContract.Messages.COLUMN_NAME_MSG + TEXT_TYPE + COMMA_SEP +
            ChatsContract.Messages.COLUMN_NAME_TIME +TEXT_TYPE + COMMA_SEP +
            ChatsContract.Messages.COLUMN_NAME_SENDER + TEXT_TYPE + COMMA_SEP + " )";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ChatsContract.ChatRooms.TABLE_NAME;




    public ChatsDBHelper(Context context){
    super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CHATROOMS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //FIXME seems to me there ain't anything to do down here, as we shan't modify our architecture (for now?)
        //this method migth be the one which should be used for new chats, not sure
    }








}
