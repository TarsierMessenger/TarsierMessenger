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
        long time = new Date().getTime();
        Random random = new Random();
        int n = 86400000;
        // 86400000 milliseconds = 24 hours

        /*
        ChatRepository chatRepository = storageAccess.getChatRepository();
        PeerRepository peerRepository = storageAccess.getPeerRepository();

        //Generate the peers
        Peer amirreza = new Peer("Amirreza Bahreini");
        Peer benjamin = new Peer("Benjamin Paccaud");
        Peer frederic = new Peer("Frederic Jacobs");
        User gabriel = new User("Gabriel Luthier");
        Peer marin = new Peer("Marin-Jerry Nicolini");
        Peer romain = new Peer("Romain Ruetschi");
        Peer xavier = new Peer("Xavier Willemin");
        Peer yann = new Peer("Yann Mahmoudi");

        final long amirrezaId = amirreza.getId();
        final long benjaminId = benjamin.getId();
        final long fredericId = frederic.getId();
        final long gabrielId = gabriel.getId();
        final long marinId = marin.getId();
        final long romainId = romain.getId();
        final long xavierId = xavier.getId();
        final long yannId = yann.getId();

        final String amirrezaName = amirreza.getName();
        final String benjaminName = benjamin.getName();
        final String fredericName = frederic.getName();
        final String gabrielName = gabriel.getName();
        final String marinName = marin.getName();
        final String romainName = romain.getName();
        final String xavierName = xavier.getName();
        final String yannName = yann.getName();

        peerRepository.insert(amirreza);
        peerRepository.insert(benjamin);
        peerRepository.insert(frederic);
        peerRepository.insert(gabriel);
        peerRepository.insert(marin);
        peerRepository.insert(romain);
        peerRepository.insert(xavier);
        peerRepository.insert(yann);

        //Generate the chats
        ArrayList<Chat> chatList = new ArrayList<Chat>(10);

        chatList.add(new Chat(frederic));
        chatList.add(new Chat(marin));
        chatList.add(new Chat("SwEng", frederic));
        chatList.add(new Chat(romain));
        chatList.add(new Chat(amirreza));
        chatList.add(new Chat(xavier));
        chatList.add(new Chat(yann));
        chatList.add(new Chat("Git helpdesk", romain));
        chatList.add(new Chat(benjamin));
        chatList.add(new Chat("Saaaaat", gabriel));

        for (Chat c : chatList) {
            chatRepository.insert(c);
        }

         */

    }
}
