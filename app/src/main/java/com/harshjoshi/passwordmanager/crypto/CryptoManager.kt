package com.harshjoshi.passwordmanager.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.CipherOutputStream
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class CryptoManager {
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply{
        load(null)
    }
    private val encryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE, getKey())
    }

    private fun getDecryptCipherForIv(iv:ByteArray): Cipher{
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        }
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry("secret", null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            val keyGenSpec = KeyGenParameterSpec.Builder(
                "secret",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).apply {
                setBlockModes(BLOCK_MODE)
                setEncryptionPaddings(PADDING)
                setUserAuthenticationRequired(false)
                setRandomizedEncryptionRequired(true)
            }.build()

            init(keyGenSpec)
        }.generateKey()
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }

    @Throws(Exception::class)
    fun encrypt(byteArray: ByteArray, outputStream: OutputStream): ByteArray {
        try {
            val iv = encryptCipher.iv
            val encryptedBytes = encryptCipher.doFinal(byteArray)

            outputStream.use {
                it.write(iv.size)
                it.write(iv)
                it.write(encryptedBytes.size)
                it.write(encryptedBytes)
            }

            return encryptedBytes
        } catch (e: Exception) {
            throw Exception("Error encrypting data", e)
        }
    }

    @Throws(Exception::class)
    fun decrypt(inputStream: InputStream): ByteArray {
        try {
            return inputStream.use {
                val ivSize = it.read()
                val iv = ByteArray(ivSize)
                it.read(iv)
                val encryptedBytesSize = it.read()
                val encryptedBytes = ByteArray(encryptedBytesSize)
                it.read(encryptedBytes)

                getDecryptCipherForIv(iv).doFinal(encryptedBytes)
            }
        } catch (e: Exception) {
            throw Exception("Error decrypting data", e)
        }
    }
}