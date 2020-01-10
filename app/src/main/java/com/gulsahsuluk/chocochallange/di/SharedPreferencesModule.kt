package com.gulsahsuluk.chocochallange.di

import android.content.Context
import android.content.SharedPreferences
import com.gulsahsuluk.chocochallange.R
import dagger.Module
import dagger.Provides

@Module
object SharedPreferencesModule {

    @JvmStatic @Provides
    fun provideLibrarySharedPreferences(
        context: Context
    ): SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.choco_preferences_name),
        Context.MODE_PRIVATE
    )
}