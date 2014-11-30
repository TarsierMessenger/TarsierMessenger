package ch.tarsier.tarsier.database;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ch.tarsier.tarsier.Tarsier;

/**
 * @author McMoudi
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    public static final String DATABASE_NAME = "tarsier.db";
    public static final int DATABASE_VERSION = 1;

    private static final String PRIMARY_KEY_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String ID_TYPE = "INTEGER";
    private static final String TEXT_TYPE = "TEXT";
    private static final String PUBLIC_KEY_TYPE = "TEXT";
    private static final String BOOLEAN_TYPE = "INTEGER"; // SQLite doesn't have a boolean type
    private static final String DATETIME_TYPE = "DATETIME";
    private static final String COMMA_SEP = ",";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS";

    private static final String SQL_CREATE_CHAT = CREATE_TABLE + " " + Columns.Chat.TABLE_NAME + " ("
            + Columns.Chat._ID + " " + PRIMARY_KEY_TYPE + COMMA_SEP + " "
            + Columns.Chat.COLUMN_NAME_TITLE + " " + TEXT_TYPE + COMMA_SEP + " "
            + Columns.Chat.COLUMN_NAME_HOST_ID + " " + ID_TYPE + COMMA_SEP + " "
            + Columns.Chat.COLUMN_NAME_IS_PRIVATE + " " + BOOLEAN_TYPE + ")";

    private static final String SQL_CREATE_MESSAGES = CREATE_TABLE + " " + Columns.Message.TABLE_NAME + " ("
            + Columns.Message._ID + " " + PRIMARY_KEY_TYPE + COMMA_SEP + " "
            + Columns.Message.COLUMN_NAME_MSG + " " + TEXT_TYPE + COMMA_SEP + " "
            + Columns.Message.COLUMN_NAME_DATETIME + " " + DATETIME_TYPE + COMMA_SEP + " "
            + Columns.Message.COLUMN_NAME_SENDER_PUBLIC_KEY + " " + PUBLIC_KEY_TYPE + COMMA_SEP + " "
            + Columns.Message.COLUMN_NAME_CHAT_ID + " " + ID_TYPE + ")";

    private static final String SQL_CREATE_PEER = CREATE_TABLE + " " + Columns.Peer.TABLE_NAME + " ("
            + Columns.Peer._ID + " " + PRIMARY_KEY_TYPE + COMMA_SEP + " "
            + Columns.Peer.COLUMN_NAME_USERNAME + " " + TEXT_TYPE + COMMA_SEP + " "
            + Columns.Peer.COLUMN_NAME_PUBLIC_KEY + " " + PUBLIC_KEY_TYPE + COMMA_SEP + " "
            + Columns.Peer.COLUMN_NAME_STATUS_MESSAGE + " " + TEXT_TYPE + COMMA_SEP + " "
            + Columns.Peer.COLUMN_NAME_PICTURE_PATH + " " + TEXT_TYPE + COMMA_SEP + " "
            + Columns.Peer.COLUMN_NAME_IS_ONLINE + " " +  BOOLEAN_TYPE + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();

        try {
            db.execSQL(SQL_CREATE_CHAT);
            db.execSQL(SQL_CREATE_MESSAGES);
            db.execSQL(SQL_CREATE_PEER);

            boolean isDebuggable = 0 != (Tarsier.app().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE);

            if (isDebuggable) {
                Log.d(TAG, "===================================");
                Log.d(TAG, "SQLiteDatabase onCreate generates :");
                Log.d(TAG, SQL_CREATE_CHAT);
                Log.d(TAG, SQL_CREATE_MESSAGES);
                Log.d(TAG, SQL_CREATE_PEER);
                Log.d(TAG, "===================================");
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Columns.Chat.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Columns.Message.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Columns.Peer.TABLE_NAME);

        onCreate(db);
    }
}
