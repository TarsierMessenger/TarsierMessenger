package ch.tarsier.tarsier.crypto;

/**
 * Created by @FredericJacobs on 28/11/14.
 */
public class CBCEncryptionProduct {
    private byte[] mIV, mCiphertext;

    CBCEncryptionProduct(byte[] IV, byte[] cipherText){
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
