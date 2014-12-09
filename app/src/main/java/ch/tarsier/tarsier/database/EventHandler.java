package ch.tarsier.tarsier.database;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.util.Log;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.event.ReceivedChatroomPeersListEvent;
import ch.tarsier.tarsier.exception.InsertException;
import ch.tarsier.tarsier.exception.InvalidModelException;

/**
 * @author romac
 */
public class EventHandler {

    private static final String TAG = "Database.EventHandler";

    private final PeerRepository mPeerRepository;
    private final Bus mEventBus;

    public EventHandler() {
        mEventBus = Tarsier.app().getEventBus();
        mPeerRepository = Tarsier.app().getPeerRepository();
    }

    public void register() {
        mEventBus.register(this);
    }

    public void unregister() {
        mEventBus.unregister(this);
    }

    @Subscribe
    public void onReceivedChatroomPeersListEvent(ReceivedChatroomPeersListEvent event) {
        Log.d(TAG, "Got ReceivedChatroomPeersListEvent");

        try {
            mPeerRepository.insertAll(event.getPeers());
        } catch (InvalidModelException | InsertException e) {
            e.printStackTrace();
        }
    }

}
