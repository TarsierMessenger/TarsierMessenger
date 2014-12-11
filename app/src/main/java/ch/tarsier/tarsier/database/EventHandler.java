package ch.tarsier.tarsier.database;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.util.Log;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.Message;
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
 * @author romac
 */
public class EventHandler {

    private static final String TAG = "Database.EventHandler";

    private final PeerRepository mPeerRepository;
    private final ChatRepository mChatRepository;
    private final MessageRepository mMessageRepository;

    private final Bus mEventBus;

    public EventHandler() {
        mEventBus = Tarsier.app().getEventBus();
        mPeerRepository = Tarsier.app().getPeerRepository();
        mChatRepository = Tarsier.app().getChatRepository();
        mMessageRepository = Tarsier.app().getMessageRepository();
    }

    public void register() {
        mEventBus.register(this);
    }

    public void unregister() {
        mEventBus.unregister(this);
    }

    @Subscribe
    public void onReceivedMessageEvent(ReceivedMessageEvent event) {
        Log.d(TAG, "Got ReceivedMessageEvent");

        try {
            Chat chat = mChatRepository.findChatForPeer(event.getSender(), event.isPrivate());

            Message message = new Message(
                chat.getId(),
                event.getMessage(),
                event.getSender().getPublicKey(),
                DateUtil.getNowTimestamp()
            );

            mMessageRepository.insert(message);
            mEventBus.post(new DisplayMessageEvent(message));

        } catch (NoSuchModelException | InvalidModelException e) {
            Log.d(TAG, "Could not find chat for given peer.");
            e.printStackTrace();
        } catch (InsertException e) {
            Log.d(TAG, "Could not find insert received message into chatroom.");
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onSendMessageEvent(SendMessageEvent event) {
        try {
            mMessageRepository.insert(event.getMessage());
        } catch (InvalidModelException | InsertException e) {
            Log.d(TAG, "Could not insert message being sent in the database.");
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onReceivedChatroomPeersListEvent(ReceivedChatroomPeersListEvent event) {
        Log.d(TAG, "Got ReceivedChatroomPeersListEvent");

        try {
            mPeerRepository.insertAll(event.getPeers());
        } catch (InvalidModelException | InsertException e) {
            Log.d(TAG, "Could not insert new peers in the database.");
            e.printStackTrace();
        }
    }

}
