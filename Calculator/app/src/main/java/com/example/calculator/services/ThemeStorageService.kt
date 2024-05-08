package com.example.calculator.services

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


class ThemeStorageService{

    private val db = FirebaseFirestore.getInstance()

    fun saveTheme(themeId: String) {
        val themeData = hashMapOf("theme_id" to themeId)

        db.collection("themes").document("current")
            .set(themeData)
            .addOnSuccessListener { Log.d("ThemeStorage", "Theme updated successfully!") }
            .addOnFailureListener { e -> Log.w("ThemeStorage", "Error updating theme", e) }
    }

    fun fetchTheme(onThemeFetched: (String) -> Unit) {
        db.collection("themes").document("current")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val themeId = documentSnapshot.getString("theme_id") ?: "light"
                onThemeFetched(themeId)
            }
            .addOnFailureListener { e -> Log.w("ThemeStorage", "Error fetching theme", e) }
    }

}