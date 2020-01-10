package com.gulsahsuluk.chocochallange.data.order.local

import com.gulsahsuluk.chocochallange.data.order.model.Order
import javax.inject.Inject

class OrdersLocalDataSource @Inject constructor(private val orderDao: OrderDao){

    fun getOrders() = orderDao.getOrders()

    fun storeOrder(order: Order) = orderDao.insert(order)

    fun clearAllOrders() = orderDao.clear()
}