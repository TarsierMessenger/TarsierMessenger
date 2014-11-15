/*
package ch.tarsier.tarsier;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
=======
import android.content.Context;

import java.util.Date;
>>>>>>> first mockup, postponed until StorageAcess is changed

import ch.tarsier.tarsier.storage.Chat;
import ch.tarsier.tarsier.storage.Message;
import ch.tarsier.tarsier.storage.StorageAccess;
<<<<<<< HEAD
import ch.tarsier.tarsier.storage.User;

*/
/**
 * @author gluthier
 *//*

public class FillDatabaseWithFictionalData {
    private static final int AMIRREZA_BAHREINI_ID = 1;
    private static final int BENJAMIN_PACCAUD_ID = 2;
    private static final int FREDERIC_JACOBS_ID = 3;
    private static final int GABRIEL_LUTHIER_ID = 4;
    private static final int MARIN_JERRY_NICOLINI_ID = 5;
    private static final int ROMAIN_RUETSCHI_ID = 6;
    private static final int XAVIER_WILLEMIN_ID = 7;
    private static final int YANN_MAHMOUDI_ID = 8;

    private static final String AMIRREZA_BAHREINI = "Amirreza Bahreini";
    private static final String BENJAMIN_PACCAUD = "Benjamin Paccaud";
    private static final String FREDERIC_JACOBS = "Frederic Jacobs";
    private static final String GABRIEL_LUTHIER = "Gabriel Luthier";
    private static final String MARIN_JERRY_NICOLINI = "Marin-Jerry Nicolini";
    private static final String ROMAIN_RUETSCHI = "Romain Ruetschi";
    private static final String XAVIER_WILLEMIN = "Xavier Willemin";
    private static final String YANN_MAHMOUDI = "Yann Mahmoudi";
=======

*/
/**
 * Created by gluthier
 *//*

public class FillDatabaseWithFictionalData {
    private static final String AMIRREZA_BAHREINI_ID = "1";
    private static final String BENJAMIN_PACCAUD_ID = "2";
    private static final String FREDERIC_JACOBS_ID = "3";
    private static final String GABRIEL_LUTHIER_ID = "4";
    private static final String MARIN_JERRY_NICOLINI_ID = "5";
    private static final String ROMAIN_RUETSCHI_ID = "6";
    private static final String XAVIER_WILLEMIN_ID = "7";
    private static final String YANN_MAHMOUDI_ID = "8";
>>>>>>> first mockup, postponed until StorageAcess is changed

    private static final int CHAT_ID_1 = 1;
    private static final int CHAT_ID_2 = 2;
    private static final int CHAT_ID_3 = 3;
    private static final int CHAT_ID_4 = 4;
    private static final int CHAT_ID_5 = 5;
    private static final int CHAT_ID_6 = 6;
    private static final int CHAT_ID_7 = 7;
    private static final int CHAT_ID_8 = 8;
<<<<<<< HEAD
    private static final int CHAT_ID_9 = 9;
    private static final int CHAT_ID_10 = 10;

    public static void populate() {
        StorageAccess storageAccess = StorageAccess.getInstance();
        long time = new Date().getTime();
        Random random = new Random();
        // 86400000 milliseconds = 24 hours
        int n = 86400000;

        //User user = new User(GABRIEL_LUTHIER, "Coucou les cop1");
        storageAccess.setMyId(Integer.toString(GABRIEL_LUTHIER_ID));
        storageAccess.setMyUsername(GABRIEL_LUTHIER);
        storageAccess.setMyMood("Yolo");

        //Generate the chats
        ArrayList<Chat> chats = new ArrayList<Chat>(10);

        chats.add(new Chat(CHAT_ID_1, AMIRREZA_BAHREINI));
        chats.add(new Chat(CHAT_ID_2, BENJAMIN_PACCAUD));
        chats.add(new Chat(CHAT_ID_3, FREDERIC_JACOBS));
        chats.add(new Chat(CHAT_ID_4, MARIN_JERRY_NICOLINI));
        chats.add(new Chat(CHAT_ID_5, ROMAIN_RUETSCHI));
        chats.add(new Chat(CHAT_ID_6, XAVIER_WILLEMIN));
        chats.add(new Chat(CHAT_ID_7, YANN_MAHMOUDI));
        chats.add(new Chat(CHAT_ID_8, "SwEng", FREDERIC_JACOBS));
        chats.add(new Chat(CHAT_ID_9, "Git helpdesk", ROMAIN_RUETSCHI));
        chats.add(new Chat(CHAT_ID_10, "Sat", GABRIEL_LUTHIER));

        for (Chat c : chats) {
            storageAccess.addChatroom(c);
        }

        //Generate the messages for the first chat
        ArrayList<Message> messagesRoom1 = new ArrayList<Message>(34);
        long time1 = time;

        messagesRoom1.add(new Message(CHAT_ID_1, "Bacon ipsum dolor amet drumstick brisket pork chop strip steak tenderloin.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Sat?",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Corned beef beef ribs venison jowl rump drumstick meatball fatback flank turducken cow bresaola.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Chicken tail leberkas hamburger tri-tip swine porchetta fatback pastrami chuck tenderloin ribeye drumstick pork chop cow.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Kevin beef t-bone spare ribs biltong venison strip steak ground round swine leberkas flank short loin frankfurter.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Beef ribs ham ribeye ball tip short ribs turkey hamburger jowl jerky boudin prosciutto tenderloin drumstick pork chop pork loin.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Sausage biltong pig, frankfurter short loin ball tip swine landjaeger.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Shankle sausage bacon, chicken meatball andouille beef ribs.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Ball tip ham meatloaf pork loin, leberkas short ribs beef tail beef ribs.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Biltong ham hock meatball sirloin pastrami beef. ",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Brisket meatball ribeye shank filet mignon sirloin pork.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Corned beef venison ribeye filet mignon, bacon pork chop jowl ground round prosciutto tri-tip.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Salami ham hock chicken boudin meatball drumstick.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Turkey turducken pork chop, picanha tongue swine ham shankle.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Pig biltong salami, ground round pork belly tail pork loin jerky kevin sirloin.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Frankfurter swine tri-tip, filet mignon turducken spare ribs kevin rump cupim pancetta boudin shoulder chuck brisket t-bone.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Flank pork loin drumstick picanha bacon t-bone rump tenderloin, swine short loin.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Leberkas pancetta ground round tail landjaeger pig strip steak capicola ribeye shoulder cupim pork belly meatloaf pork.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Sirloin prosciutto strip steak chicken ball tip ribeye swine.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Corned beef filet mignon ball tip landjaeger chicken.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Kevin turducken hamburger boudin bacon beef.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Alcatra frankfurter filet mignon brisket bresaola pork.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Short loin hamburger pork cupim, beef meatball fatback kielbasa cow strip steak pastrami boudin.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Capicola doner venison pastrami, short loin shoulder landjaeger cupim pork belly kielbasa prosciutto porchetta.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "T-bone leberkas ham jowl pastrami pork loin, meatball ham hock pork turducken.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Meatball turducken capicola ham sausage ham hock ribeye spare ribs cow. ",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Venison t-bone short loin shoulder tail meatloaf capicola flank spare ribs meatball boudin brisket cow.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Corned beef chicken doner venison kevin turducken chuck.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Pork loin chicken shankle, cupim strip steak hamburger filet mignon andouille kevin.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Jowl tri-tip pork chop, beef ribs pork belly corned beef meatloaf porchetta bacon chuck shoulder.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, " Porchetta doner tail, ham ribeye pork chop corned beef bresaola kevin tri-tip pork.",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Kevin cow short ribs pork loin.",
                time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Drumstick short ribs shankle, picanha shank pork loin bresaola capicola. ",
                AMIRREZA_BAHREINI_ID, time1));
        time1 = time1 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_1, "Chuck tail venison biltong, filet mignon andouille fatback.",
                time1));

        for (Message m : messagesRoom1) {
            storageAccess.addMessage(m);
        }

        //Generate the messages for the second chat
        ArrayList<Message> messagesRoom2 = new ArrayList<Message>(6);
        long time2 = time;

        messagesRoom1.add(new Message(CHAT_ID_2, "Have you ever been in any YouTube videos?",
                BENJAMIN_PACCAUD_ID, time2));
        time2 = time2 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_2, "Would you rather have summer weather or winter weather all year round?",
                time2));
        time2 = time2 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_2, "Would you rather be tall and fat or short and well built?",
                BENJAMIN_PACCAUD_ID, time2));
        time2 = time2 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_2, "Would you rather be poor or ugly?",
                time2));
        time2 = time2 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_2, "When you were younger, what did you want to be when you grew up?",
                BENJAMIN_PACCAUD_ID, time2));
        time2 = time2 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_2, "Go sat",
                time2));

        for (Message m : messagesRoom2) {
            storageAccess.addMessage(m);
        }

        //Generate the messages for the third chat
        ArrayList<Message> messagesRoom3 = new ArrayList<Message>(8);
        long time3 = time;

        messagesRoom1.add(new Message(CHAT_ID_3, "Improve ashamed married expense bed her comfort pursuit mrs. Four time took ye your as fail lady. Up greatest am exertion or marianne. Shy occasional terminated insensible and inhabiting gay. So know do fond to half on. Now who promise was justice new winding. In finished on he speaking suitable advanced if. Boy happiness sportsmen say prevailed offending concealed nor was provision. Provided so as doubtful on striking required. Waiting we to compass assured.",
                FREDERIC_JACOBS_ID, time3));
        time3 = time3 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_3, "Is education residence conveying so so. Suppose shyness say ten behaved morning had. Any unsatiable assistance compliment occasional too reasonably advantages. Unpleasing has ask acceptance partiality alteration understood two. Worth no tiled my at house added. Married he hearing am it totally removal. Remove but suffer wanted his lively length. Moonlight two applauded conveying end direction old principle but. Are expenses distance weddings perceive strongly who age domestic.",
                time3));
        time3 = time3 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_3, "Performed suspicion in certainty so frankness by attention pretended. Newspaper or in tolerably education enjoyment. Extremity excellent certainty discourse sincerity no he so resembled. Joy house worse arise total boy but. Elderly up chicken do at feeling is. Like seen drew no make fond at on rent. Behaviour extremely her explained situation yet september gentleman are who. Is thought or pointed hearing he.",
                FREDERIC_JACOBS_ID, time3));
        time3 = time3 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_3, "Shewing met parties gravity husband sex pleased. On to no kind do next feel held walk. Last own loud and knew give gay four. Sentiments motionless or principles preference excellence am. Literature surrounded insensible at indulgence or to admiration remarkably. Matter future lovers desire marked boy use. Chamber reached do he nothing be.",
                FREDERIC_JACOBS_ID, time3));
        time3 = time3 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_3, "Ça sent le text généré automatiquement.",
                time3));
        time3 = time3 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_3, "Quand même!",
                time3));
        time3 = time3 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_3, "J'avoue!",
                FREDERIC_JACOBS_ID, time3));
        time3 = time3 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_3, "Ok, go à sat alors!",
                time3));

        for (Message m : messagesRoom3) {
            storageAccess.addMessage(m);
        }

        //Generate the messages for the fourth chat
        ArrayList<Message> messagesRoom4 = new ArrayList<Message>(7);
        long time4 = time;

        messagesRoom1.add(new Message(CHAT_ID_4, "Salut", MARIN_JERRY_NICOLINI_ID, time4));
        time4 = time4 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_4, "Salut", time4));
        time4 = time4 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_4, "On va à sat?", MARIN_JERRY_NICOLINI_ID, time4));
        time4 = time4 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_4, "J'ai vraiment besoin d'une bière!", MARIN_JERRY_NICOLINI_ID, time4));
        time4 = time4 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_4, "ou 10", time4));
        time4 = time4 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_4, "ou 1000", time4));
        time4 = time4 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_4, "#lolswagyolo", MARIN_JERRY_NICOLINI_ID, time4));

        for (Message m : messagesRoom4) {
            storageAccess.addMessage(m);
        }

        //Generate the messages for the fifth chat
        ArrayList<Message> messagesRoom5 = new ArrayList<Message>(7);
        long time5 = time;

        messagesRoom1.add(new Message(CHAT_ID_5, "Salut", ROMAIN_RUETSCHI_ID, time5));
        time5 = time5 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_5, "Salut", time5));
        time5 = time5 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_5, "On va à sat?", ROMAIN_RUETSCHI_ID, time5));
        time5 = time5 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_5, "J'ai vriament besoin d'une bière!", ROMAIN_RUETSCHI_ID, time5));
        time5 = time5 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_5, "ou 10", time5));
        time5 = time5 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_5, "ou 1000", time5));
        time5 = time5 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_5, "#lolswagyolo", ROMAIN_RUETSCHI_ID, time5));

        for (Message m : messagesRoom5) {
            storageAccess.addMessage(m);
        }

        //Generate the messages for the sixth chat
        ArrayList<Message> messagesRoom6 = new ArrayList<Message>(7);
        long time6 = time;

        messagesRoom1.add(new Message(CHAT_ID_6, "Salut", XAVIER_WILLEMIN_ID, time6));
        time6 = time6 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_6, "Salut", time6));
        time6 = time6 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_6, "On va à sat?", XAVIER_WILLEMIN_ID, time6));
        time6 = time6 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_6, "J'ai vriament besoin d'une bière!", XAVIER_WILLEMIN_ID, time6));
        time6 = time6 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_6, "ou 10", time6));
        time6 = time6 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_6, "ou 1000", time6));
        time6 = time6 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_6, "#lolswagyolo", XAVIER_WILLEMIN_ID, time6));

        for (Message m : messagesRoom6) {
            storageAccess.addMessage(m);
        }

        //Generate the messages for the seventh chat
        ArrayList<Message> messagesRoom7 = new ArrayList<Message>(7);
        long time7 = time;

        messagesRoom1.add(new Message(CHAT_ID_7, "Salut", YANN_MAHMOUDI_ID, time7));
        time7 = time7 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_7, "Salut", time7));
        time7 = time7 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_7, "On va à sat?", YANN_MAHMOUDI_ID, time7));
        time7 = time7 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_7, "J'ai vriament besoin d'une bière!", YANN_MAHMOUDI_ID, time7));
        time7 = time7 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_7, "ou 10", time7));
        time7 = time7 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_7, "ou 1000", time7));
        time7 = time7 - random.nextInt(n);
        messagesRoom1.add(new Message(CHAT_ID_7, "#lolswagyolo", YANN_MAHMOUDI_ID, time7));

        for (Message m : messagesRoom7) {
            storageAccess.addMessage(m);
        }

        //Generate the messages for the eighth chat
        ArrayList<Message> messagesRoom8 = new ArrayList<Message>(8);
        long time8 = time;

        messagesRoom8.add(new Message(CHAT_ID_8, "Make an empty app", AMIRREZA_BAHREINI_ID, time8));
        time8 = time8 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_8, "Investigate continous integration", BENJAMIN_PACCAUD_ID, time8));
        time8 = time8 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_8, "Make storyboards", FREDERIC_JACOBS_ID, time8));
        time8 = time8 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_8, "Write protocol specification", GABRIEL_LUTHIER_ID, time8));
        time8 = time8 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_8, "Storage Manager", MARIN_JERRY_NICOLINI_ID, time8));
        time8 = time8 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_8, "Key distribution", ROMAIN_RUETSCHI_ID, time8));
        time8 = time8 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_8, "UI Testing", XAVIER_WILLEMIN_ID, time8));
        time8 = time8 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_8, "UI Design", YANN_MAHMOUDI_ID, time8));

        for (Message m : messagesRoom8) {
            storageAccess.addMessage(m);
        }

        //Generate the messages for the ninth chat
        ArrayList<Message> messagesRoom9 = new ArrayList<Message>(10);
        long time9 = time;

        messagesRoom8.add(new Message(CHAT_ID_9, "git rebase", ROMAIN_RUETSCHI_ID, time9));
        time9 = time9 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_9, "With the rebase command, you can take all the changes that were committed on one branch and replay them on another one.",
                ROMAIN_RUETSCHI_ID, time9));
        time9 = time9 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_9, "ok", BENJAMIN_PACCAUD_ID, time9));
        time9 = time9 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_9, "ok", GABRIEL_LUTHIER_ID, time9));
        time9 = time9 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_9, "ok", MARIN_JERRY_NICOLINI_ID, time9));
        time9 = time9 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_9, "git merge", ROMAIN_RUETSCHI_ID, time9));
        time9 = time9 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_9, "The merge command performs a three-way merge between the two latest branch snapshots.",
                ROMAIN_RUETSCHI_ID, time9));
        time9 = time9 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_9, "ok", BENJAMIN_PACCAUD_ID, time9));
        time9 = time9 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_9, "ok", GABRIEL_LUTHIER_ID, time9));
        time9 = time9 - random.nextInt(n);
        messagesRoom8.add(new Message(CHAT_ID_9, "ok", MARIN_JERRY_NICOLINI_ID, time9));

        for (Message m : messagesRoom9) {
            storageAccess.addMessage(m);
        }

        //Generate the messages for the tenth chat
        ArrayList<Message> messagesRoom10 = new ArrayList<Message>(20);
        Message message10 = new Message(CHAT_ID_10, "sat", GABRIEL_LUTHIER_ID, time);

        for (int i = 0; i < 20; ++i) {
            storageAccess.addMessage(message10);
        }
=======

    public static void populateDb(Context context) {
        StorageAccess storageAccess = StorageAccess.getInstance(context);
        Date date = new Date();

        Chat chat1 = new Chat(CHAT_ID_1, "TA meeting 1", ROMAIN_RUETSCHI_ID);
        Message chat1mess1 = new Message(1, CHAT_ID_1, "À fond, mais je bosse dur, aussi!",
                GABRIEL_LUTHIER_ID, date.getTime(), false);


        Chat chat2 = new Chat(CHAT_ID_2, BENJAMIN_PACCAUD_ID);
        Chat chat3 = new Chat(CHAT_ID_3, "Hong Kong's umbrella movement", FREDERIC_JACOBS_ID);
        Chat chat4 = new Chat(CHAT_ID_4, MARIN_JERRY_NICOLINI_ID);
        Chat chat5 = new Chat(CHAT_ID_5, YANN_MAHMOUDI_ID);
        Chat chat6 = new Chat(CHAT_ID_6, ROMAIN_RUETSCHI_ID);
        Chat chat7 = new Chat(CHAT_ID_7, "SwEng", XAVIER_WILLEMIN_ID);

        Chat chat8 = new Chat(CHAT_ID_8, AMIRREZA_BAHREINI_ID);
        Message chat8mess1 = new Message(1, CHAT_ID_8, "ça se passe avec ces réseaux de neurones et ces Google Glass !?",
                ROMAIN_RUETSCHI_ID, date.getTime(), true);


>>>>>>> first mockup, postponed until StorageAcess is changed
    }
}
*/
