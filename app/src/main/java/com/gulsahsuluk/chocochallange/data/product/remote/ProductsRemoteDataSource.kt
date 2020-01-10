package com.gulsahsuluk.chocochallange.data.product.remote

import com.gulsahsuluk.chocochallange.data.AppService
import javax.inject.Inject

class ProductsRemoteDataSource @Inject constructor(private val appService: AppService) {

    fun fetchProducts() = appService.getProducts()
}