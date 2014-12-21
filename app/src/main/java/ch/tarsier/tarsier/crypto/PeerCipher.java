package ch.tarsier.tarsier.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.exception.PeerCipherException;

/**
 * @author FredericJacobs on 28/11/14.
 */
public class PeerCipher {
    private static final String TAG = "PeerCipher";
    private byte[] mPrecomputedSharedSecret;

    public PeerCipher(byte[] hisPublicKey) {
        mPrecomputedSharedSecret = EC25519.calculateCurve25519KeyAgreement(retreiveKeyPair().getPrivateKey(),
                hisPublicKey);
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

    public byte[] decrypt(byte[] ciphertext, byte[] IV) throws PeerCipherException {

        try {
            IvParameterSpec iv = new IvParameterSpec(IV);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec k = new SecretKeySpec(mPrecomputedSharedSecret, "AES");
            cipher.init(Cipher.DECRYPT_MODE, k, iv);

            return cipher.doFinal(ciphertext);
        } catch (NoSuchPaddingException | RuntimeException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | NoSuchAlgorithmException |
        InvalidAlgorithmParameterException e) {
            throw new PeerCipherException();
        }
    }

    private KeyPair retreiveKeyPair() {
        return Tarsier.app().getUserPreferences().getKeyPair();
    }
}
