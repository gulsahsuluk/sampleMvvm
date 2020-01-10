package com.gulsahsuluk.chocochallange.di

import android.content.Context
import com.gulsahsuluk.chocochallange.presentation.account.AccountViewModel
import com.gulsahsuluk.chocochallange.presentation.login.LoginViewModel
import com.gulsahsuluk.chocochallange.presentation.order.OrdersViewModel
import com.gulsahsuluk.chocochallange.presentation.product.ProductsViewModel
import com.gulsahsuluk.chocochallange.presentation.splash.SplashViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SharedPreferencesModule::class, DatabaseModule::class])
interface ApplicationComponent {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }

  val loginViewModel: LoginViewModel
  val productsViewModel: ProductsViewModel
  val ordersViewModel: OrdersViewModel
  val splashViewModel: SplashViewModel
  val accountViewModel: AccountViewModel
}
