package ch.tarsier.tarsier;

import android.content.Context;

import java.util.Date;

import ch.tarsier.tarsier.storage.Chat;
import ch.tarsier.tarsier.storage.Message;
import ch.tarsier.tarsier.storage.StorageAccess;

/**
 * @author gluthier
 */
public class FillDatabaseWithFictionalData {
    private static final String AMIRREZA_BAHREINI_ID = "1";
    private static final String BENJAMIN_PACCAUD_ID = "2";
    private static final String FREDERIC_JACOBS_ID = "3";
    private static final String GABRIEL_LUTHIER_ID = "4";
    private static final String MARIN_JERRY_NICOLINI_ID = "5";
    private static final String ROMAIN_RUETSCHI_ID = "6";
    private static final String XAVIER_WILLEMIN_ID = "7";
    private static final String YANN_MAHMOUDI_ID = "8";

    private static final int CHAT_ID_1 = 1;
    private static final int CHAT_ID_2 = 2;
    private static final int CHAT_ID_3 = 3;
    private static final int CHAT_ID_4 = 4;
    private static final int CHAT_ID_5 = 5;
    private static final int CHAT_ID_6 = 6;
    private static final int CHAT_ID_7 = 7;
    private static final int CHAT_ID_8 = 8;

    public static void populateDb(Context context) {
        //StorageAccess storageAccess = StorageAccess.getInstance(context);
        Date date = new Date();

        Chat chat1 = new Chat(CHAT_ID_1, "TA meeting 1", ROMAIN_RUETSCHI_ID);
       // Message chat1mess1 = new Message(1, CHAT_ID_1, "À fond, mais je bosse dur, aussi!",
       //         GABRIEL_LUTHIER_ID, date.getTime(), false);


        Chat chat2 = new Chat(CHAT_ID_2, BENJAMIN_PACCAUD_ID);
        Chat chat3 = new Chat(CHAT_ID_3, "Hong Kong's umbrella movement", FREDERIC_JACOBS_ID);
        Chat chat4 = new Chat(CHAT_ID_4, MARIN_JERRY_NICOLINI_ID);
        Chat chat5 = new Chat(CHAT_ID_5, YANN_MAHMOUDI_ID);
        Chat chat6 = new Chat(CHAT_ID_6, ROMAIN_RUETSCHI_ID);
        Chat chat7 = new Chat(CHAT_ID_7, "SwEng", XAVIER_WILLEMIN_ID);

        Chat chat8 = new Chat(CHAT_ID_8, AMIRREZA_BAHREINI_ID);
        //Message chat8mess1 = new Message(1, CHAT_ID_8, "ça se passe avec ces réseaux de neurones et ces Google Glass !?",
        //        ROMAIN_RUETSCHI_ID, date.getTime(), true);


    }
}
