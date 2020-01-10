package com.gulsahsuluk.chocochallange.presentation.order

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.gulsahsuluk.chocochallange.R
import com.gulsahsuluk.chocochallange.di.injector
import com.gulsahsuluk.chocochallange.di.viewModels
import com.gulsahsuluk.chocochallange.common.observeNonNull
import com.gulsahsuluk.chocochallange.data.order.model.Order
import kotlinx.android.synthetic.main.activity_orders.*
import kotlinx.android.synthetic.main.activity_orders.recyclerView

class OrdersActivity : AppCompatActivity() {

    private val ordersAdapter: OrderAdapter by lazy {
        OrderAdapter()
    }

    private val viewModel by viewModels { injector.ordersViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        observeViewModelData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            adapter = ordersAdapter
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                   LinearLayoutManager(this@OrdersActivity)
                else
                    GridLayoutManager(this@OrdersActivity, 2)
        }

        ordersAdapter.clickListener = {
            //TODO: show order products detail screen
        }
    }

    private fun observeViewModelData() {
        viewModel.getOrdersState().observeNonNull(this) {
            handleProductsState(it)
        }
    }

    private fun handleProductsState(resource: List<Order>){
        if (resource.isNotEmpty()){
            ordersAdapter.setItems(resource)
        }else{
            Snackbar.make(root, R.string.empty_order_error_message, Snackbar.LENGTH_LONG).show()
        }
    }
}
