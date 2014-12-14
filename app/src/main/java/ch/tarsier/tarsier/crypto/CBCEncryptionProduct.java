package ch.tarsier.tarsier.crypto;

/**
 * @author FredericJacobs on 28/11/14.
 */
public class CBCEncryptionProduct {
    private byte[] mIV;
    private byte[] mCiphertext;

    CBCEncryptionProduct(byte[] IV, byte[] cipherText) {
        mIV = IV;
        mCiphertext = cipherText;
    }

    public byte[] getIV() {
        return mIV;
    }
    public byte[] getCiphertext() {
        return mCiphertext;
    }
}
