package ch.tarsier.tarsier.database;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.util.Log;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.event.DisplayMessageEvent;
import ch.tarsier.tarsier.event.ReceivedChatroomPeersListEvent;
import ch.tarsier.tarsier.event.ReceivedMessageEvent;
import ch.tarsier.tarsier.event.SendMessageEvent;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;
import ch.tarsier.tarsier.exception.NoSuchModelException;
import ch.tarsier.tarsier.util.DateUtil;

/**
 * EventHandler is the class that handles the events for the database.
 *
 * @author romac
 */
public class EventHandler {

    private static final String TAG = "Database.EventHandler";

    private final PeerRepository mPeerRepository;
    private final ChatRepository mChatRepository;
    private final MessageRepository mMessageRepository;

    private final Bus mEventBus;

    /**
     * Constructs a new instance of the EventHandler class,
     * fetching its dependencies from the Tarsier.app() singleton.
     */
    public EventHandler() {
        mEventBus = Tarsier.app().getEventBus();
        mPeerRepository = Tarsier.app().getPeerRepository();
        mChatRepository = Tarsier.app().getChatRepository();
        mMessageRepository = Tarsier.app().getMessageRepository();
    }

    /**
     * Register this instance with the event bus.
     */
    public void register() {
        mEventBus.register(this);
    }

    /**
     * Unregister this instance from the event bus.
     */
    public void unregister() {
        mEventBus.unregister(this);
    }

    /**
     * When the user receives a message (either private or public), add the message to
     * the database, along with the appropriate chatroom (that will be created if it doesn't
     * exist yet).
     *
     * @param event The event representing a received message.
     */
    @Subscribe
    public void onReceivedMessageEvent(ReceivedMessageEvent event) {
        Log.d(TAG, "Got ReceivedMessageEvent");

        try {
            Chat chat;

            if (event.isPrivate()) {
                chat = mChatRepository.findPrivateChatForPeer(event.getSender());
            } else {
                chat = mChatRepository.findPublicChat();
            }

            Message message = new Message(
                    chat.getId(),
                    event.getMessage(),
                    event.getSender().getPublicKey(),
                    DateUtil.getNowTimestamp()
            );

            mMessageRepository.insert(message);
            mEventBus.post(new DisplayMessageEvent(message, event.getSender(), chat));

        } catch (NoSuchModelException | InvalidModelException e) {
            Log.d(TAG, "Could not find chat for given peer.");
            e.printStackTrace();
        } catch (InsertException e) {
            Log.d(TAG, "Could not find insert received message into chatroom.");
            e.printStackTrace();
        }
    }

    /**
     * When the user sends a message, insert it into the database.
     *
     * @param event The event representing the sent message.
     */
    @Subscribe
    public void onSendMessageEvent(SendMessageEvent event) {
        try {
            mMessageRepository.insert(event.getMessage());
        } catch (InvalidModelException | InsertException e) {
            Log.d(TAG, "Could not insert message being sent in the database.");
            e.printStackTrace();
        }
    }

    /**
     * When the list of peers in the public chatroom is received,
     * insert the peers we haven't ever seen into the database.
     *
     * @param event The event representing the new peers list.
     */
    @Subscribe
    public void onReceivedChatroomPeersListEvent(ReceivedChatroomPeersListEvent event) {
        Log.d(TAG, "Got ReceivedChatroomPeersListEvent");

        try {
            for (Peer peer : event.getPeers()) {
                mPeerRepository.insertIfNotExistsWithPublicKey(peer);
            }
        } catch (InvalidModelException | InsertException e) {
            Log.d(TAG, "Could not insert new peers in the database.");
            e.printStackTrace();
        }
    }

}
