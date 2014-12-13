package ch.tarsier.tarsier.test.repository;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.crypto.KeyPair;
import ch.tarsier.tarsier.domain.model.User;
import ch.tarsier.tarsier.domain.repository.UserRepository;
import ch.tarsier.tarsier.prefs.UserPreferences;
import ch.tarsier.tarsier.test.TarsierTestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author gluthier
 */
public class UserRepositoryTest extends TarsierTestCase<UserRepository> {

    private UserPreferences mUserPreferencesMock;
    private UserRepository mUserRepository;
    private User mDummyUser;

    public UserRepositoryTest() {
        super(UserRepository.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mUserPreferencesMock = mock(UserPreferences.class);
        when(mUserPreferencesMock.getUsername()).thenReturn("Dummy username");
        when(mUserPreferencesMock.getStatusMessage()).thenReturn("Dummy status");
        when(mUserPreferencesMock.getKeyPair()).thenReturn(new KeyPair(new byte[]{0, 1}, new byte[]{1, 1}));

        mDummyUser = new User();
        mDummyUser.setUserName("Dummy username");
        mDummyUser.setStatusMessage("Dummy status");

        Tarsier.app().setUserPreferences(mUserPreferencesMock);

        mUserRepository = Tarsier.app().getUserRepository();
    }

    public void testGetUser() {
        User userFromRepository = mUserRepository.getUser();

        assertNotNull(userFromRepository);
        assertEquals(mDummyUser.getUserName(), userFromRepository.getUserName());
        assertEquals(mDummyUser.getStatusMessage(), userFromRepository.getStatusMessage());
    }
}
