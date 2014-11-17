package ch.tarsier.tarsier.database;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ch.tarsier.tarsier.Tarsier;

/**
 * @author McMoudi
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "tarsier.db";

    private static final String ID_TYPE = "INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String PUBLIC_KEY_TYPE = " BLOB";
    private static final String BOOLEAN_TYPE = "INTEGER"; // SQLite doesn't have a boolean type
    private static final String DATETIME_TYPE = "INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String CREATE_TABLE = "CREATE TABLE ";

    private static final String SQL_CREATE_CHAT =
              CREATE_TABLE + " " + Columns.Chat.TABLE_NAME + " (" + "\n"
            + Columns.Chat._ID + " " + ID_TYPE + " PRIMARY KEY" + COMMA_SEP + "\n"
            + Columns.Chat.COLUMN_NAME_TITLE + " " + TEXT_TYPE + COMMA_SEP + "\n"
            + Columns.Chat.COLUMN_NAME_HOST_ID + ID_TYPE + COMMA_SEP + "\n"
            + Columns.Chat.COLUMN_NAME_IS_PRIVATE + BOOLEAN_TYPE + "\n"
            + " )";

    private static final String SQL_CREATE_MESSAGES =
              CREATE_TABLE + Columns.Message.TABLE_NAME
            + Columns.Message._ID + " (" + " INTEGER PRIMARY KEY," + Columns.Message.COLUMN_NAME_MSG
            + TEXT_TYPE + COMMA_SEP + Columns.Message.COLUMN_NAME_DATETIME + DATETIME_TYPE
            + COMMA_SEP + Columns.Message.COLUMN_NAME_SENDER_ID + TEXT_TYPE + COMMA_SEP
            + Columns.Message.COLUMN_NAME_CHAT_ID + " )";

    private static final String SQL_CREATE_PEER =
              CREATE_TABLE + Columns.Peer.TABLE_NAME
            + Columns.Peer._ID + " (" + " INTEGER PRIMARY KEY, " + Columns.Peer.COLUMN_NAME_USERNAME
            + TEXT_TYPE + COMMA_SEP + Columns.Peer.COLUMN_NAME_PUBLIC_KEY + PUBLIC_KEY_TYPE + COMMA_SEP
            + Columns.Peer.COLUMN_NAME_STATUS_MESSAGE + TEXT_TYPE + COMMA_SEP
            + Columns.Peer.COLUMN_NAME_PICTURE_PATH + TEXT_TYPE + COMMA_SEP
            + Columns.Peer.COLUMN_NAME_IS_ONLINE + BOOLEAN_TYPE + " )";

    private static final String SQL_DELETE_CHAT =
        "DROP TABLE IF EXISTS " + Columns.Chat.TABLE_NAME;

    private static final String SQL_DELETE_MESSAGES =
        "DROP TABLE IF EXISTS " + Columns.Message.TABLE_NAME;

    private static final String SQL_DELETE_PEER =
        "DROP TABLE IF EXISTS " + Columns.Peer.TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CHAT);
        db.execSQL(SQL_CREATE_MESSAGES);
        db.execSQL(SQL_CREATE_PEER);

        boolean isDebuggable =  (0 != (Tarsier.app().getApplicationInfo().flags
                & ApplicationInfo.FLAG_DEBUGGABLE));

        if (isDebuggable) {
            System.out.println("===================================");
            System.out.println("SQLiteDatabase onCreate generates :");
            System.out.println(SQL_CREATE_CHAT);
            System.out.println(SQL_CREATE_MESSAGES);
            System.out.println(SQL_CREATE_PEER);
            System.out.println("===================================");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // seems to me there ain't anything to do down here, as we shan't modify our architecture (for now?)
        // this method might be the one which should be used for new chats, not sure
    }
}
