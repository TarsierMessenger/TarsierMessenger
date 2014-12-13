package ch.tarsier.tarsier.test;

import java.util.ArrayList;

import ch.tarsier.tarsier.crypto.EC25519;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.model.value.PublicKey;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;

/**
 * @author gluthier
 */
public class FillDatabaseWithFictionalDataForTesting {

    private static final long FIRST_DECEMBER_2014_MID_DAY = 1417392000;
    private static final long ONE_MINUTE = 60;
    private static final long TWO_MINUTE = 120;
    private static final long THREE_MINUTE = 180;

    public static void populate(ChatRepository chatRepositoryMock,
                                MessageRepository messageRepositoryMock,
                                PeerRepository peerRepositoryMock) {

        //Generate the peers
        Peer peer1 = new Peer();
        peer1.setUserName("peer1");
        peer1.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
        peer1.setStatusMessage("status1");

        Peer peer2 = new Peer();
        peer2.setUserName("peer2");
        peer2.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
        peer2.setStatusMessage("status2");

        Peer peer3 = new Peer();
        peer3.setUserName("peer3");
        peer3.setPublicKey(new PublicKey(EC25519.generateKeyPair().getPublicKey()));
        peer3.setStatusMessage("status3");

        byte[] user1Id = peer1.getPublicKey().getBytes();
        byte[] user2Id = peer2.getPublicKey().getBytes();
        byte[] user3Id = peer3.getPublicKey().getBytes();

        try {
            peerRepositoryMock.insert(peer1);
            peerRepositoryMock.insert(peer2);
            peerRepositoryMock.insert(peer3);
        } catch (InsertException | InvalidModelException e) {
            e.printStackTrace();
        }

        //Generate the chats
        Chat chat1 = new Chat();
        chat1.setHost(peer1);
        chat1.setPrivate(true);

        Chat chat2 = new Chat();
        chat2.setHost(peer2);
        chat2.setPrivate(false);

        long chat1Id = chat1.getId();
        long chat2Id = chat2.getId();

        try {
            chatRepositoryMock.insert(chat1);
            chatRepositoryMock.insert(chat2);
        } catch (InvalidModelException | InsertException e) {
            e.printStackTrace();
        }

        //Generate the messages for the first chat
        ArrayList<Message> messagesChat1 = new ArrayList<Message>();

        messagesChat1.add(new Message(chat1Id, "Chat 1: Message 1", user1Id, FIRST_DECEMBER_2014_MID_DAY));
        messagesChat1.add(new Message(chat1Id, "Chat 1: Message 2", user2Id, FIRST_DECEMBER_2014_MID_DAY + ONE_MINUTE));
        messagesChat1.add(new Message(chat1Id, "Chat 1: Message 3", user1Id, FIRST_DECEMBER_2014_MID_DAY + TWO_MINUTE));
        messagesChat1.add(new Message(chat1Id, "Chat 1: Message 4", user2Id, FIRST_DECEMBER_2014_MID_DAY + THREE_MINUTE));

        try {
            for (Message m : messagesChat1) {
                messageRepositoryMock.insert(m);
            }
        } catch (InvalidModelException | InsertException e) {
            e.printStackTrace();
        }

        //Generate the messages for the second chat
        ArrayList<Message> messagesChat2 = new ArrayList<Message>();

        messagesChat2.add(new Message(chat2Id, "Chat 2: Message 1", user1Id, FIRST_DECEMBER_2014_MID_DAY));
        messagesChat2.add(new Message(chat2Id, "Chat 2: Message 2", user2Id, FIRST_DECEMBER_2014_MID_DAY + ONE_MINUTE));
        messagesChat2.add(new Message(chat2Id, "Chat 2: Message 3", user3Id, FIRST_DECEMBER_2014_MID_DAY + TWO_MINUTE));
        messagesChat2.add(new Message(chat2Id, "Chat 2: Message 4", user3Id, FIRST_DECEMBER_2014_MID_DAY + THREE_MINUTE));

        try {
            for (Message m : messagesChat2) {
                messageRepositoryMock.insert(m);
            }
        } catch (InvalidModelException | InsertException e) {
            e.printStackTrace();
        }
    }
}
