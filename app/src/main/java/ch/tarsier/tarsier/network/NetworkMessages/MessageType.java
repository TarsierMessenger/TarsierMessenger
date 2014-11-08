package ch.tarsier.tarsier.network.NetworkMessages;

/**
 * Created by Freddy on 11/8/14.
 */
public class MessageType {
    final public static int MESSAGE_TYPE_HELLO = 0;
    final public static int MESSAGE_TYPE_PRIVATE = 1;
    final public static int MESSAGE_TYPE_PUBLIC = 2;

    public static byte[] recipientPublicKey(byte[] message){
        return null;
    }
   public static int messageTypeFromData (byte[] data){
       return 0;
   }
}
