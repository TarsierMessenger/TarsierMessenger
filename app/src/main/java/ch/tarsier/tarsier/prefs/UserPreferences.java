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
        return Tarsier.app().getString(R.string.personnal_file_key);
    }

    public KeyPair getKeyPair() {
        if (mKeyPair != null) {
            return mKeyPair;
        } else {
            byte[] publicKey = getString(R.string.personnal_file_public_key).getBytes();
            byte[] privateKey = getString(R.string.personnal_file_private_key).getBytes();
            return new KeyPair(publicKey, privateKey);
        }
    }

    /**
     * Generate a KeyPair and store it in the user preferences
     */
    public void setKeyPair() {
        if (getString(R.string.personnal_file_public_key) == null) {
            mKeyPair = EC25519.generateKeyPair();
            setString(R.string.personnal_file_public_key, new String(mKeyPair.getPublicKey()));
            setString(R.string.personnal_file_private_key, new String(mKeyPair.getPrivateKey()));
        } else {
            //TODO : throw exception because we can't regenerate a new keyPair
        }
    }

    public String getUsername() {
        return getString(R.string.personnal_file_key_myusername);
    }

    public void setUsername(String username) {
        setString(R.string.personnal_file_key_myusername, username);
    }

    public String getStatusMessage() {
        return getString(R.string.personnal_file_key_mymood);
    }

    public void setStatusMessage(String statusMessage) {
        setString(R.string.personnal_file_key_mymood, statusMessage);
    }

    public String getBackground() {
        return getString(R.string.preferences_file_key_background);
    }

    public String getSound() {
        return getString(R.string.preferences_file_key_sound);
    }

    public String getVibration() {
        return getString(R.string.preferences_file_key_vibration);
    }

    public String getPicturePath() {
        return Environment.getExternalStorageDirectory()
               + "/" + PROFILE_PICTURE_NAME;
    }

    public Uri getPictureUri() {
        return Uri.parse(getPicturePath());
    }
}
