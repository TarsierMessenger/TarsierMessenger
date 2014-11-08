package ch.tarsier.tarsier.network;

/**
 * Created by amirreza on 11/8/14.
 */
public interface MessageHandler {
    public void handleMessage(byte[] message);
}
