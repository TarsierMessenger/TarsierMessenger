package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.domain.repository.UserRepository;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TarsierTestCase provides a custom TestCase for the app.
 *
 * @author benjamin on 18/11/14.
 * @author gluthier
 * @param <T> The type of the elements to test
 */
public class TarsierTestCase<T> extends ActivityInstrumentationTestCase2 {

    protected ChatRepository chatRepositoryMock;
    protected MessageRepository messageRepositoryMock;
    protected PeerRepository peerRepositoryMock;
    protected UserRepository userRepositoryMock;

    public TarsierTestCase(Class activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // XXX: Hack required to make Mockito work on Android
        if (getInstrumentation() != null && getInstrumentation().getTargetContext() != null
                && getInstrumentation().getTargetContext().getCacheDir() != null) {

            System.setProperty("dexmaker.dexcache",
                    getInstrumentation().getTargetContext().getCacheDir().getPath());
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        Tarsier.app().setChatRepository(null);
        Tarsier.app().setMessageRepository(null);
        Tarsier.app().setPeerRepository(null);
        Tarsier.app().setUserRepository(null);
    }

    /**
     * Mock the Repositories
     *
     * @throws NoSuchModelException If a Repository doesn't find a
     * @throws InvalidModelException If a Repository searches for and invalid Model
     */
    protected void mockRepositories()
            throws NoSuchModelException, InvalidModelException {

        chatRepositoryMock = mock(ChatRepository.class);
        messageRepositoryMock = mock(MessageRepository.class);
        peerRepositoryMock = mock(PeerRepository.class);
        userRepositoryMock = mock(UserRepository.class);


        FillDBForTesting.populate(chatRepositoryMock,
                messageRepositoryMock,
                peerRepositoryMock);

        Peer[] peers = FillDBForTesting.peers;

        Chat chat1 = FillDBForTesting.chat2;
        Chat chat2 = FillDBForTesting.chat1;

        Message[] messagesChat1 = FillDBForTesting.messagesChat1;
        Message[] messagesChat2 = FillDBForTesting.messagesChat2;


        when(chatRepositoryMock.findAll()).thenReturn(FillDBForTesting.allChats);
        when(chatRepositoryMock.findById(chat1.getId())).thenReturn(chat1);
        when(chatRepositoryMock.findById(chat2.getId())).thenReturn(chat2);
        when(chatRepositoryMock.findPrivateChatForPeer(peers[0])).thenReturn(chat1);
        when(chatRepositoryMock.findPrivateChatForPeer(peers[1])).thenThrow(NoSuchModelException.class);
        when(chatRepositoryMock.findPrivateChatForPeer(peers[2])).thenThrow(NoSuchModelException.class);
        when(chatRepositoryMock.findChatForPeer(peers[0], true)).thenReturn(chat1);
        when(chatRepositoryMock.findChatForPeer(peers[0], false)).thenThrow(NoSuchModelException.class);
        when(chatRepositoryMock.findChatForPeer(peers[1], true)).thenThrow(NoSuchModelException.class);
        when(chatRepositoryMock.findChatForPeer(peers[1], false)).thenReturn(chat2);
        when(chatRepositoryMock.findChatForPeer(peers[2], true)).thenThrow(NoSuchModelException.class);
        when(chatRepositoryMock.findChatForPeer(peers[2], false)).thenThrow(NoSuchModelException.class);

        when(messageRepositoryMock.findAll()).thenReturn(FillDBForTesting.allMessages);
        for (int i=0; i<messagesChat1.length; i++) {
            when(messageRepositoryMock.findById(messagesChat1[i].getId())).thenReturn(messagesChat1[i]);
            when(messageRepositoryMock.findById(messagesChat2[i].getId())).thenReturn(messagesChat2[i]);
        }
        when(messageRepositoryMock.getLastMessageOf(chat1)).thenReturn(messagesChat1[messagesChat1.length-1]);
        when(messageRepositoryMock.getLastMessageOf(chat2)).thenReturn(messagesChat2[messagesChat1.length-1]);

        when(peerRepositoryMock.findAll()).thenReturn(FillDBForTesting.allPeers);
        for (Peer peer : peers) {
            when(peerRepositoryMock.findById(peer.getId())).thenReturn(peer);
            when(peerRepositoryMock.findByPublicKey(peer.getPublicKey().getBytes())).thenReturn(peer);
            when(peerRepositoryMock.findByPublicKey(peer.getPublicKey())).thenReturn(peer);
        }

        when(userRepositoryMock.getUser()).thenReturn(FillDBForTesting.user);


        Tarsier.app().setChatRepository(chatRepositoryMock);
        Tarsier.app().setMessageRepository(messageRepositoryMock);
        Tarsier.app().setPeerRepository(peerRepositoryMock);
        Tarsier.app().setUserRepository(userRepositoryMock);
    }
}
