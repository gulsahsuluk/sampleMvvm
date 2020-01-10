package com.gulsahsuluk.chocochallange.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gulsahsuluk.chocochallange.common.*
import com.gulsahsuluk.chocochallange.data.order.model.Order
import com.gulsahsuluk.chocochallange.data.product.model.Product
import com.gulsahsuluk.chocochallange.domain.products.GetProductsUseCase
import com.gulsahsuluk.chocochallange.domain.orders.StoreOrderUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val productsUseCase: GetProductsUseCase,
    private val storeOrderUseCase: StoreOrderUseCase
) : RxAwareViewModel() {

    private val productsLiveData: MutableLiveData<Resource<List<Product>>> =
        MutableLiveData()

    fun getProductsState(): LiveData<Resource<List<Product>>> =
        productsLiveData

    private val orderLiveData: MutableLiveData<ResourceState> =
        MutableLiveData()

    fun getCreateOrderState(): LiveData<ResourceState> =
        orderLiveData

    fun fetchProducts(): Disposable =
        productsUseCase.fetchProducts()
            .doOnSubscribe { productsLiveData.value = Resource.loading() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                productsLiveData.value = Resource.success(response)
            }, {
                if (it is IOException) productsLiveData.value = Resource.error(AppError.Network)
                else productsLiveData.value = Resource.error(AppError.Unexpected)
            })
            .also {
                disposable += it
            }

    fun createOrder(products: List<Product>) {
        storeOrderUseCase.storeOrder(createOrderObject(products))
            .doOnSubscribe { orderLiveData.value = ResourceState.LOADING }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                orderLiveData.value = ResourceState.SUCCESS
                Timber.d("Inserted ${products.size} products")
            },{
                orderLiveData.value = ResourceState.ERROR
            })
            .also {
                disposable += it
            }
    }

    private fun createOrderObject(products: List<Product>): Order {
        return Order(
            id = UUID.randomUUID().toString(),
            products = products,
            name = "Order Name",
            price = getTotalPrice(products),
            description = "Order Desc"
        )
    }

    private fun getTotalPrice(products: List<Product>): Long{
         return products.map { it.price }.sum()
    }
}