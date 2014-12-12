package ch.tarsier.tarsier.notifications;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Peer;
import ch.tarsier.tarsier.event.ReceivedMessageEvent;

/**
 * @author romac
 */
public class Notifications {

    private final Bus mEventBus;

    private final Context mContext;

    public Notifications(Bus eventBus, Context context) {
        mContext = context;
        mEventBus = eventBus;
    }

    public void register() {
        mEventBus.register(this);
    }

    public void unregister() {
        mEventBus.unregister(this);
    }

    @Subscribe
    public void onReceivedMessageEvent(ReceivedMessageEvent event) {
        playGotMessageSound();
    }

    public void playGotMessageSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(mContext, notification);
            ringtone.play();
            ringtone.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
