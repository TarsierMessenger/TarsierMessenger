package ch.tarsier.tarsier.test;

import android.test.AndroidTestCase;

import java.util.Arrays;

import ch.tarsier.tarsier.crypto.*;

public class CryptoTests extends AndroidTestCase{

    public void testAgreement (){
        for (int i = 0; i < 100; i++){
                        KeyPair edKeyPair    = EC25519.generateKeyPair();
            KeyPair lauraKeyPair = EC25519.generateKeyPair();

            byte[] edSecret    = EC25519.calculateCurve25519KeyAgreement(edKeyPair.getPrivateKey(), lauraKeyPair.getPublicKey());
            byte[] lauraSecret = EC25519.calculateCurve25519KeyAgreement(lauraKeyPair.getPrivateKey(), edKeyPair.getPublicKey());

            assertTrue(edSecret.length == 32);
            assertTrue(lauraSecret.length == 32);

            assertTrue(Arrays.equals(edSecret, lauraSecret));
        }
    }

    public void testSignatureTrue(){
        KeyPair edKeyPair = EC25519.generateKeyPair();
        String     body      = "I'm a NSA whistleblower";
        byte[]     bodyBytes = body.getBytes();

        byte[] sig = EC25519.calculateEd25519Signature(edKeyPair.getPrivateKey(), bodyBytes);

        assertTrue(EC25519.verifyEd25519Signature(edKeyPair.getPublicKey(), bodyBytes, sig));
     }

    public void testSignatureFalse(){
        KeyPair edKeyPair    = EC25519.generateKeyPair();
        String     body         = "I'm a NSA whistleblower";
        String     bodyTampered = "I'm a Russian agent";

        byte[] sig = EC25519.calculateEd25519Signature( edKeyPair.getPrivateKey(), body.getBytes());
        assertFalse(EC25519.verifyEd25519Signature(edKeyPair.getPublicKey(), bodyTampered.getBytes(), sig));
    }
}
