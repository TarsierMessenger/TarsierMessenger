package ch.tarsier.tarsier.network;

import com.google.common.base.Optional;

import ch.tarsier.tarsier.domain.model.Peer;

/**
 * @author FredericJacobs
 */
public interface ChatStorageDelegate {

    void storeMessage(String message, Peer peer, Optional<String> discussion);
}
