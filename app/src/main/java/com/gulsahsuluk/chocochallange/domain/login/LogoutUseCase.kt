package com.gulsahsuluk.chocochallange.domain.login

import com.gulsahsuluk.chocochallange.data.login.LoginRepository
import com.gulsahsuluk.chocochallange.data.order.OrdersRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: LoginRepository,
    private val ordersRepository: OrdersRepository
){
    fun logout(): Completable {
        return Observable.fromCallable { repository.logout() }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .flatMapCompletable { ordersRepository.clearAllOrders() }
    }
}