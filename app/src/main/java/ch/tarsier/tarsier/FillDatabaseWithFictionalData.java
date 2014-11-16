package ch.tarsier.tarsier;

import java.util.Date;
import java.util.Random;

import ch.tarsier.tarsier.storage.StorageAccess;

/**
 * @author gluthier
 */
public class FillDatabaseWithFictionalData {

    public static void populate() {
        StorageAccess storageAccess = Tarsier.app().getStorage();

        // Variables to simulate the time
        long time = new Date().getTime();
        int oneMinute = 60000; /* in milliseconds */
        int oneHour = 3600000; /* in milliseconds */
        int oneDay = 86400000; /* in milliseconds */
        Random random = new Random();

        /*
        ChatRepository chatRepository = storageAccess.getChatRepository();
        PeerRepository peerRepository = storageAccess.getPeerRepository();
        MessageRepository messageRepository = storageAccess.getMessageRepository();

        //Generate the peers
        Peer amirreza = new Peer("Amirreza Bahreini");
        Peer benjamin = new Peer("Benjamin Paccaud");
        Peer frederic = new Peer("Frederic Jacobs");
        User gabriel = new User("Gabriel Luthier");
        Peer marin = new Peer("Marin-Jerry Nicolini");
        Peer romain = new Peer("Romain Ruetschi");
        Peer xavier = new Peer("Xavier Willemin");
        Peer yann = new Peer("Yann Mahmoudi");

        peerRepository.insert(amirreza);
        peerRepository.insert(benjamin);
        peerRepository.insert(frederic);
        peerRepository.insert(gabriel);
        peerRepository.insert(marin);
        peerRepository.insert(romain);
        peerRepository.insert(xavier);
        peerRepository.insert(yann);

        //Generate the chats
        Chat chat1 = Chat(frederic);
        Chat chat2 = new Chat(marin);
        Chat chat3 = new Chat("SwEng", frederic);
        Chat chat4 = new Chat(romain);
        Chat chat5 = new Chat(amirreza);
        Chat chat6 = new Chat(xavier);
        Chat chat7 = new Chat(yann);
        Chat chat8 = new Chat("Git helpdesk", romain);
        Chat chat9 = new Chat(benjamin);
        Chat chat10 = new Chat("Saaaaat", gabriel);

        chatRepository.insert(chat1);
        chatRepository.insert(chat2);
        chatRepository.insert(chat3);
        chatRepository.insert(chat4);
        chatRepository.insert(chat5);
        chatRepository.insert(chat6);
        chatRepository.insert(chat7);
        chatRepository.insert(chat8);
        chatRepository.insert(chat9);
        chatRepository.insert(chat10);

        //Generate the messages for the first chat
        ArrayList<Message> messagesChat1 = new ArrayList<Message>();
        long time1 = time;

        messagesChat1.add(new Message(chat1.getChatId(), "Yo ça va?", gabriel.getId(), time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1.getChatId(), "ça avance le projet?", gabriel.getId(), time1);
        time1 -= random.nextInt(oneHour);
        messagesChat1.add(new Message(chat1.getChatId(), "Oui ça avance bien, on a presque fini notre partie!", frederic.getId(), time1);
        time1 -= random.nextInt(oneHour);
        messagesChat1.add(new Message(chat1.getChatId(), "Excellent :)", gabriel.getId(), time1);
        time1 -= random.nextInt(oneDay);
        messagesChat1.add(new Message(chat1.getChatId(), "On va bosser à sat si jamais tu veux venir", frederic.getId(), time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1.getChatId(), "Volontiers!", gabriel.getId(), time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1.getChatId(), "J'arrive tout de suite :)", gabriel.getId(), time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1.getChatId(), "On est sur les canaps au fond si jamais", frederic.getId(), time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1.getChatId(), "Tu peux me prendre une cuvée si vous n'avez pas encore commandé stp?", gabriel.getId(), time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1.getChatId(), "On a déjà nos bière dsl", frederic.getId(), time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1.getChatId(), "Mais y a pas beaucoup de monde de toute façon", frederic.getId(), time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1.getChatId(), "Ok nickel^^", gabriel.getId(), time1);

        for (Message m : messagesChat1) {
            messageRepository.insert(m);
        }

         */

    }
}
