package ch.tarsier.tarsier.storage;

import android.provider.BaseColumns;

/**
 * Created by McMoudi on 23/10/14.
 */
public final class ChatsContract {


    public static final String COLUMN_NAME_ENTRY_ID = "entryid";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";




    public ChatsContract() {}


    public static abstract class ChatRooms implements BaseColumns {

        public static final String TABLE_NAME = "chatrooms";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_HOST = "host";


    }

    public static abstract class Chats implements BaseColumns{
        public static final String TABLE_NAME = "chats";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_HOST = "host";

    }


    public static abstract class Messages implements BaseColumns {

        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_NAME_MSG = "msg";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_SENDER = "sender";


    }

}
