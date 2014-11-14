package ch.tarsier.tarsier.prefs;

import android.net.Uri;
import android.os.Environment;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;

/**
 * @author romac
 */
public class UserPreferences extends AbstractPreferences {

    public static final String PROFILE_PICTURE_NAME = "profile_picture_temp.png";

    @Override
    protected String getPreferencesFile() {
        return Tarsier.app().getString(R.string.personnal_file_key);
    }

    public String getUsername() {
        return getString(R.string.personnal_file_key_myusername);
    }

    public String getStatusMessage() {
        return getString(R.string.personnal_file_key_mymood);
    }

    public String getPicturePath() {
        return Environment.getExternalStorageDirectory()
               + "/" + PROFILE_PICTURE_NAME;
    }

    public Uri getPictureUri() {
        return Uri.parse(getPicturePath());
    }
}
