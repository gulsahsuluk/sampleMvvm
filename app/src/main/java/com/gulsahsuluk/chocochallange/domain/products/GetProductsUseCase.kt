package com.gulsahsuluk.chocochallange.domain.products

import com.gulsahsuluk.chocochallange.data.product.ProductsRepository
import com.gulsahsuluk.chocochallange.data.product.model.Product
import io.reactivex.Observable
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repository: ProductsRepository) {

    fun fetchProducts(): Observable<List<Product>> {
        return repository
            .fetchProducts()
    }
}