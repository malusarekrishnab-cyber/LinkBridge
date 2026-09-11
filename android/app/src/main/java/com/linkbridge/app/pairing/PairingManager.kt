package com.linkbridge.app.pairing

import android.util.Base64
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.security.KeyFactory

class PairingManager {
    private var keyPair: KeyPair? = null

    init {
        generateKeyPair()
    }

    private fun generateKeyPair() {
        val generator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(2048)
        keyPair = generator.generateKeyPair()
    }

    fun getPublicKeyBase64(): String {
        return Base64.encodeToString(keyPair?.public?.encoded, Base64.NO_WRAP)
    }

    fun loadPublicKeyFromBase64(base64Str: String): PublicKey {
        val keyBytes = Base64.decode(base64Str, Base64.DEFAULT)
        val spec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(spec)
    }

    fun signData(data: String): String {
        val privateKey: PrivateKey = keyPair?.private ?: return ""
        val signature = java.security.Signature.getInstance("SHA256withRSA")
        signature.initSign(privateKey)
        signature.update(data.toByteArray())
        val sigBytes = signature.sign()
        return Base64.encodeToString(sigBytes, Base64.NO_WRAP)
    }
    
    fun verifySignature(data: String, signatureBase64: String, publicKey: PublicKey): Boolean {
        val signature = java.security.Signature.getInstance("SHA256withRSA")
        signature.initVerify(publicKey)
        signature.update(data.toByteArray())
        val sigBytes = Base64.decode(signatureBase64, Base64.DEFAULT)
        return signature.verify(sigBytes)
    }
}
