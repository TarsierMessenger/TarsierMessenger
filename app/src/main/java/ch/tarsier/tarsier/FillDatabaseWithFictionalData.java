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
        long oneMinute = 60000; /* in milliseconds */
        long oneHour = 3600000; /* in milliseconds */
        long oneDay = 86400000; /* in milliseconds */
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
        long time1 = time - oneDay;
        long chat1Id = chat1.getChatId();

        messagesChat1.add(new Message(chat1Id, "Yo ça va?", gabrielId, time1));
        time1 += random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "ça avance le projet?", gabrielId, time1));
        time1 += random.nextInt(oneHour);
        messagesChat1.add(new Message(chat1Id, "Oui ça avance bien, on a presque fini notre partie!", fredericId, time1));
        time1 += random.nextInt(oneHour);
        messagesChat1.add(new Message(chat1Id, "Excellent :)", gabrielId, time1));
        time1 += random.nextInt(oneHour);
        messagesChat1.add(new Message(chat1Id, "On va bosser à sat si jamais tu veux venir", fredericId, time1));
        time1 += random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "Volontiers!", gabrielId, time1));
        time1 += random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "J'arrive tout de suite :)", gabrielId, time1));
        time1 += random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "On est sur les canaps au fond si jamais", fredericId, time1));
        time1 += random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "Tu peux me prendre une cuvée si vous n'avez pas encore commandé stp?", gabrielId, time1));
        time1 += random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "On a déjà nos bière dsl", fredericId, time1));
        time1 += random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "Mais y a pas beaucoup de monde de toute façon", fredericId, time1));
        time1 += random.nextInt(oneMinute);
        messagesChat1.add(new Message(chat1Id, "Ok nickel^^", gabrielId, time1));

        for (Message m : messagesChat1) {
            messageRepository.insert(m);
        }

        //Generate the messages for the second chat
        ArrayList<Messages> messagesChat2 = new ArrayList<Message>(8);
        long time2 = time - oneDay;
        long chat2Id = chat2.getChatId();

        messagesChat2.add(new Message(chat2Id, "yo", marinId, time2));
        time2 += random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "café?", marinId, time2));
        time2 += random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "ouais", gabrielId, time2));
        time2 += random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "j'en ai besoin^^", gabrielId, time2));
        time2 += random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "pareil :p", marinId, time2));
        time2 += random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "rdv en haut du bc?!", marinId, time2));
        time2 += random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "ok top", gabrielId, time2));
        time2 += random.nextInt(oneMinute);
        messagesChat2.add(new Message(chat2Id, "j'arrive dans 5 min", gabrielId, time2));

        for (Message m : messagesChat2) {
            messageRepository.insert(m);
        }

        //Generate the messages for the third chat
        ArrayList<Message> messagesChat3 = new ArrayList<Message(18);
        long time3 = time - oneDay;
        long chat3Id = chat3.getChatId();

        messagesChat3.add(new Message(chat3Id, "Salut les gars, ça vous dit d'aller prendre une bière à sat?", amirrezaId, time3));
        time3 += random.nextInt(oneHour);
        messagesChat3.add(new Message(chat3Id, "Yes!", gabrielId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "J'y suis déjà, je vous prend quoi?", gabrielId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "Oui parce que comme c'est un chat fictif, je peux offrir des bières à tout le monde", gabrielId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "En vrai j'ai pas d'argent... :(", gabrielId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "Yes t'es trop cool comme mec", amirrezaId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "Le pire c'est que je suis entrain de m'autocomplimenter là", gabrielId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "À la limite de la schizo", gabrielId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "Bon tg, commande-nous des bières!", amirrezaId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "Une cuvée pour moi thx", amirrezaId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "Une delirium perso", yannId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "Yes, je prend une karmeliet stp", romainId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "une guinness merci", marinId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "Aussi une cuvée!", fredericId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "pareil", benjaminId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "Je vais prendre un Kwak si t'arrives", xavierId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "Ok ça marche", gabrielId, time3));
        time3 += random.nextInt(oneMinute);
        messagesChat3.add(new Message(chat3Id, "Très bons choix les gars!", gabrielId, time3));

        for (Message m : messagesChat3) {
            messageRepository.insert(m);
        }

        //Generate the messages for the fourth chat
        ArrayList<Message> messagesChat4 = new ArrayList<Message>(12);
        long time4 = time - oneDay;
        long chat4Id = chat4.getChatId();

        messagesChat4.add(new Message(chat4Id, "yoyo", gabrielId, time4));
        time4 += random.nextInt(oneHour);
        messagesChat4.add(new Message(chat4Id, "yéyé", gabrielId, time4));
        time4 += random.nextInt(oneHour);
        messagesChat4.add(new Message(chat4Id, "yaya", gabrielId, time4));
        time4 += random.nextInt(oneHour);
        messagesChat4.add(new Message(chat4Id, "yolo", gabrielId, time4));
        time4 += random.nextInt(oneHour);
        messagesChat4.add(new Message(chat4Id, "swag", gabrielId, time4));
        time4 += random.nextInt(oneHour);
        messagesChat4.add(new Message(chat4Id, "wesh", gabrielId, time4);
        time4 += random.nextInt(oneMinute);
        messagesChat4.add(new Message(chat4Id, "ma geule", gabrielId, time4));
        time4 += random.nextInt(oneHour);
        messagesChat4.add(new Message(chat4Id, "stop spam", romainId, time4));
        time4 += random.nextInt(oneMinute);
        messagesChat4.add(new Message(chat4Id, "stop spam", romainId, time4));
        time4 += random.nextInt(oneMinute);
        messagesChat4.add(new Message(chat4Id, "stop spam", romainId, time4));
        time4 += random.nextInt(oneMinute);
        messagesChat4.add(new Message(chat4Id, "stop spam", romainId, time4));
        time4 += random.nextInt(oneMinute);
        messagesChat4.add(new Message(chat4Id, "ok", gabrielId, time4));

        for (Message m : messagesChat4) {
            messageRepository.insert(m);
        }

        //Generate the messages for the fifth chat
        ArrayList<Message> messagesChat5 = new ArrayList<Message>(8);
        long time5 = time - oneDay;
        long chat5Id = chat5.getChatId();

        messagesChat5.add(new Message(chat5Id, "la fête?", amirrezaId, time5));
        time5 += random.nextInt(oneHour);
        messagesChat5.add(new Message(chat5Id, "oui", gabrielId, time5));
        time5 += random.nextInt(oneHour);
        messagesChat5.add(new Message(chat5Id, "La fête?", gabrielId, time5));
        time5 += random.nextInt(oneHour);
        messagesChat5.add(new Message(chat5Id, "oui", amirrezaId, time5));
        time5 += random.nextInt(oneHour);
        messagesChat5.add(new Message(chat5Id, "La fête?", amirrezaId, time5));
        time5 += random.nextInt(oneHour);
        messagesChat5.add(new Message(chat5Id, "oui", gabrielId, time5));
        time5 += random.nextInt(oneHour);
        messagesChat5.add(new Message(chat5Id, "La fête?", gabrielId, time5));
        time5 += random.nextInt(oneHour);
        messagesChat5.add(new Message(chat5Id, "oui", amirrezaId, time5));

        for (Message m : messagesChat5) {
            messageRepository.insert(m);
        }

        //Generate the messages for the sixth chat
        ArrayList<Message> messagesChat6 = new Arraylist<Message>(12);
        long time6 = time - oneDay;
        long chat6Id = chat6.getChatId();

        messagesChat6.add(new Message(chat6Id, "Yo, tu peux m'envoyer pleins de messages pour tester l'affichage de l'app stp?", xavierId, time6));
        time6 += random.nextInt(oneHour);
        messagesChat6.add(new Message(chat6Id, "Ok!", gabrielId, time6));
        time6 += random.nextInt(oneMinute);
        messagesChat6.add(new Message(chat6Id, "On va commencer avec une très long message:", gabrielId, time6));
        time6 += random.nextInt(oneMinute);
        messagesChat6.add(new Message(chat6Id, "Quel est votre secret ?
                Pour faire une bonne purée ce qui est pas mal quand on cuit les pommes de terre
                C'est de mettre du laurier et du thym pour parfumer en amont
                Après tu peux ajouter n'importe quel épice
                Tu peux mettre du safran, du curcuma, du gingembre
                Ou une gousse d'ail une fois que les patates sont pétries", gabrielId, time6));
        time6 += random.nextInt(oneMinute);
        messagesChat6.add(new Message(chat6Id, "Salut c'est cool en force!", gabrielId, time6));
        time6 += random.nextInt(oneMinute);
        messagesChat6.add(new Message(chat6Id, "1", gabrielId, time6));
        time6 += random.nextInt(oneMinute);
        messagesChat6.add(new Message(chat6Id, "2", gabrielId, time6));
        time6 += random.nextInt(oneMinute);
        messagesChat6.add(new Message(chat6Id, "3", gabrielId, time6));
        time6 += random.nextInt(oneMinute);
        messagesChat6.add(new Message(chat6Id, "4", gabrielId, time6));
        time6 += random.nextInt(oneMinute);
        messagesChat6.add(new Message(chat6Id, "5", gabrielId, time6));
        time6 += random.nextInt(oneMinute);
        messagesChat6.add(new Message(chat6Id, "Dis moi si tu veux plus de trucs...", gabrielId, time6));
        time6 += random.nextInt(oneMinute);
        messagesChat6.add(new Message(chat6Id, "J'ai pas trop d'inspirations^^", gabrielId, time6));

        for (Message m : messagesChat6) {
            messageRepository.insert(m);
        }

        //Generate the messages for the seventh chat
        ArrayList<Message> messagesChat7 = new ArrayList<Message>(10);
        long time7 = time - oneDay;
        long chat7Id = chat7.getChatId();

        messagesChat7.add(new Message(chat7Id, "t'es là?", gabrielId, time7));
        time7 += random.nextInt(oneHour);
        messagesChat7.add(new Message(chat7Id, "oui", yannId, time7));
        time7 += random.nextInt(oneHour);
        messagesChat7.add(new Message(chat7Id, "t'es là?", yannId, time7));
        time7 += random.nextInt(oneHour);
        messagesChat7.add(new Message(chat7Id, "oui", gabrielId, time7));
        time7 += random.nextInt(oneHour);
        messagesChat7.add(new Message(chat7Id, "t'es là?", gabrielId, time7));
        time7 += random.nextInt(oneHour);
        messagesChat7.add(new Message(chat7Id, "oui", yannId, time7));
        time7 += random.nextInt(oneHour);
        messagesChat7.add(new Message(chat7Id, "t'es là?", yannId, time7));
        time7 += random.nextInt(oneHour);
        messagesChat7.add(new Message(chat7Id, "oui", gabrielId, time7));
        time7 += random.nextInt(oneHour);
        messagesChat7.add(new Message(chat7Id, "t'es là?", gabrielId, time7));
        time7 += random.nextInt(oneHour);
        messagesChat7.add(new Message(chat7Id, "oui", yannId, time7));
        time7 += random.nextInt(oneHour);

        for (Message m : messagesChat7) {
            messageRepository.insert(m);
        }

        //Generate the messages for the eighth chat
        ArrayList<Message> messagesChat8 = new ArrayList<Message>(10);
        long time8 = time - oneDay;
        long chat8Id = chat8.getChatId();

        messagesChat8.add(new Message(chat8Id, "git rebase", romainId, time8));
        time8 += random.nextInt(oneMinute);
        messagesChat8.add(new Message(chat8Id, "With the rebase command, you can take all the changes that were committed on one branch and replay them on another one.", romainId, time8));
        time8 += random.nextInt(oneMinute);
        messagesChat8.add(new Message(chat8Id, "ok", gabrielId, time8));
        time8 += random.nextInt(oneMinute);
        messagesChat8.add(new Message(chat8Id, "ok", marinId, time8));
        time8 += random.nextInt(oneMinute);
        messagesChat8.add(new Message(chat8Id, "ok", benjaminId, time8));
        time8 += random.nextInt(oneHour);
        messagesChat8.add(new Message(chat8Id, "git merge", romainId, time8));
        time8 += random.nextInt(oneMinute);
        messagesChat8.add(new Message(chat8Id, "The merge command performs a three-way merge between the two latest branch snapshots.", romainId, time8));
        time8 += random.nextInt(oneMinute);
        messagesChat8.add(new Message(chat8Id, "ok", marinId, time8));
        time8 += random.nextInt(oneMinute);
        messagesChat8.add(new Message(chat8Id, "ok", benjaminId, time8));
        time8 += random.nextInt(oneMinute);
        messagesChat8.add(new Message(chat8Id, "ok", gabrielId, time8));

        for (Message m : messagesChat8) {
            messageRepository.insert(m);
        }

        //Generate the messages for the ninth chat
        ArrayList<Message> messagesChat9 = new ArrayList<Message>(8);
        long time9 = time - oneDay;
        long chat9Id = chat9.getChatId();

        messagesChat9.add(new Message(chat9Id, "salut", benjaminId, time9));
        time9 += random.nextInt(oneHour);
        messagesChat9.add(new Message(chat9Id, "salut", benjaminId, time9));
        time9 += random.nextInt(oneHour);
        messagesChat9.add(new Message(chat9Id, "salut", benjaminId, time9));
        time9 += random.nextInt(oneHour);
        messagesChat9.add(new Message(chat9Id, "salut", benjaminId, time9));
        time9 += random.nextInt(oneMinute);
        messagesChat9.add(new Message(chat9Id, "Sympa le vent...", benjaminId, time9));
        time9 += random.nextInt(oneHour);
        messagesChat9.add(new Message(chat9Id, "dsl", gabrielId, time9));
        time9 += random.nextInt(oneMinute);
        messagesChat9.add(new Message(chat9Id, "J'étais entrain de bosser", gabrielId, time9));
        time9 += random.nextInt(oneMinute);
        messagesChat9.add(new Message(chat9Id, "Alors ça!", benjaminId, time9));

        for (Message m : messagesChat9) {
            messageRepository.insert(m);
        }

        //Generate the messages for the tenth chat
        ArrayList<Message> messagesChat10 = new ArrayList<Message>(24);
        long time10 = time - oneDay;
        long chat10Id = chat10.getChatId();

        messagesChat10.add(new Message(chat10Id, "Quel est le meilleur bar?", gabrielId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "sat", amirrezaId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "sat", benjaminId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "sat", fredericId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "sat", marinId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "sat", romainId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "sat", xavierId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "sat", yanId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "Où est-ce qu'on sert de bonnes bières?", gabrielId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", amirrezaId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", benjaminId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", fredericId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", marinId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", romainId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", xavierId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", yanId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "Où est-ce qu'on va ce soir?", gabrielId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", amirrezaId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", benjaminId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", fredericId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", marinId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", romainId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", xavierId, time10));
        time10 += random.nextInt(oneMinute);
        messagesChat10.add(new Message(chat10Id, "à sat", yanId, time10));

        for (Message m : messagesChat10) {
            messageRepository.insert(m);
        }
        */
    }
}
