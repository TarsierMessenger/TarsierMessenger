package ch.tarsier.tarsier.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.exception.PeerCipherException;

/**
 * Created by fred on 28/11/14.
 */

public class PeerCipher {
    private static final String TAG = "PeerCipher";
    private byte[] mPeerPublicKey;
    private byte[] mPrecomputedSharedSecret;

    public PeerCipher(byte[] hisPublicKey) {
        mPeerPublicKey = hisPublicKey;
        mPrecomputedSharedSecret = EC25519.calculateCurve25519KeyAgreement(retreiveKeyPair().getPrivateKey(),
                                                                           mPeerPublicKey);
    }

    public CBCEncryptionProduct encrypt(byte[] plaintext) throws PeerCipherException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec k = new SecretKeySpec(mPrecomputedSharedSecret, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, k);
            byte[] encryptedData = cipher.doFinal(plaintext);
            return new CBCEncryptionProduct(cipher.getIV(), encryptedData);
        } catch (NoSuchPaddingException | RuntimeException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | NoSuchAlgorithmException e) {
            throw new PeerCipherException();
        }
    }

    public byte[] decrypt(byte[] ciphertext) throws PeerCipherException {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec k = new SecretKeySpec(mPrecomputedSharedSecret, "AES");
            cipher.init(Cipher.DECRYPT_MODE, k);
            byte[] plainText = cipher.doFinal(ciphertext);

            return plainText;
        } catch (NoSuchPaddingException | RuntimeException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | NoSuchAlgorithmException e) {
            throw new PeerCipherException();
        }
    }

    private KeyPair retreiveKeyPair() {
        return Tarsier.app().getUserPreferences().getKeyPair();
    }
}
