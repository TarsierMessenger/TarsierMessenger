package ch.tarsier.tarsier.database;

import android.provider.BaseColumns;

/**
 * @author McMoudi
 * @author romac
 */
public final class Columns {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public Columns() {

    }

    /**
     * The chat table fields
     */
    public static abstract class Chat implements BaseColumns {
        public static final String TABLE_NAME = "chat";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_HOST_ID = "hostId";
        public static final String COLUMN_NAME_IS_PRIVATE = "isPrivate";

    }

    /**
     * The message table fields
     */
    public static abstract class Message implements BaseColumns {
        public static final String TABLE_NAME = "message";

        public static final String COLUMN_NAME_MSG = "msg";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_SENDER_PUBLIC_KEY = "senderPublicKey";
        public static final String COLUMN_NAME_SENT_BY_USER= "sentByUser";
        public static final String COLUMN_NAME_CHAT_ID = "chatId";

    }

    /**
     * The peer table fields
     */
    public static abstract class Peer implements BaseColumns {
        public static final String TABLE_NAME = "peer";

        public static final String COLUMN_NAME_PUBLIC_KEY = "publicKey";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_STATUS_MESSAGE = "statusMessage";
        public static final String COLUMN_NAME_PICTURE_PATH = "picturePath";
        public static final String COLUMN_NAME_IS_ONLINE = "online";
    }
}
