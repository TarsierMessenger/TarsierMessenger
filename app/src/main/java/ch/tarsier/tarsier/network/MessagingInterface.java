package ch.tarsier.tarsier.network;

import java.util.EventListener;
import java.util.List;
import java.util.Observer;

/**
 * Created by amirreza on 11/8/14.
 */
public interface MessagingInterface {
    public List<TarsierMember> getMembersList();
    public void broadcastMessage(byte[] message);
    //TODO: Integer may be changed by TarsierMember
    public void sendMessage(Integer member, byte[] message);

    public void registerReceiveMessageHandler(MessageHandler handler);
    public void registerMemberChangeHandler(MessageHandler handler);
}
