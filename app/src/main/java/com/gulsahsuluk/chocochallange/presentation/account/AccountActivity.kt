package com.gulsahsuluk.chocochallange.presentation.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gulsahsuluk.chocochallange.R
import com.gulsahsuluk.chocochallange.common.ResourceState
import com.gulsahsuluk.chocochallange.common.gone
import com.gulsahsuluk.chocochallange.di.injector
import com.gulsahsuluk.chocochallange.di.viewModels
import com.gulsahsuluk.chocochallange.common.observeNonNull
import com.gulsahsuluk.chocochallange.common.visible
import com.gulsahsuluk.chocochallange.presentation.login.LoginActivity
import com.gulsahsuluk.chocochallange.presentation.order.OrdersActivity
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity() {

    private val viewModel by viewModels { injector.accountViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        observeViewModelData()
        setupUi()
    }

    private fun setupUi(){
        containerOrders.setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
        }

        containerLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun observeViewModelData() {
        viewModel.getlogoutState().observeNonNull(this) {
            handleLogoutState(it)
        }
    }

    private fun handleLogoutState(state: ResourceState){
        when (state) {
            ResourceState.LOADING -> {
                loading.visible()
            }
            ResourceState.SUCCESS -> {
                loading.gone()
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            ResourceState.ERROR -> {
                loading.gone()
                //show message
            }
        }
    }
}
