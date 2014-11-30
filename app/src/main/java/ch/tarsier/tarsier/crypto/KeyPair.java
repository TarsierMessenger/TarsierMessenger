package ch.tarsier.tarsier.crypto;

import android.util.Base64;

/**
 * @author FredericJacobs
 */
public class KeyPair {
    private byte[] mPublicKey;
    private byte[] mPrivateKey;

    /**
     * Constructs a KeyPair object
     * @param publicKey  The public  key
     * @param privateKey The private key
     */
    public KeyPair(byte[] publicKey, byte[] privateKey) {
        mPublicKey  = publicKey;
        mPrivateKey = privateKey;
    }

    public KeyPair(String publicKey, String privateKey) {
        this(Base64.decode(publicKey, Base64.DEFAULT),
             Base64.decode(privateKey, Base64.DEFAULT));
    }

    public byte[] getPublicKey() {
        return mPublicKey;
    }

    public byte[] getPrivateKey() {
        return mPrivateKey;
    }

    public String getBase64EncodedPublicKey() {
        return Base64.encodeToString(mPublicKey, Base64.DEFAULT);
    }

    public String getBase64EncodedPrivateKey() {
        return Base64.encodeToString(mPrivateKey, Base64.DEFAULT);
    }
}
