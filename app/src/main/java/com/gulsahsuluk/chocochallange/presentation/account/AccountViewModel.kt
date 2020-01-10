package com.gulsahsuluk.chocochallange.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gulsahsuluk.chocochallange.common.ResourceState
import com.gulsahsuluk.chocochallange.common.RxAwareViewModel
import com.gulsahsuluk.chocochallange.common.plusAssign
import com.gulsahsuluk.chocochallange.domain.login.LogoutUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val useCase: LogoutUseCase
) : RxAwareViewModel() {

    private val logoutLiveData: MutableLiveData<ResourceState> =
        MutableLiveData()

    fun getlogoutState(): LiveData<ResourceState> =
        logoutLiveData

    fun logout() {
        useCase.logout()
            .doOnSubscribe { logoutLiveData.value = ResourceState.LOADING }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                logoutLiveData.value = ResourceState.SUCCESS
                Timber.d("User Logout Success")
            }, {
                logoutLiveData.value = ResourceState.ERROR
            })
            .also {
                disposable += it
            }
    }
}