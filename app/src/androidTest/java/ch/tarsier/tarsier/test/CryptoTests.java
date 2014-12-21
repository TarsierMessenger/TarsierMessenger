package ch.tarsier.tarsier.test;

import android.test.AndroidTestCase;

import java.util.Arrays;

import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.crypto.EC25519;
import ch.tarsier.tarsier.crypto.KeyPair;

/**
 * CryptoTests tests the crypto.
 *
 * @author FredericJacobs
 */
public class CryptoTests extends AndroidTestCase {

    private static final int AGREEMENT_TEST_ITERATIONS = 100;
    private static final int AGREEMENT_SECRET_LENGTH = 32;

    public void testAgreement() {
        for (int i = 0; i < AGREEMENT_TEST_ITERATIONS; i++) {
            KeyPair edKeyPair    = EC25519.generateKeyPair();
            KeyPair lauraKeyPair = EC25519.generateKeyPair();

            byte[] edSecret    = EC25519.calculateCurve25519KeyAgreement(
                    edKeyPair.getPrivateKey(),
                    lauraKeyPair.getPublicKey()
            );

            byte[] lauraSecret = EC25519.calculateCurve25519KeyAgreement(
                    lauraKeyPair.getPrivateKey(),
                    edKeyPair.getPublicKey()
            );

            assertTrue(edSecret.length == AGREEMENT_SECRET_LENGTH);
            assertTrue(lauraSecret.length == AGREEMENT_SECRET_LENGTH);

            assertTrue(Arrays.equals(edSecret, lauraSecret));
        }
    }

    public void testSignatureTrue() {
        KeyPair edKeyPair = EC25519.generateKeyPair();
        String     body      = "I'm a NSA whistleblower";
        byte[]     bodyBytes = body.getBytes();

        byte[] sig = EC25519.calculateEd25519Signature(bodyBytes, edKeyPair.getPrivateKey());

        assertTrue(EC25519.verifyEd25519Signature(edKeyPair.getPublicKey(), bodyBytes, sig));
    }

    public void testSignatureFalse() {
        KeyPair edKeyPair    = EC25519.generateKeyPair();
        String     body         = "I'm a NSA whistleblower";
        String     bodyTampered = "I'm a Russian agent";

        byte[] sig = EC25519.calculateEd25519Signature(body.getBytes(), edKeyPair.getPrivateKey());
        assertFalse(EC25519.verifyEd25519Signature(edKeyPair.getPublicKey(), bodyTampered.getBytes(), sig));
    }
}
