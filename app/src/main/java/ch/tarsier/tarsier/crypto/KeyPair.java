package ch.tarsier.tarsier.crypto;

/**
 * Created by FredericJacobs on 17/11/14.
 */
public class KeyPair {
    private byte[] mPublicKey;
    private byte[] mPrivateKey;

    /**
     * Constructs a KeyPair object
     * @param publicKey  The public  key
     * @param privateKey The private key
     */
    KeyPair(byte[] publicKey, byte[] privateKey){
        mPublicKey  = publicKey;
        mPrivateKey = privateKey;
    }

    public byte[] getPublicKey(){
        return this.mPublicKey;
    }

    public byte[] getPrivateKey(){
        return this.mPrivateKey;
    }
}
