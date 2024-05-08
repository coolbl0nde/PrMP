package com.example.calculator

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ThemeViewModel : ViewModel() {
    private val _theme = mutableStateOf("light") // Значение по умолчанию
    val theme: State<String> = _theme

    init {
        loadThemeFromFirestore()
    }

    private fun loadThemeFromFirestore() {
        FirebaseFirestore.getInstance().collection("themes").document("current")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val themeId = documentSnapshot.getString("theme_id") ?: "light"
                _theme.value = themeId
            }
            .addOnFailureListener { e ->
                Log.w("ThemeViewModel", "Error fetching theme", e)
            }
    }

    fun setTheme(newTheme: String) {
        if (newTheme != _theme.value) {
            _theme.value = newTheme
            saveThemeToFirestore(newTheme)
        }
    }

    private fun saveThemeToFirestore(themeId: String) {
        FirebaseFirestore.getInstance().collection("themes").document("current")
            .set(mapOf("theme_id" to themeId))
            .addOnSuccessListener { Log.d("ThemeViewModel", "Theme updated successfully!") }
            .addOnFailureListener { e -> Log.w("ThemeViewModel", "Error updating theme", e) }
    }
}
