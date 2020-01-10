package com.gulsahsuluk.chocochallange.data.login

import com.gulsahsuluk.chocochallange.data.UserPreference
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(
    private val userPreference: UserPreference
) {

    fun logout() {
        userPreference.setUserToken(null)
        userPreference.setUserEmail(null)
        userPreference.setIsLoggedIn(false)
    }
}