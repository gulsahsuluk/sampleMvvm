package com.gulsahsuluk.chocochallange.data.product

import com.gulsahsuluk.chocochallange.data.product.model.Product
import com.gulsahsuluk.chocochallange.data.product.remote.ProductsRemoteDataSource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val remoteDataSource: ProductsRemoteDataSource,
    private val mapper: ProductSafeCheckMapper
) {

    fun fetchProducts(): Observable<List<Product>> {
        return remoteDataSource
            .fetchProducts()
            .map(mapper)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
    }
}