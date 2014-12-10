package ch.tarsier.tarsier.domain.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;

/**
 * @author McMoudi
 * @author romac
 */
public class Chat implements Serializable {

    private static final String DEFAULT_TITLE = "'s chatroom";

    private long mId;
    private String mTitle;
    private Peer mHost;
    private boolean mPrivate;

    public Chat() {
        mId = -1;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    /**
     * @return the other peer in a private chat or the name of the chatroom in a chatroom.
     */
    public String getTitle() {
        if (isPrivate()) {
            return getHost().getUserName();
        } else {
            if (mTitle != null) {
                return mTitle;
            } else {
                return getHost().getUserName() + DEFAULT_TITLE;
            }
        }
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isPrivate() {
        return mPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        mPrivate = isPrivate;
    }

    public Peer getHost() {
        return mHost;
    }

    public void setHost(Peer host) {
        mHost = host;
    }

    public boolean isHost(Peer peer) {
        return !(peer == null || getHost() == null) && getHost().getId() == peer.getId();

    }

    public Bitmap getPicture() {
        if (isPrivate()) {
            return getHost().getPicture();
        } else {
            return BitmapFactory.decodeResource(Tarsier.app().getResources(),
                    R.drawable.tarsier_group_placeholder);
        }
    }
}
