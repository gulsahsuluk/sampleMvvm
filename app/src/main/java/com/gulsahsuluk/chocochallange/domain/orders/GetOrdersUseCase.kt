package com.gulsahsuluk.chocochallange.domain.orders

import androidx.lifecycle.LiveData
import com.gulsahsuluk.chocochallange.data.order.OrdersRepository
import com.gulsahsuluk.chocochallange.data.order.model.Order
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val repository: OrdersRepository) {

    fun getOrders(): LiveData<List<Order>> {
        return repository
            .getOrdersFromDb()
    }
}