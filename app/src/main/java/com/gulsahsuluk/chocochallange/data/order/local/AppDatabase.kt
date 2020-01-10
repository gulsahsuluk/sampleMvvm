package com.gulsahsuluk.chocochallange.data.order.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gulsahsuluk.chocochallange.data.order.model.Order

@Database(entities = [Order::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao
}