package ch.tarsier.tarsier;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.database.FillDatabaseWithFictionalData;
import ch.tarsier.tarsier.domain.model.Message;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.exception.NoSuchModelException;
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

    public static Tarsier app() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        mDatabase = new Database(getApplicationContext());

        new FillDatabase().execute();
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

    // Reset the Tarsier.app() singleton [for testing purpose only]
    public void reset() {
        setUserPreferences(null);
        setDatabase(null);

        setPeerRepository(null);
        setChatRepository(null);
        setMessageRepository(null);

        Tarsier.app().onCreate();
    }

    private class FillDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            while (!Tarsier.app().getDatabase().isReady()) { }

            FillDatabaseWithFictionalData.populate();

            return null;
        }
    }
}
