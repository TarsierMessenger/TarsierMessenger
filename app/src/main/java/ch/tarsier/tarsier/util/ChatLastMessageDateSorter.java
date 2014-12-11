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
        long timeChat1 = 0;
        long timeChat2 = 0;

        try {
            timeChat1 = getTimeLastMessageOf(chat1);
            timeChat2 = getTimeLastMessageOf(chat2);
        } catch (InvalidModelException e) {
            e.printStackTrace();
        }

        if (timeChat1 > timeChat2) {
            return -1;
        } else if (timeChat1 < timeChat2) {
            return 1;
        } else {
            return 0;
        }
    }

    private long getTimeLastMessageOf(Chat chat) throws InvalidModelException {
        if (chat == null) {
            throw new InvalidModelException("Chat is null");
        }

        MessageRepository messageRepository = Tarsier.app().getMessageRepository();

        long time = 0;
        try {
            time = messageRepository.getLastMessageOf(chat).getDateTime();
        } catch (NoSuchModelException | InvalidModelException e) {
            e.printStackTrace();
        }

        return time;
    }
}
