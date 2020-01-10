package com.gulsahsuluk.chocochallange.presentation.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gulsahsuluk.chocochallange.R
import com.gulsahsuluk.chocochallange.common.AppError
import com.gulsahsuluk.chocochallange.common.RxAwareViewModel
import com.gulsahsuluk.chocochallange.common.Resource
import com.gulsahsuluk.chocochallange.common.plusAssign
import com.gulsahsuluk.chocochallange.data.login.model.LoginRequest
import com.gulsahsuluk.chocochallange.domain.login.LoginUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import java.io.IOException
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase
) : RxAwareViewModel() {

    private val loginFormLiveData = MutableLiveData<LoginFormState>()
    fun getLoginFormState(): LiveData<LoginFormState> = loginFormLiveData

    private val loginLiveData = MutableLiveData<Resource<Boolean>>()
    fun getLoginState(): LiveData<Resource<Boolean>> = loginLiveData

    fun login(request: LoginRequest) =
        useCase.login(request)
            .doOnSubscribe { loginLiveData.value = Resource.loading() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loginLiveData.value = Resource.success(true)
            }, {
                if (it is IOException) loginLiveData.value =
                    Resource.error(AppError.Network)
                else loginLiveData.value = Resource.error(AppError.Custom(R.string.login_failed))
            })
            .also {
                disposable += it
            }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            loginFormLiveData.value =
                LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            loginFormLiveData.value =
                LoginFormState(passwordError = R.string.invalid_password)
        } else {
            loginFormLiveData.value =
                LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}