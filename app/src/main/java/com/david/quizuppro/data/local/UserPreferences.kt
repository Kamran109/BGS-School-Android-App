package com.david.quizuppro.data.local

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun getUsername(): String {
        return prefs.getString("username", "Player${(1000..9999).random()}") ?: "Player"
    }

    fun setUsername(username: String) {
        prefs.edit().putString("username", username).apply()
    }

    fun getUserId(): String {
        var userId = prefs.getString("user_id", null)
        if (userId == null) {
            userId = java.util.UUID.randomUUID().toString()
            prefs.edit().putString("user_id", userId).apply()
        }
        return userId!!
    }
}
