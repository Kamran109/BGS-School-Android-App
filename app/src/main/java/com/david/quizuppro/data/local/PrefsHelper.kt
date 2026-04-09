package com.david.quizuppro.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PrefsHelper(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getUsername(): String {
        return prefs.getString("username", "Player${(1000..9999).random()}") ?: "Player"
    }

    fun setUsername(username: String) {
        prefs.edit { putString("username", username) }
    }

    fun getUserId(): String {
        var userId = prefs.getString("user_id", null)
        if (userId == null) {
            userId = java.util.UUID.randomUUID().toString()
            prefs.edit().putString("user_id", userId).apply()
        }
        return userId!!
    }

    companion object {
        private const val ONBOARDING_SHOWN = "onboarding_shown"
        private const val IS_LOGGED_IN = "is_logged_in"

    }

    fun isOnboardingShown(): Boolean {
        return prefs.getBoolean(ONBOARDING_SHOWN, false)
    }

    fun setOnboardingShown(shown: Boolean) {
        prefs.edit().putBoolean(ONBOARDING_SHOWN, shown).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    fun setLoggedIn(loggedIn: Boolean) {
        prefs.edit().putBoolean(IS_LOGGED_IN, loggedIn).apply()
    }

}