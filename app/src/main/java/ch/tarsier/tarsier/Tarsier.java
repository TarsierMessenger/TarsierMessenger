package ch.tarsier.tarsier;

import com.squareup.otto.Bus;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.util.Log;

import ch.tarsier.tarsier.database.Database;
import ch.tarsier.tarsier.database.FillDatabaseWithFictionalData;
import ch.tarsier.tarsier.domain.repository.ChatRepository;
import ch.tarsier.tarsier.domain.repository.MessageRepository;
import ch.tarsier.tarsier.domain.repository.PeerRepository;
import ch.tarsier.tarsier.domain.repository.UserRepository;
import ch.tarsier.tarsier.event.MainThreadBus;
import ch.tarsier.tarsier.network.MessagingManager;
import ch.tarsier.tarsier.prefs.UserPreferences;

/**
 * @author romac
 */
public class Tarsier extends Application {

    private static final String TARSIER_TAG = "TarsierApp";
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

        initDatabase();
        initNetwork();
    }

    private void initDatabase() {
        mDatabase = new Database(getApplicationContext());

        new FillDatabase().execute();
    }

    public void initNetwork() {
        Log.d(TARSIER_TAG, "Network initialized");
        WifiP2pManager mWifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        WifiP2pManager.Channel mWifiP2pChannel = mWifiP2pManager.initialize(this, getMainLooper(), null);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        MessagingManager mMessagingManager = new MessagingManager(mWifiP2pManager, mWifiP2pChannel);
        mMessagingManager.setEventBus(getEventBus());

        registerReceiver(mMessagingManager, intentFilter);
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
        if (mEventBus == null) {
            mEventBus = new MainThreadBus();
        }

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

    /*
     * FillDatabase is the class that fill the database with fictional data
     */
    private class FillDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            while (!Tarsier.app().getDatabase().isReady()) { }

            if (getUserPreferences().isDatabaseEmpty()) {
                // Pass true to force clear and populate the database,
                // even if there's already some data.
                FillDatabaseWithFictionalData.populate(false);
                getUserPreferences().setIsDatabaseEmpty(false);
            }

            return null;
        }
    }
}
