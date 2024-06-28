package com.harshjoshi.passwordmanager

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.harshjoshi.passwordmanager.crypto.CryptoManager
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStream

@Composable
@RequiresApi(Build.VERSION_CODES.M)
fun CryptoView() {
    val cryptoManager = CryptoManager()
    var msgToEncrypt by remember { mutableStateOf("") }
    var msgToDecrypt by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column {
        TextField(
            value = msgToEncrypt,
            onValueChange = { msgToEncrypt = it }
        )
        Row {
            Button(onClick = {
                try {
                    val bytesToEncrypt = msgToEncrypt.toByteArray(Charsets.UTF_8)
                    val outputStream = ByteArrayOutputStream()
                    cryptoManager.encrypt(bytesToEncrypt, outputStream)
                    msgToDecrypt = outputStream.toByteArray().toString(Charsets.UTF_8)
                    errorMessage = null // Clear any previous errors
                } catch (e: Exception) {
                    errorMessage = "Encryption failed: ${e.message}"
                }
            }) {
                Text(text = "Encrypt")
            }
            Button(onClick = {
                try {
                    val inputStream = ByteArrayInputStream(msgToDecrypt.toByteArray(Charsets.UTF_8))
                    val decryptedBytes = cryptoManager.decrypt(inputStream)
                    msgToDecrypt = decryptedBytes.toString(Charsets.UTF_8)
                    errorMessage = null // Clear any previous errors
                } catch (e: Exception) {
                    errorMessage = "Decryption failed: ${e.message}"
                }
            }) {
                Text(text = "Decrypt")
            }
        }
        errorMessage?.let {
            Text(text = it, color = Color.Red)
        }
        Text(text = msgToDecrypt)
    }
}
