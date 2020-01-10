package com.gulsahsuluk.chocochallange.data.login

import com.gulsahsuluk.chocochallange.data.AppService
import com.gulsahsuluk.chocochallange.data.UserPreference
import com.gulsahsuluk.chocochallange.data.login.model.LoginRequest
import com.gulsahsuluk.chocochallange.data.login.model.LoginResponse
import io.reactivex.Single
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val appService: AppService
) {

    fun login(request: LoginRequest): Single<LoginResponse> =
        appService.login(request)
}