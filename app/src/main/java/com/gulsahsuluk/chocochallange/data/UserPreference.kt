package com.gulsahsuluk.chocochallange.data

import android.content.SharedPreferences
import dagger.Reusable
import javax.inject.Inject

@Reusable
class UserPreference @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val USER_EMAIL = "com.gulsahsuluk.chocochallange.KEY_USER_EMAIL"
        const val USER_TOKEN = "com.gulsahsuluk.chocochallange.KEY_USER_TOKEN"
        const val USER_IS_LOGGED_IN = "com.gulsahsuluk.chocochallange.KEY_USER_IS_LOGGED_IN"
    }

    fun getUserEmail() = sharedPreferences.getString(USER_EMAIL, "")

    fun setUserEmail(value: String?) {
        sharedPreferences.edit().putString(USER_EMAIL, value).apply()
    }

    fun getUserToken() = sharedPreferences.getString(USER_TOKEN, "")

    fun setUserToken(value: String?) {
        sharedPreferences.edit().putString(USER_TOKEN, value).apply()
    }

    fun isLoggedIn() = sharedPreferences.getBoolean(USER_IS_LOGGED_IN, false)

    fun setIsLoggedIn(value: Boolean) {
        sharedPreferences.edit().putBoolean(USER_IS_LOGGED_IN, value).apply()
    }
}