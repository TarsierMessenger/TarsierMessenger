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
import ch.tarsier.tarsier.exception.InvalidCursorException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author benjamin on 18/11/14.
 * @author gluthier
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
                peerRepositoryMock,
                userRepositoryMock);

        Peer peer1 = FillDBForTesting.peer1;
        Peer peer2 = FillDBForTesting.peer2;
        Peer peer3 = FillDBForTesting.peer3;

        Chat chat1 = FillDBForTesting.chat1;
        Chat chat2 = FillDBForTesting.chat2;

        Message message1Chat1 = FillDBForTesting.message1Chat1;
        Message message2Chat1 = FillDBForTesting.message2Chat1;
        Message message1Chat2 = FillDBForTesting.message1Chat2;
        Message message2Chat2 = FillDBForTesting.message2Chat2;
        Message message3Chat2 = FillDBForTesting.message3Chat2;


        when(chatRepositoryMock.findAll()).thenReturn(FillDBForTesting.allChats);
        when(chatRepositoryMock.findById(chat1.getId())).thenReturn(chat1);
        when(chatRepositoryMock.findById(chat2.getId())).thenReturn(chat2);
        when(chatRepositoryMock.findPrivateChatForPeer(peer1)).thenReturn(chat1);
        when(chatRepositoryMock.findPrivateChatForPeer(peer2)).thenThrow(NoSuchModelException.class);
        when(chatRepositoryMock.findPrivateChatForPeer(peer3)).thenThrow(NoSuchModelException.class);
        when(chatRepositoryMock.findChatForPeer(peer1, true)).thenReturn(chat1);
        when(chatRepositoryMock.findChatForPeer(peer1, false)).thenThrow(NoSuchModelException.class);
        when(chatRepositoryMock.findChatForPeer(peer2, true)).thenThrow(NoSuchModelException.class);
        when(chatRepositoryMock.findChatForPeer(peer2, false)).thenReturn(chat2);
        when(chatRepositoryMock.findChatForPeer(peer3, true)).thenThrow(NoSuchModelException.class);
        when(chatRepositoryMock.findChatForPeer(peer3, false)).thenThrow(NoSuchModelException.class);

        when(messageRepositoryMock.findAll()).thenReturn(FillDBForTesting.allMessages);
        when(messageRepositoryMock.findById(message1Chat1.getId())).thenReturn(message1Chat1);
        when(messageRepositoryMock.findById(message2Chat1.getId())).thenReturn(message2Chat1);
        when(messageRepositoryMock.findById(message1Chat2.getId())).thenReturn(message1Chat2);
        when(messageRepositoryMock.findById(message2Chat2.getId())).thenReturn(message2Chat2);
        when(messageRepositoryMock.findById(message3Chat2.getId())).thenReturn(message3Chat2);
        when(messageRepositoryMock.getLastMessageOf(chat1)).thenReturn(message2Chat1);
        when(messageRepositoryMock.getLastMessageOf(chat2)).thenReturn(message3Chat2);

        when(peerRepositoryMock.findAll()).thenReturn(FillDBForTesting.allPeers);
        when(peerRepositoryMock.findById(peer1.getId())).thenReturn(peer1);
        when(peerRepositoryMock.findById(peer2.getId())).thenReturn(peer2);
        when(peerRepositoryMock.findById(peer3.getId())).thenReturn(peer3);
        when(peerRepositoryMock.findByPublicKey(peer1.getPublicKey().getBytes())).thenReturn(peer1);
        when(peerRepositoryMock.findByPublicKey(peer2.getPublicKey().getBytes())).thenReturn(peer2);
        when(peerRepositoryMock.findByPublicKey(peer3.getPublicKey().getBytes())).thenReturn(peer3);
        when(peerRepositoryMock.findByPublicKey(peer1.getPublicKey())).thenReturn(peer1);
        when(peerRepositoryMock.findByPublicKey(peer2.getPublicKey())).thenReturn(peer2);
        when(peerRepositoryMock.findByPublicKey(peer3.getPublicKey())).thenReturn(peer3);

        when(userRepositoryMock.getUser()).thenReturn(FillDBForTesting.user);


        Tarsier.app().setChatRepository(chatRepositoryMock);
        Tarsier.app().setMessageRepository(messageRepositoryMock);
        Tarsier.app().setPeerRepository(peerRepositoryMock);
        Tarsier.app().setUserRepository(userRepositoryMock);
    }
}
