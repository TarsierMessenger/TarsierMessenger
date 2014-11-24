package ch.tarsier.tarsier;

import com.squareup.otto.Bus;

import android.app.Application;

import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.domain.repository.UserRepository;
import ch.tarsier.tarsier.prefs.UserPreferences;

/**
 * @author romac
 */
public class Tarsier extends Application {

    private static Tarsier app;

    private UserPreferences mUserPreferences;
    private Database mDatabase;

    private PeerRepository mPeerRepository;
    private ChatRepository mChatRepository;
    private MessageRepository mMessageRepository;
    private UserRepository mUserRepository;

    private Bus mEventBus;

    public static Tarsier app() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        mDatabase = new Database(getApplicationContext());
    }

    public UserPreferences getUserPreferences() {
        if (mUserPreferences == null) {
            mUserPreferences = new UserPreferences();
        }

        return mUserPreferences;
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        mUserPreferences = userPreferences;
    }

    public Database getDatabase() {
        return mDatabase;
    }

    public void setDatabase(Database database) {
        mDatabase = database;
    }

    public PeerRepository getPeerRepository() {
        if (mPeerRepository == null) {
            mPeerRepository = new PeerRepository(getDatabase());
        }

        return mPeerRepository;
    }

    public void setPeerRepository(PeerRepository peerRepository) {
        mPeerRepository = peerRepository;
    }

    public ChatRepository getChatRepository() {
        if (mChatRepository == null) {
            mChatRepository = new ChatRepository(getDatabase());
        }

        return mChatRepository;
    }

    public void setChatRepository(ChatRepository chatRepository) {
        mChatRepository = chatRepository;
    }

    public MessageRepository getMessageRepository() {
        if (mMessageRepository == null) {
            mMessageRepository = new MessageRepository(getDatabase());
        }

        return mMessageRepository;
    }

    public void setMessageRepository(MessageRepository messageRepository) {
        mMessageRepository = messageRepository;
    }

    public Bus getEventBus() {
        return mEventBus;
    }

    public void setEventBus(Bus eventBus) {
        mEventBus = eventBus;
    }

    public UserRepository getUserRepository() {
        if (mUserRepository == null) {
            mUserRepository = new UserRepository();
        }

        return mUserRepository;
    }

    // Reset the Tarsier.app() singleton [for testing purpose only]
    public void reset() {
        setUserPreferences(null);
        setDatabase(null);

        setPeerRepository(null);
        setChatRepository(null);
        setMessageRepository(null);

        Tarsier.app().onCreate();
    }
}
