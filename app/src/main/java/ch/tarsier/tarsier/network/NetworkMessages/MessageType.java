package ch.tarsier.tarsier.network.networkMessages;

/**
 * Created by Freddy on 11/8/14.
 */
public class MessageType {
    final public static int SERVER_SOCKET           = 8888;
    final public static int MESSAGE_TYPE_HELLO      = 0;
    final public static int MESSAGE_TYPE_PEER_LIST  = 1;
    final public static int MESSAGE_TYPE_PRIVATE    = 2;
    final public static int MESSAGE_TYPE_PUBLIC     = 3;



    final public static int MESSAGE_TYPE_DISCONNECT = 10;

    public static int messageTypeFromData (byte[] data){
        return data[0];
   }
}