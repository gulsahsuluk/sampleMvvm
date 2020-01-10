package com.gulsahsuluk.chocochallange.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.gulsahsuluk.chocochallange.R
import com.gulsahsuluk.chocochallange.di.injector
import com.gulsahsuluk.chocochallange.di.viewModels
import com.gulsahsuluk.chocochallange.presentation.login.LoginActivity
import com.gulsahsuluk.chocochallange.presentation.product.ProductsActivity

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels { injector.splashViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({

            if(viewModel.isLoggedIn()){
                startActivity(Intent(this, ProductsActivity::class.java))
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 1500)
    }
}
