package com.example.calculator

import android.app.Application
import androidx.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import android.util.Base64
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SetupViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    private val _isPassKeySet = MutableStateFlow(false)
    val isPassKeySet: StateFlow<Boolean> = _isPassKeySet

    init {
        _isPassKeySet.value = checkPassKeyExists()
    }

    companion object {
        private const val KEY_ALIAS = "myAppKeyAlias"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val IV_SIZE = 12
        private const val TAG_LENGTH_BIT = 128
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = java.security.KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)

        keyStore.getKey(KEY_ALIAS, null)?.let {
            return it as SecretKey
        }

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setRandomizedEncryptionRequired(true)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }

    fun setPassKey(passKey: String) {
        val secretKey = getSecretKey()
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(passKey.toByteArray(Charsets.UTF_8))
        val ivString = Base64.encodeToString(iv, Base64.DEFAULT)
        val encryptedString = Base64.encodeToString(encrypted, Base64.DEFAULT)

        sharedPreferences.edit()
            .putString("encryptedPassKey", encryptedString)
            .putString("iv", ivString)
            .apply()
    }

    fun getPassKey(): String? {
        val encryptedString = sharedPreferences.getString("encryptedPassKey", null) ?: return null
        val ivString = sharedPreferences.getString("iv", null) ?: return null
        val iv = Base64.decode(ivString, Base64.DEFAULT)
        val encrypted = Base64.decode(encryptedString, Base64.DEFAULT)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val secretKey = getSecretKey()
        val spec = GCMParameterSpec(TAG_LENGTH_BIT, iv)

        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        return String(cipher.doFinal(encrypted), Charsets.UTF_8)
    }

    fun validatePassKey(input: String): Boolean {
        val savedPassKey = getPassKey()
        return savedPassKey == input
    }

    private fun checkPassKeyExists(): Boolean {
        return sharedPreferences.contains("encryptedPassKey")
    }

    fun resetPassKey() {
        sharedPreferences.edit().remove("encryptedPassKey").apply()
        sharedPreferences.edit().remove("iv").apply() // Удаление IV, если используется AES/GCM
    }
}
