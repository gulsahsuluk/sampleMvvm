package com.gulsahsuluk.chocochallange.data.login

import com.gulsahsuluk.chocochallange.data.login.model.LoginRequest
import com.gulsahsuluk.chocochallange.data.login.model.LoginResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val remoteDataSource: LoginRemoteDataSource,
    private val localDataSource: LoginLocalDataSource
) {

    fun login(request: LoginRequest): Single<LoginResponse> =
        remoteDataSource.login(request)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())

    fun logout() {
        localDataSource.logout()
    }
}