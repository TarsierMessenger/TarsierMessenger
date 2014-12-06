package ch.tarsier.tarsier.util;

import com.google.protobuf.ByteString;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.crypto.CBCEncryptionProduct;
import ch.tarsier.tarsier.crypto.PeerCipher;
import ch.tarsier.tarsier.exception.PeerCipherException;
import ch.tarsier.tarsier.network.messages.MessageType;
import ch.tarsier.tarsier.network.messages.TarsierWireProtos.TarsierPrivateMessage;
import ch.tarsier.tarsier.network.messages.TarsierWireProtos.TarsierPublicMessage;

public class TarsierMessageFactory {

    public static byte[] wirePrivateProto(byte[] peerPublicKey, byte[] message)
                                                        throws PeerCipherException
    {
        byte[] myPublicKey = getPublicKey();
        TarsierPrivateMessage.Builder privateMessage = TarsierPrivateMessage.newBuilder();

        privateMessage.setReceiverPublicKey(ByteString.copyFrom(peerPublicKey));
        privateMessage.setSenderPublicKey(ByteString.copyFrom(myPublicKey));

        PeerCipher cipher = new PeerCipher(peerPublicKey);
        CBCEncryptionProduct encryptionProduct = cipher.encrypt(message);
        byte[] cipherText = encryptionProduct.getCiphertext();
        byte[] IV         = encryptionProduct.getIV();

        privateMessage.setCipherText(ByteString.copyFrom(cipherText));
        privateMessage.setIV(ByteString.copyFrom(IV));

        byte[] messageProto = privateMessage.build().toByteArray();

        return ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PRIVATE, messageProto);;
    }

    public static byte[] wirePublicProto(byte[] message) {
        TarsierPublicMessage.Builder publicMessage = TarsierPublicMessage.newBuilder();
        publicMessage.setSenderPublicKey(ByteString.copyFrom(getPublicKey()));
        publicMessage.setPlainText(ByteString.copyFrom(message));

        byte[] messageProto =  publicMessage.build().toByteArray();

        return ByteUtils.prependInt(MessageType.MESSAGE_TYPE_PUBLIC, messageProto);
    }

    public static byte[] getPublicKey() {
        return Tarsier.app().getUserPreferences().getKeyPair().getPublicKey();
    }

    //TODO: Handle incoming methods too
}
