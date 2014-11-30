package ch.tarsier.tarsier.util;

import java.util.Comparator;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;

/**
 * @author gluthier
 */
public class ChatLastMessageDateSorter implements Comparator<Chat> {
    @Override
    public int compare(Chat chat1, Chat chat2) {
        long timeChat1 = getTimeLastMessageOf(chat1);
        long timeChat2 = getTimeLastMessageOf(chat2);

        if (timeChat1 > timeChat2) {
            return -1;
        } else if (timeChat1 < timeChat2) {
            return 1;
        } else {
            return 0;
        }
    }

    private long getTimeLastMessageOf(Chat chat) {
        MessageRepository messageRepository = Tarsier.app().getMessageRepository();

        long time = 0;
        try {
            time = messageRepository.getLastMessageOf(chat).getDateTime();
        } catch (NoSuchModelException e) {
            e.printStackTrace();
        } catch (InvalidModelException e) {
            e.printStackTrace();
        }

        return time;
    }
}
