package ch.tarsier.tarsier.test;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.exception.NoSuchModelException;

/**
 * @author gluthier
 */
public class MessageRepositoryTest extends AndroidTestCase {

    MessageRepository messageRepository = Tarsier.app().getMessageRepository();

    public void testIllegalIdForFindById() {
        long[] illegalIds = {0, -1};

        for (long id : illegalIds) {
            try {
                messageRepository.findById(id);
                fail("Expecting NoSuchModelException but none was thrown.");
            } catch (NoSuchModelException e) {
                assertEquals("Message ID cannot be < 1", e.getMessage());
            }
        }
    }
}
