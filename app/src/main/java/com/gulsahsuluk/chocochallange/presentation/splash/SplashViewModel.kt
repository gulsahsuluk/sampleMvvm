package com.gulsahsuluk.chocochallange.presentation.splash

import androidx.lifecycle.ViewModel
import com.gulsahsuluk.chocochallange.data.UserPreference
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val userPreference: UserPreference
): ViewModel() {

    fun isLoggedIn() : Boolean{
        return userPreference.isLoggedIn()
    }
}