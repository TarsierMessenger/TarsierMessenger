package ch.tarsier.tarsier.test;

import android.test.ActivityInstrumentationTestCase2;

import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.domain.repository.UserRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by benjamin on 18/11/14.
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

        chatRepositoryMock = mock(ChatRepository.class);
        messageRepositoryMock = mock(MessageRepository.class);
        peerRepositoryMock = mock(PeerRepository.class);
        userRepositoryMock = mock(UserRepository.class);

        when(chatRepositoryMock.insert(new Chat())).then(mockInsertChatRepository());
    }

    public void mockInsertChatRepository() {

    }
}
