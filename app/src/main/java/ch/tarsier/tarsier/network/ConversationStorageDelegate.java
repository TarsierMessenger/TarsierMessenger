package ch.tarsier.tarsier.network;

import com.google.common.base.Optional;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * Created by fred on 09/11/14.
 */
public interface ConversationStorageDelegate {
    public void storeMessage(String message, Peer peer, Optional<String> discussion);
}
