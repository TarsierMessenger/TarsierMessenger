#include <string.h>
#include <stdint.h>

#include <jni.h>
#include "curve25519-donna.h"
#include "curve_sigs.h"

JNIEXPORT jbyteArray JNICALL Java_ch_tarsier_tarsier_crypto_EC25519_generatePrivateKey
(JNIEnv *env, jclass clazz, jbyteArray random)
{
    uint8_t* privateKey = (uint8_t*)(*env)->GetByteArrayElements(env, random, 0);
    
    // See https://github.com/agl/curve25519-donna
    privateKey[0] &= 248;
    privateKey[31] &= 127;
    privateKey[31] |= 64;
    
    (*env)->ReleaseByteArrayElements(env, random, privateKey, 0);
    
    return random;
}

JNIEXPORT jbyteArray JNICALL Java_ch_tarsier_tarsier_crypto_EC25519_generatePublicKey
(JNIEnv *env, jclass clazz, jbyteArray privateKey)
{
    static const uint8_t  basepoint[32] = {9};
    
    jbyteArray publicKey       = (*env)->NewByteArray(env, 32);
    uint8_t*   publicKeyBytes  = (uint8_t*)(*env)->GetByteArrayElements(env, publicKey, 0);
    uint8_t*   privateKeyBytes = (uint8_t*)(*env)->GetByteArrayElements(env, privateKey, 0);
    
    curve25519_donna(publicKeyBytes, privateKeyBytes, basepoint);
    
    (*env)->ReleaseByteArrayElements(env, publicKey, publicKeyBytes, 0);
    (*env)->ReleaseByteArrayElements(env, privateKey, privateKeyBytes, 0);
    
    return publicKey;
}

JNIEXPORT jbyteArray JNICALL Java_ch_tarsier_tarsier_crypto_EC25519_calculateAgreement
(JNIEnv *env, jclass clazz, jbyteArray privateKey, jbyteArray publicKey)
{
    jbyteArray sharedKey       = (*env)->NewByteArray(env, 32);
    uint8_t*   sharedKeyBytes  = (uint8_t*)(*env)->GetByteArrayElements(env, sharedKey, 0);
    uint8_t*   privateKeyBytes = (uint8_t*)(*env)->GetByteArrayElements(env, privateKey, 0);
    uint8_t*   publicKeyBytes  = (uint8_t*)(*env)->GetByteArrayElements(env, publicKey, 0);
    
    curve25519_donna(sharedKeyBytes, privateKeyBytes, publicKeyBytes);
    
    (*env)->ReleaseByteArrayElements(env, sharedKey, sharedKeyBytes, 0);
    (*env)->ReleaseByteArrayElements(env, publicKey, publicKeyBytes, 0);
    (*env)->ReleaseByteArrayElements(env, privateKey, privateKeyBytes, 0);
    
    return sharedKey;
}

JNIEXPORT jbyteArray JNICALL Java_ch_tarsier_tarsier_crypto_EC25519_calculateSignature
(JNIEnv *env, jclass clazz, jbyteArray random, jbyteArray privateKey, jbyteArray message)
{
    jbyteArray signature       = (*env)->NewByteArray(env, 64);
    uint8_t*   signatureBytes  = (uint8_t*)(*env)->GetByteArrayElements(env, signature, 0);
    uint8_t*   randomBytes     = (uint8_t*)(*env)->GetByteArrayElements(env, random, 0);
    uint8_t*   privateKeyBytes = (uint8_t*)(*env)->GetByteArrayElements(env, privateKey, 0);
    uint8_t*   messageBytes    = (uint8_t*)(*env)->GetByteArrayElements(env, message, 0);
    jsize      messageLength   = (*env)->GetArrayLength(env, message);
    
    int result = curve25519_sign(signatureBytes, privateKeyBytes, messageBytes, messageLength, randomBytes);
    
    (*env)->ReleaseByteArrayElements(env, signature, signatureBytes, 0);
    (*env)->ReleaseByteArrayElements(env, random, randomBytes, 0);
    (*env)->ReleaseByteArrayElements(env, privateKey, privateKeyBytes, 0);
    (*env)->ReleaseByteArrayElements(env, message, messageBytes, 0);
    
    if (result == 0) return signature;
    else             (*env)->ThrowNew(env, (*env)->FindClass(env, "java/lang/AssertionError"), "Signature failed!");
}

JNIEXPORT jboolean JNICALL Java_ch_tarsier_tarsier_crypto_EC25519_verifySignature
(JNIEnv *env, jclass clazz, jbyteArray publicKey, jbyteArray message, jbyteArray signature)
{
    uint8_t*   signatureBytes = (uint8_t*)(*env)->GetByteArrayElements(env, signature, 0);
    uint8_t*   publicKeyBytes = (uint8_t*)(*env)->GetByteArrayElements(env, publicKey, 0);
    uint8_t*   messageBytes   = (uint8_t*)(*env)->GetByteArrayElements(env, message, 0);
    jsize      messageLength  = (*env)->GetArrayLength(env, message);
    
    jboolean result = (curve25519_verify(signatureBytes, publicKeyBytes, messageBytes, messageLength) == 0);
    
    (*env)->ReleaseByteArrayElements(env, signature, signatureBytes, 0);
    (*env)->ReleaseByteArrayElements(env, publicKey, publicKeyBytes, 0);
    (*env)->ReleaseByteArrayElements(env, message, messageBytes, 0);
    
    return result;
}
