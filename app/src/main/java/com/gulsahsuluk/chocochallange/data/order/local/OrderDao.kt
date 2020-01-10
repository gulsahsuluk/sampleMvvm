package com.gulsahsuluk.chocochallange.data.order.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gulsahsuluk.chocochallange.data.order.model.Order

@Dao
interface OrderDao {

    @Query("SELECT * FROM orders")
    fun getOrders(): LiveData<List<Order>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(order: Order)

    @Query("DELETE FROM orders")
    fun clear()
}