package com.gulsahsuluk.chocochallange.data.order.model

import androidx.room.*
import com.gulsahsuluk.chocochallange.data.order.local.ProductListConverters
import com.gulsahsuluk.chocochallange.data.product.model.Product

@Entity(tableName = "orders")
@TypeConverters(ProductListConverters::class)
data class Order(
    @PrimaryKey
    val id: String,

    val products: List<Product>,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "price")
    val price: Long,

    @ColumnInfo(name = "description")
    val description: String
)