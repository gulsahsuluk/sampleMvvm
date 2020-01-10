package com.gulsahsuluk.chocochallange.presentation.product

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.recyclerview.selection.Selection
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.gulsahsuluk.chocochallange.R
import com.gulsahsuluk.chocochallange.common.*
import com.gulsahsuluk.chocochallange.di.injector
import com.gulsahsuluk.chocochallange.di.viewModels
import com.gulsahsuluk.chocochallange.data.product.model.Product
import com.gulsahsuluk.chocochallange.presentation.account.AccountActivity
import com.gulsahsuluk.chocochallange.presentation.order.OrdersActivity
import com.gulsahsuluk.chocochallange.presentation.product.selection.MyItemDetailsLookup
import com.gulsahsuluk.chocochallange.presentation.product.selection.MyItemKeyProvider
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.activity_products.loading
import kotlinx.android.synthetic.main.activity_products.root

class ProductsActivity : AppCompatActivity() {

    private val productsAdapter: ProductAdapter by lazy {
        ProductAdapter()
    }

    private lateinit var tracker: SelectionTracker<Long>

    private val viewModel by viewModels { injector.productsViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        observeViewModelData()
        setupUi()

        savedInstanceState.runIfNull {
            viewModel.fetchProducts()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_account, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_account -> {
                startActivity(Intent(this, AccountActivity::class.java))
            }
        }
        return true
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        tracker.onRestoreInstanceState(savedInstanceState)
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        tracker.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    private fun initRecyclerView() {
        with(recyclerView) {
            adapter = productsAdapter
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    LinearLayoutManager(this@ProductsActivity)
                else
                    GridLayoutManager(this@ProductsActivity, 2)
        }

        productsAdapter.clickListener = {
            //TODO: show product detail screen
        }

        tracker = SelectionTracker.Builder<Long>(
            "mySelection",
            recyclerView,
            MyItemKeyProvider(
                recyclerView
            ),
            MyItemDetailsLookup(
                recyclerView
            ),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        productsAdapter.tracker = tracker
    }

    private fun setupUi(){
        initRecyclerView()

        order.setOnClickListener {
            if (tracker.hasSelection()) {
                viewModel.createOrder(getSelectedProducts(tracker.selection))
            } else {
                Snackbar.make(root, R.string.empty_product_error_message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun observeViewModelData() {
        viewModel.getProductsState().observeNonNull(this) {
            handleProductsState(it)
        }

        viewModel.getCreateOrderState().observeNonNull(this) {
            handleCreateOrderState(it)
        }
    }

    private fun handleProductsState(resource: Resource<List<Product>>) {
        when (resource.state) {
            ResourceState.LOADING -> {
                loading.visible()
            }
            ResourceState.SUCCESS -> {
                loading.gone()
                resource.data?.let { productsAdapter.setItems(it) }
            }
            ResourceState.ERROR -> {
                loading.gone()
                showError(resource.error)
            }
        }
    }

    private fun handleCreateOrderState(state: ResourceState) {
        when (state) {
            ResourceState.LOADING -> {
                loading.visible()
            }
            ResourceState.SUCCESS -> {
                loading.gone()
                startActivity(Intent(this, OrdersActivity::class.java))
                tracker.clearSelection()
            }
            ResourceState.ERROR -> {
                loading.gone()
                Snackbar.make(root, R.string.unexpected_error_message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun getSelectedProducts(selection: Selection<Long>): List<Product>{
        return selection.map {
            productsAdapter.items[it.toInt()]
        }.toList()
    }

    private fun showError(error: AppError?) {
        if (error != null) {
            when (error) {
                is AppError.Network -> showSnackBar(R.string.network_error_message)
                is AppError.Unexpected -> showSnackBar(R.string.unexpected_error_message)
                is AppError.Custom -> showSnackBar(error.message)
            }
        }
    }

    private fun showSnackBar(@StringRes resId: Int) =
        Snackbar.make(root, resId, Snackbar.LENGTH_LONG).show()
}
