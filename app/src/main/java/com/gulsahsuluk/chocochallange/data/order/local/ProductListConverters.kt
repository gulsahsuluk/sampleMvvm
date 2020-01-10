package com.gulsahsuluk.chocochallange.data.order.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gulsahsuluk.chocochallange.data.product.model.Product
import java.util.*

object ProductListConverters {

    private var gson = Gson()

    @TypeConverter
    @JvmStatic
    fun stringToProductList(data: String?): List<Product> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Product>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun productListToString(products: List<Product>): String {
        return gson.toJson(products)
    }
}