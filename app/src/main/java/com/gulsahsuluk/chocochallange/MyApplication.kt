package com.gulsahsuluk.chocochallange

import android.app.Application
import com.gulsahsuluk.chocochallange.di.ApplicationComponent
import com.gulsahsuluk.chocochallange.di.DaggerApplicationComponent
import com.gulsahsuluk.chocochallange.di.DaggerComponentProvider
import com.gulsahsuluk.chocochallange.common.unsynchronizedLazy
import timber.log.Timber

class MyApplication : Application(), DaggerComponentProvider {

    override val component: ApplicationComponent by unsynchronizedLazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}