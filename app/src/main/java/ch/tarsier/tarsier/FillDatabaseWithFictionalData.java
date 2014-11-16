package ch.tarsier.tarsier;

import java.util.Date;
import java.util.Random;

import ch.tarsier.tarsier.domain.model.User;
import ch.tarsier.tarsier.domain.repository.UserRepository;
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
        Peer gabriel = new Peer("Gabriel Luthier");
        Peer marin = new Peer("Marin-Jerry Nicolini");
        Peer romain = new Peer("Romain Ruetschi");
        Peer xavier = new Peer("Xavier Willemin");
        Peer yann = new Peer("Yann Mahmoudi");

        final long amirrezaId = amirrezaP.getId();
        final long benjaminId = benjaminP.getId();
        final long fredericId = fredericP.getId();
        final long marinId = marinP.getId();
        final long romainId = romainP.getId();
        final long xavierId = xavierP.getId();
        final long yannId = yannP.getId();

        final String amirrezaName = amirrezaP.getName();
        final String benjaminName = benjaminP.getName();
        final String fredericName = fredericP.getName();
        final String marinName = marinP.getName();
        final String romainName = romainP.getName();
        final String xavierName = xavierP.getName();
        final String yannName = yannP.getName();

        peerRepository.insert(amirrezaP);
        peerRepository.insert(benjaminP);
        peerRepository.insert(fredericP);
        peerRepository.insert(gabrielP);
        peerRepository.insert(marinP);
        peerRepository.insert(romainP);
        peerRepository.insert(xavierP);
        peerRepository.insert(yannP);

         */

    }
}
