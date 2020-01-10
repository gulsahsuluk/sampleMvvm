package com.gulsahsuluk.chocochallange.data.order

import androidx.lifecycle.LiveData
import com.gulsahsuluk.chocochallange.data.order.model.Order
import com.gulsahsuluk.chocochallange.data.order.local.OrdersLocalDataSource
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OrdersRepository @Inject constructor(private val localDataSource: OrdersLocalDataSource) {

    fun getOrdersFromDb(): LiveData<List<Order>> {
        return localDataSource
            .getOrders()
    }

    fun storeOrder(order: Order): Completable {
        return Completable.fromCallable { localDataSource.storeOrder(order) }
            .subscribeOn(Schedulers.io())
    }

    fun clearAllOrders(): Completable {
        return Completable.fromCallable { localDataSource.clearAllOrders() }
            .subscribeOn(Schedulers.io())
    }
}