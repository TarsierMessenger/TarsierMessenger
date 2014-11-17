package ch.tarsier.tarsier.crypto;

import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * The EC25519 class is a wrapper on C libraries to enable
 * elliptic curve key agreement and signing.
 */

public class EC25519 {
    private static final int ECC_KEY_LENGTH = 32;

    private static final int ECC_SIGNATURE_RANDOM_BYTES_NUM = 64;

    static {
        System.loadLibrary("curve25519");
        try {
            mRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    private static SecureRandom mRandom;

    private static native byte[] calculateAgreement(byte[] ourPrivate, byte[] theirPublic);
    private static native byte[] generatePublicKey(byte[] privateKey);
    private static native byte[] generatePrivateKey(byte[] random);

    private static native byte[]  calculateSignature(byte[] random, byte[] privateKey, byte[] message);
    private static native boolean verifySignature(byte[] publicKey, byte[] message, byte[] signature);


    /**
     * Generates a new Curve25519 keys (Montgomery space)
     * @return Generated key pair
     */
    public static KeyPair generateKeyPair() {
        byte[] privateKey = generatePrivateKey();
        byte[] publicKey  = generatePublicKey(privateKey);

        return new KeyPair(publicKey, privateKey);
    }

    private static byte[] generatePrivateKey() {
        byte[] privateKey = new byte[ECC_KEY_LENGTH];
        mRandom.nextBytes(privateKey);

        return generatePrivateKey(privateKey);
    }

    private static byte[] getRandom(int size) {
        try {
            byte[] random = new byte[size];
            SecureRandom.getInstance("SHA1PRNG").nextBytes(random);

            return random;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Computes the shared secret on the Curve2119 curve
     * @param ourPrivate  Our private key
     * @param theirPublic Their public key
     * @return Curve25519 Shared secret
     */
    public static byte[] calculateCurve25519KeyAgreement(byte[] ourPrivate, byte[] theirPublic) {
        if (ourPrivate.length != ECC_KEY_LENGTH || theirPublic.length != ECC_KEY_LENGTH) {
            throw new InvalidParameterException();
        }
        return calculateAgreement(ourPrivate, theirPublic);
    }

    /**
     * Signs the data with Curve25519 keys. Keys are transposed from a Montgomery to an Edwards space
     * to enable us to produce a ed25519 signature output.
     * @param privateKey Curve25519 key
     * @param message    Message to be signed
     * @return Signed message
     * @throws InvalidParameterException
     */
    public static byte[] calculateEd25519Signature(byte[] privateKey, byte[] message) throws InvalidParameterException {
        if (privateKey.length != ECC_KEY_LENGTH) {
            throw new InvalidParameterException();
        }
        byte[] randomBytes = getRandom(ECC_SIGNATURE_RANDOM_BYTES_NUM);
        return calculateSignature(randomBytes, privateKey, message);
    }

    public static boolean verifyEd25519Signature(byte[] publicKey, byte[] message, byte[] signature)
        throws InvalidParameterException {

        if (publicKey.length != ECC_KEY_LENGTH) {
            throw new InvalidParameterException();
        }
        return verifySignature(publicKey, message, signature);
    }
}
