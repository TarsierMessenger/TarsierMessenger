package ch.tarsier.tarsier.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author McMoudi
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "tarsier.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String DATETIME_TYPE = "INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String ID_TYPE = "INTEGER";
    private static final String CREATE_TABLE = "CREATE TABLE ";


    private static final String SQL_CREATE_CHATROOMS =
              CREATE_TABLE + Columns.Discussion.TABLE_NAME
            + " (" + Columns.Discussion._ID + Columns.Discussion.COLUMN_NAME_CHAT_ID
            + " INTEGER PRIMARY KEY," + TEXT_TYPE + COMMA_SEP + Columns.Discussion.COLUMN_NAME_TITLE
            + TEXT_TYPE + COMMA_SEP + Columns.Discussion.COLUMN_NAME_HOST + TEXT_TYPE + " )";

    private static final String SQL_CREATE_MESSAGES =
              CREATE_TABLE + Columns.Message.TABLE_NAME
            + Columns.Message._ID + " (" + " INTEGER PRIMARY KEY," + Columns.Message.COLUMN_NAME_MSG
            + TEXT_TYPE + COMMA_SEP + Columns.Message.COLUMN_NAME_DATETIME + DATETIME_TYPE
            + COMMA_SEP + Columns.Message.COLUMN_NAME_SENDER_ID + TEXT_TYPE + COMMA_SEP
            + Columns.Message.COLUMN_NAME_CHAT_ID + " )";

    private static final String SQL_DELETE_CHATROOMS =
        "DROP TABLE IF EXISTS " + Columns.Discussion.TABLE_NAME;

    private static final String SQL_DELETE_MESSAGES =
        "DROP TABLE IF EXISTS " + Columns.Message.TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CHATROOMS);
        db.execSQL(SQL_CREATE_MESSAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // seems to me there ain't anything to do down here, as we shan't modify our architecture (for now?)
        // this method might be the one which should be used for new chats, not sure
    }


}
