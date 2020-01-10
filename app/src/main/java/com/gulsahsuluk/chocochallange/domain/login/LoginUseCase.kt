package com.gulsahsuluk.chocochallange.domain.login

import com.gulsahsuluk.chocochallange.data.UserPreference
import com.gulsahsuluk.chocochallange.data.login.LoginRepository
import com.gulsahsuluk.chocochallange.data.login.model.LoginRequest
import com.gulsahsuluk.chocochallange.data.login.model.LoginResponse
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository,
    private val userPreference: UserPreference
){
    fun login(request: LoginRequest): Single<LoginResponse> {
        return repository
            .login(request)
            .doOnSuccess {
                userPreference.setUserToken(it.token)
                userPreference.setIsLoggedIn(true)
            }
    }
}