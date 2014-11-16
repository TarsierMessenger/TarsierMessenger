package ch.tarsier.tarsier;

import android.app.Application;

import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.prefs.UserPreferences;
import ch.tarsier.tarsier.storage.StorageAccess;

/**
 * @author Romain Ruetschi
 */
public class Tarsier extends Application {

    private static Tarsier app;

    private StorageAccess mStorage;
    private UserPreferences mUserPreferences;
    private Database mDatabase;

    private PeerRepository mPeerRepository;
    private ChatRepository mChatRepository;
    private MessageRepository mMessageRepository;

    public static Tarsier app() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        mDatabase = new Database(getApplicationContext());
    }

    public StorageAccess getStorage() {
        if (mStorage == null) {
            mStorage = new StorageAccess(getApplicationContext());
        }

        return mStorage;
    }

    public UserPreferences getUserPreferences() {
        if (mUserPreferences == null) {
            mUserPreferences = new UserPreferences();
        }

        return mUserPreferences;
    }

    public Database getDatabase() {
        return mDatabase;
    }

    public PeerRepository getPeerRepository() {
        if (mPeerRepository == null) {
            mPeerRepository = new PeerRepository(getDatabase());
        }

        return mPeerRepository;
    }

    public ChatRepository getChatRepository() {
        if (mChatRepository == null) {
            mChatRepository = new ChatRepository(getDatabase());
        }

        return mChatRepository;
    }

    public MessageRepository getMessageRepository() {
        if (mMessageRepository == null) {
            mMessageRepository = new MessageRepository(getDatabase());
        }

        return mMessageRepository;
    }
}
