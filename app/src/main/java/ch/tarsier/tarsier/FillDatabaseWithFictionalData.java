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

        long amirrezaId = amirreza.getId();
        long benjaminId = benjamin.getId();
        long fredericId = frederic.getId();
        long gabrielId = gabriel.getId();
        long marinId = marin.getId();
        long romainId = romain.getId();
        long xavierId = xavier.getId();
        long yannId = yann.getId();

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
        ArrayList<Message> messagesChat1 = new ArrayList<Message>(12);
        long time1 = time;
        long chat1Id = chat1.getChatId();

        messagesChat1.add(new Message(chat1Id, "Yo ça va?", gabrielId, time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "ça avance le projet?", gabrielId, time1);
        time1 -= random.nextInt(oneHour);
        messagesChat1.add(new Message(chat1Id, "Oui ça avance bien, on a presque fini notre partie!", fredericId, time1);
        time1 -= random.nextInt(oneHour);
        messagesChat1.add(new Message(chat1Id, "Excellent :)", gabrielId, time1);
        time1 -= random.nextInt(oneDay);
        messagesChat1.add(new Message(chat1Id, "On va bosser à sat si jamais tu veux venir", fredericId, time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "Volontiers!", gabrielId, time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "J'arrive tout de suite :)", gabrielId, time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "On est sur les canaps au fond si jamais", fredericId, time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "Tu peux me prendre une cuvée si vous n'avez pas encore commandé stp?", gabrielId, time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "On a déjà nos bière dsl", fredericId, time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "Mais y a pas beaucoup de monde de toute façon", fredericId, time1);
        time1 -= random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "Ok nickel^^", gabrielId, time1);

        for (Message m : messagesChat1) {
            messageRepository.insert(m);
        }

        //Generate the messages for the second chat
        ArrayList<Messages> messagesChat2 = new ArrayList<Message>(8);
        long time2 = time;
        long chat2Id = chat2.getChatId();

        messagesChat2.add(new Message(chat2Id, "yo", marinId, time2);
        time2 -= random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "café?", marinId, time2);
        time2 -= random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "ouais", gabrielId, time2);
        time2 -= random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "j'en ai besoin^^", gabrielId, time2);
        time2 -= random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "pareil :p", marinId, time2);
        time2 -= random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "rdv en haut du bc?!", marinId, time2);
        time2 -= random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "ok top", gabrielId, time2);
        time2 -= random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "j'arrive dans 5 min", gabrielId, time2);

        for (Message m : messagesChat2) {
            messageRepository.insert(m);
        }

        //Generate the messages for the third chat
        ArrayList<Message> messagesChat3 = new ArrayList<Message(18);
        long time3 = time;
        long chat3Id = chat3.getChatId();

        messageChat3.add(new Message(chat3Id, "Salut les gars, ça vous dit d'aller prendre une bière à sat?", amirrezaId, time3);
        time3 -= random.nextInt(oneHour);
        messageChat3.add(new Message(chat3Id, "Yes!", gabrielId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "J'y suis déjà, je vous prend quoi?", gabrielId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "Oui parce que comme c'est un chat fictif, je peux offrir des bières à tout le monde", gabrielId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "En vrai j'ai pas d'argent... :(", gabrielId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "Yes t'es trop cool comme mec", amirrezaId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "Le pire c'est que je suis entrain de m'autocomplimenter là", gabrielId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "À la limite de la schizo", gabrielId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "Bon tg, commande-nous des bières!", amirrezaId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "Une cuvée pour moi thx", amirrezaId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "Une delirium perso", yannId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "Yes, je prend une karmeliet stp", romainId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "une guinness merci", marinId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "Aussi une cuvée!", fredericId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "pareil", benjaminId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "Je vais prendre un Kwak si t'arrives", xavierId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "Ok ça marche", gabrielId, time3);
        time3 -= random.nextInt(oneMinute);
        messageChat3.add(new Message(chat3Id, "Très bons choix les gars!", gabrielId, time3);

        for (Message m : messagesChat3) {
            messageRepository.insert(m);
        }

        //Generate the messages for the fourth chat
        ArrayList<Message> messagesChat4 = new ArrayList<Message>();
        long time4 = time;
        long chat4Id = chat4.getChatId();

        messageChat4.add(new Message(chat4Id, "yoyo", gabrielId, time4);
        time4 -= random.nextInt(oneHour);
        messageChat4.add(new Message(chat4Id, "yéyé", gabrielId, time4);
        time4 -= random.nextInt(oneHour);
        messageChat4.add(new Message(chat4Id, "yaya", gabrielId, time4);
        time4 -= random.nextInt(oneHour);
        messageChat4.add(new Message(chat4Id, "yolo", gabrielId, time4);
        time4 -= random.nextInt(oneHour);
        messageChat4.add(new Message(chat4Id, "swag", gabrielId, time4);
        time4 -= random.nextInt(oneHour);
        messageChat4.add(new Message(chat4Id, "wesh", gabrielId, time4);
        time4 -= random.nextInt(oneMinute);
        messageChat4.add(new Message(chat4Id, "ma geule", gabrielId, time4);
        time4 -= random.nextInt(oneDay);
        messageChat4.add(new Message(chat4Id, "stop spam", romainId, time4);
        time4 -= random.nextInt(oneMinute);
        messageChat4.add(new Message(chat4Id, "stop spam", romainId, time4);
        time4 -= random.nextInt(oneMinute);
        messageChat4.add(new Message(chat4Id, "stop spam", romainId, time4);
        time4 -= random.nextInt(oneMinute);
        messageChat4.add(new Message(chat4Id, "stop spam", romainId, time4);
        time4 -= random.nextInt(oneMinute);
        messageChat4.add(new Message(chat4Id, "ok", gabrielId, time4);

        for (Message m : messagesChat4) {
            messageRepository.insert(m);
        }

         */

    }
}
