package com.gulsahsuluk.chocochallange.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.gulsahsuluk.chocochallange.R
import com.gulsahsuluk.chocochallange.common.*
import com.gulsahsuluk.chocochallange.di.injector
import com.gulsahsuluk.chocochallange.di.viewModels
import com.gulsahsuluk.chocochallange.data.login.model.LoginRequest
import com.gulsahsuluk.chocochallange.presentation.product.ProductsActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels { injector.loginViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        observeViewModelData()
        setupUi()
    }

    private fun setupUi(){
        username.afterTextChanged {
            viewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->{
                        viewModel.login(
                            LoginRequest(
                                username.text.toString(),
                                password.text.toString()
                            )
                        )
                    }
                }
                false
            }
        }

        login.setOnClickListener{
            viewModel.login(
                LoginRequest(
                    username.text.toString(),
                    password.text.toString()
                )
            )
            hideSoftKeyboard()
        }
    }

    private fun observeViewModelData() {
        viewModel.getLoginState().observeNonNull(this) {
            handleLoginState(it)
        }

        viewModel.getLoginFormState().observeNonNull(this){
            val loginState = it
            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        }
    }

    private fun handleLoginState(resource: Resource<Boolean>) {
        when (resource.state) {
            ResourceState.LOADING -> {
                loading.visible()
            }
            ResourceState.SUCCESS -> {
                loading.gone()
                val intent = Intent(this, ProductsActivity::class.java)
                startActivity(intent)
                finish()
            }
            ResourceState.ERROR -> {
                loading.gone()
                showError(resource.error)
            }
        }
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
