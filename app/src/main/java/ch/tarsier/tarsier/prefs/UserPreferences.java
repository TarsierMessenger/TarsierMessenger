package ch.tarsier.tarsier.prefs;

import android.net.Uri;
import android.os.Environment;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.crypto.EC25519;
import ch.tarsier.tarsier.crypto.KeyPair;

/**
 * @author romac
 *
 * TODO: Add method to set the profile picture
 *       rather than doing it in AddProfilePictureActivity.
 */
public class UserPreferences extends AbstractPreferences {

    public static final String PROFILE_PICTURE_NAME = "profile_picture_temp.png";
    private KeyPair mKeyPair;

    @Override
    protected String getPreferencesFile() {
        return Tarsier.app().getString(R.string.pref_filename);
    }

    public KeyPair getKeyPair() {
        if (mKeyPair == null) {
            if (!hasPublicKey()) {
                mKeyPair = generateKeyPair();
            } else {
                mKeyPair = fetchKeyPair();
            }
        }

        return mKeyPair;
    }

    /**
     * Generate a KeyPair and store it in the user preferences
     */
    private KeyPair generateKeyPair() {
        KeyPair keyPair = EC25519.generateKeyPair();

        setString(R.string.pref_public_key, keyPair.getBase64EncodedPublicKey());
        setString(R.string.pref_private_key, keyPair.getBase64EncodedPrivateKey());

        return keyPair;
    }

    private KeyPair fetchKeyPair() {
        String publicKey = getString(R.string.pref_public_key);
        String privateKey = getString(R.string.pref_private_key);

        return new KeyPair(publicKey, privateKey);
    }

    private boolean hasPublicKey() {
        String publicKey = getString(R.string.pref_public_key);

        return publicKey != null && !publicKey.equals("");
    }

    public String getUsername() {
        return getString(R.string.pref_username);
    }

    public void setUsername(String username) {
        setString(R.string.pref_username, username);
    }

    public String getStatusMessage() {
        return getString(R.string.pref_status_message);
    }

    public void setStatusMessage(String statusMessage) {
        setString(R.string.pref_status_message, statusMessage);
    }

    public String getPicturePath() {
        return Environment.getExternalStorageDirectory()
               + "/" + PROFILE_PICTURE_NAME;
    }

    public Uri getPictureUri() {
        return Uri.parse(getPicturePath());
    }

    public boolean isDatabaseEmpty() {
        return getBoolean(R.string.pref_database_filled, true);
    }

    public void setIsDatabaseEmpty(boolean isDatabaseEmpty) {
        setBoolean(R.string.pref_database_filled, isDatabaseEmpty);
    }
}
