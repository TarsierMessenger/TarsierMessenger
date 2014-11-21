package ch.tarsier.tarsier.test;

import android.test.AndroidTestCase;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.exception.NoSuchModelException;

/**
 * @author gluthier
 */
public class MessageRepositoryTest extends AndroidTestCase {

    MessageRepository mMessageRepository;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mMessageRepository = Tarsier.app().getMessageRepository();
    }

    public void testIllegalIdsForFindById() {
        long[] illegalIds = {0, -1, -9001};

        for (long id : illegalIds) {
            try {
                mMessageRepository.findById(id);
                fail("Expecting IllegalArgumentException but none was thrown.");
            } catch (IllegalArgumentException e) {
                assertEquals("Message ID cannot be < 1", e.getMessage());
            }catch (NoSuchModelException e) {
                fail("Expecting IllegalArgumentException, not NoSuchModelException.");
            }
        }
    }
}
