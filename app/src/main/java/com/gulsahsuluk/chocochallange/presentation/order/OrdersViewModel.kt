package com.gulsahsuluk.chocochallange.presentation.order

import androidx.lifecycle.LiveData
import com.gulsahsuluk.chocochallange.common.RxAwareViewModel
import com.gulsahsuluk.chocochallange.data.order.model.Order
import com.gulsahsuluk.chocochallange.domain.orders.GetOrdersUseCase
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    private val useCase: GetOrdersUseCase
) : RxAwareViewModel() {

    fun getOrdersState(): LiveData<List<Order>> = useCase.getOrders()
}