package com.gulsahsuluk.chocochallange.di

import android.content.Context
import androidx.room.Room
import com.gulsahsuluk.chocochallange.data.order.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DaoModule::class])
object DatabaseModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "choco-challange")
            .fallbackToDestructiveMigration().build()
    }
}

@Module
object DaoModule {

    @JvmStatic
    @Provides
    fun provideOrderDao(database: AppDatabase) = database.orderDao()
}