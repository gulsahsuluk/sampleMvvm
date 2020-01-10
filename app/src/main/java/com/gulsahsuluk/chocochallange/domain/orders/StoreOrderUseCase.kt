package com.gulsahsuluk.chocochallange.domain.orders

import com.gulsahsuluk.chocochallange.data.order.OrdersRepository
import com.gulsahsuluk.chocochallange.data.order.model.Order
import io.reactivex.Completable
import javax.inject.Inject

class StoreOrderUseCase @Inject constructor(private val repository: OrdersRepository){

    fun storeOrder(order: Order): Completable {
        return repository.storeOrder(order)
    }
}