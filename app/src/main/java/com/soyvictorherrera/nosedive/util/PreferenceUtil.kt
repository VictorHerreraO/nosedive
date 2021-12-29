package com.soyvictorherrera.nosedive.util

import android.content.SharedPreferences
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class PreferenceUtil(
    private val sharedPreferences: SharedPreferences,
    private val flowSharedPreferences: FlowSharedPreferences
) {

    private object Keys {
        const val KEY_SESSION_OPEN = "session-open"
        const val KEY_USER_ID = "user-id"
    }

    private val editor = sharedPreferences.edit()

    fun isSessionOpen(fallback: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(Keys.KEY_SESSION_OPEN, fallback)
    }

    fun setSessionOpen(sessionOpen: Boolean) {
        editor.putBoolean(Keys.KEY_SESSION_OPEN, sessionOpen).apply()
    }

    fun observeSessionOpen(fallback: Boolean = false): Flow<Boolean> {
        return flowSharedPreferences.getBoolean(Keys.KEY_SESSION_OPEN, fallback)
            .asFlow()
            .onStart {
                emit(isSessionOpen(fallback))
            }
    }

    fun getUserId(fallback: String? = null): String? {
        return sharedPreferences.getString(Keys.KEY_USER_ID, fallback)
    }

    fun setUserId(userId: String) {
        editor.putString(Keys.KEY_USER_ID, userId).apply()
    }

    fun observeUserId(fallback: String?): Flow<String?> {
        return flowSharedPreferences.getNullableString(Keys.KEY_USER_ID, fallback)
            .asFlow()
            .onStart { emit(getUserId(fallback)) }
    }

}
