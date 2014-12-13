package ch.tarsier.tarsier.test;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.crypto.EC25519;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.User;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.domain.repository.UserRepository;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;

/**
 * The FillDBForTesting class is used to fill the mocked database for testing purposes
 *
 * @author gluthier
 */
public class FillDBForTesting {

    public static User user;

    public static Peer peer1;
    public static Peer peer2;
    public static Peer peer3;

    public static Chat chat1;
    public static Chat chat2;

    public static Message message1Chat1;
    public static Message message2Chat1;

    public static Message message1Chat2;
    public static Message message2Chat2;
    public static Message message3Chat2;

    public static List<Chat> allChats;
    public static List<Message> allMessages;
    public static List<Peer> allPeers;

    private static final long FIRST_DECEMBER_2014_MID_DAY = 1417392000;
    private static final long ONE_MINUTE = 60;
    private static final long TWO_MINUTES = 120;

    private static boolean initiated = false;

    /**
     * Create the Models
     */
    public static void initiate() {

        //Generate the user
        user = new User();
        user.setUserName("user");
        user.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
        user.setStatusMessage("status user");


        //Generate the peers
        peer1 = new Peer();
        peer1.setUserName("peer1");
        peer1.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
        peer1.setStatusMessage("status1");

        peer2 = new Peer();
        peer2.setUserName("peer2");
        peer2.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
        peer2.setStatusMessage("status2");

        peer3 = new Peer();
        peer3.setUserName("peer3");
        peer3.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
        peer3.setStatusMessage("status3");

        byte[] peer1Id = peer1.getPublicKey().getBytes();
        byte[] peer2Id = peer2.getPublicKey().getBytes();
        byte[] peer3Id = peer3.getPublicKey().getBytes();

        allPeers = new ArrayList<>();
        allPeers.add(peer1);
        allPeers.add(peer2);
        allPeers.add(peer3);


        //Generate the chats
        chat1 = new Chat();
        chat1.setHost(peer1);
        chat1.setPrivate(true);

        chat2 = new Chat();
        chat2.setHost(peer2);
        chat2.setPrivate(false);

        long chat1Id = chat1.getId();
        long chat2Id = chat2.getId();

        allChats = new ArrayList<>();
        allChats.add(chat1);
        allChats.add(chat2);


        //Generate the messages for the first chat
        message1Chat1 = new Message(chat1Id, "Chat 1: Message 1", peer1Id, FIRST_DECEMBER_2014_MID_DAY);
        message2Chat1 = new Message(chat1Id, "Chat 1: Message 2", peer2Id, FIRST_DECEMBER_2014_MID_DAY + ONE_MINUTE);

        //Generate the messages for the second chat
        message1Chat2 = new Message(chat2Id, "Chat 2: Message 1", peer1Id, FIRST_DECEMBER_2014_MID_DAY);
        message2Chat2 = new Message(chat2Id, "Chat 2: Message 2", peer2Id, FIRST_DECEMBER_2014_MID_DAY + ONE_MINUTE);
        message3Chat2 = new Message(chat2Id, "Chat 2: Message 2", peer3Id, FIRST_DECEMBER_2014_MID_DAY + TWO_MINUTES);

        allMessages = new ArrayList<>();
        allMessages.add(message1Chat1);
        allMessages.add(message2Chat1);
        allMessages.add(message1Chat2);
        allMessages.add(message2Chat2);
        allMessages.add(message3Chat2);

        initiated = true;
    }

    /**
     * Insert the Models into the Repositories
     *
     * @param chatRepositoryMock The mocked ChatRepository
     * @param messageRepositoryMock The mocked MessageRepository
     * @param peerRepositoryMock The mocked PeerRepository
     */
    public static void populate(ChatRepository chatRepositoryMock,
                                MessageRepository messageRepositoryMock,
                                PeerRepository peerRepositoryMock,
                                UserRepository userRepositoryMock) {

        if (!initiated) {
            initiate();
        }

        try {
            peerRepositoryMock.insert(peer1);
            peerRepositoryMock.insert(peer2);
            peerRepositoryMock.insert(peer3);

            chatRepositoryMock.insert(chat1);
            chatRepositoryMock.insert(chat2);

            messageRepositoryMock.insert(message1Chat1);
            messageRepositoryMock.insert(message2Chat1);

            messageRepositoryMock.insert(message1Chat2);
            messageRepositoryMock.insert(message2Chat2);
            messageRepositoryMock.insert(message3Chat2);
        } catch (InvalidModelException | InsertException e) {
            e.printStackTrace();
        }
    }
}
