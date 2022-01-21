package com.soyvictorherrera.nosedive.util

import android.content.SharedPreferences
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class PreferenceUtil(
    private val sharedPreferences: SharedPreferences,
    private val flowSharedPreferences: FlowSharedPreferences
) {

    private object Key {
        const val SESSION_OPEN = "session-open"
        const val USER_ID = "user-id"
        const val FCM_TOKEN = "fcm-token"
    }

    private val editor = sharedPreferences.edit()

    fun isSessionOpen(fallback: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(Key.SESSION_OPEN, fallback)
    }

    fun setSessionOpen(sessionOpen: Boolean) {
        editor.putBoolean(Key.SESSION_OPEN, sessionOpen).apply()
    }

    fun observeSessionOpen(fallback: Boolean = false): Flow<Boolean> {
        return flowSharedPreferences.getBoolean(Key.SESSION_OPEN, fallback)
            .asFlow()
            .onStart {
                emit(isSessionOpen(fallback))
            }
    }

    fun getUserId(fallback: String? = null): String? {
        return sharedPreferences.getString(Key.USER_ID, fallback)
    }

    fun setUserId(userId: String) {
        editor.putString(Key.USER_ID, userId).apply()
    }

    fun observeUserId(fallback: String?): Flow<String?> {
        return flowSharedPreferences.getNullableString(Key.USER_ID, fallback)
            .asFlow()
            .onStart { emit(getUserId(fallback)) }
    }

    fun getFcmToken(fallback: String? = null): String? {
        return sharedPreferences.getString(Key.FCM_TOKEN, fallback);
    }

    fun setFcmToken(token: String) {
        editor.putString(Key.FCM_TOKEN, token).apply()
    }

}
