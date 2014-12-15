package ch.tarsier.tarsier.test;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import ch.tarsier.tarsier.crypto.EC25519;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.User;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.util.DateUtil;

/**
 * The FillDBForTesting class is used to fill the mocked database for testing purposes.
 *
 * @author gluthier
 */
public class FillDBForTesting {

    private static final String TAG = "FillDBForTesting";

    public static User user;

    private static final int NUMBER_OF_PEERS = 3;
    public static Peer[] peers;

    public static Chat chat2;
    public static Chat chat1;

    private static final int NUMBER_OF_MESSAGES = 75;
    public static Message[] messagesChat1;
    public static Message[] messagesChat2;

    public static List<Chat> allChats;
    public static List<Message> allMessages;
    public static List<Peer> allPeers;

    private static final long FIRST_DECEMBER_2014_MIDNIGHT = DateUtil.getFirstDecemberTimestamp();

    /**
     * Insert the Models into the Repositories
     *
     * @param chatRepository The ChatRepository
     * @param messageRepository The MessageRepository
     * @param peerRepository The PeerRepository
     */
    public static void populate(ChatRepository chatRepository,
                                MessageRepository messageRepository,
                                PeerRepository peerRepository) {

        //Generate the user
        user = new User();
        user.setUserName("user");
        user.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
        user.setStatusMessage("status user");


        peers = new Peer[NUMBER_OF_PEERS];
        for (int i=0; i<NUMBER_OF_PEERS; i++) {
            //Generate the peers
            Peer peer = new Peer();
            peer.setUserName("peer "+i);
            peer.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
            peer.setStatusMessage("status"+i);
            peers[i] = peer;
        }

        allPeers = new ArrayList<>();
        allPeers.addAll(Arrays.asList(peers));

        for (int i=0; i<NUMBER_OF_PEERS; i++) {
            try {
                peerRepository.insert(peers[i]);
            } catch (InvalidModelException | InsertException e) {
                e.printStackTrace();
            }
        }

        //Generate the chats
        chat1 = new Chat();
        chat1.setHost(peers[0]);
        chat1.setPrivate(false);

        chat2 = new Chat();
        chat2.setHost(peers[1]);
        chat2.setPrivate(true);

        allChats = new ArrayList<>();
        allChats.add(chat1);
        allChats.add(chat2);

        try {
            chatRepository.insert(chat1);
            Log.d(TAG, "Insert chat1 with id " + chat1.getId());
            chatRepository.insert(chat2);
            Log.d(TAG, "Insert chat2 with id " + chat2.getId());
        } catch (InvalidModelException | InsertException e) {
            e.printStackTrace();
        }


        //Generate the messages for both chats
        Random r = new Random();
        int randomPeerIndex;
        long nowTimeStamp = DateUtil.getNowTimestamp();
        int constantWidthInterval = (int) (nowTimeStamp - FIRST_DECEMBER_2014_MIDNIGHT) / NUMBER_OF_MESSAGES;
        long messageTimeStamp = FIRST_DECEMBER_2014_MIDNIGHT;
        messagesChat1 = new Message[NUMBER_OF_MESSAGES];
        messagesChat2 = new Message[NUMBER_OF_MESSAGES];
        for (int i=0; i<NUMBER_OF_MESSAGES; i++) {
            //Generate the messages for the first chat
            randomPeerIndex = r.nextInt(NUMBER_OF_PEERS);
            messagesChat1[i] = new Message(chat1.getId(), "Chat 1: Message "+i,
                    peers[randomPeerIndex].getPublicKey().getBytes(), messageTimeStamp);
            //Generate the messages for the second chat
            randomPeerIndex = r.nextInt(NUMBER_OF_PEERS);
            messagesChat2[i] = new Message(chat2.getId(), "Chat 2: Message "+i,
                    peers[randomPeerIndex].getPublicKey().getBytes(), messageTimeStamp);

            messageTimeStamp = messageTimeStamp + constantWidthInterval;
        }

        allMessages = new ArrayList<>();
        allMessages.addAll(Arrays.asList(messagesChat1));
        allMessages.addAll(Arrays.asList(messagesChat2));

        try {
            for (int i=0; i<NUMBER_OF_MESSAGES; i++) {
                messageRepository.insert(messagesChat1[i]);
                messageRepository.insert(messagesChat2[i]);
            }
        } catch (InvalidModelException | InsertException e) {
            e.printStackTrace();
        }
    }
}
