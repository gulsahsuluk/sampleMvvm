package com.gulsahsuluk.chocochallange

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.gulsahsuluk.chocochallange.testCommon.RxImmediateSchedulerRule
import com.gulsahsuluk.chocochallange.common.AppError
import com.gulsahsuluk.chocochallange.common.Resource
import com.gulsahsuluk.chocochallange.common.ResourceState
import com.gulsahsuluk.chocochallange.data.product.model.Product
import com.gulsahsuluk.chocochallange.domain.products.GetProductsUseCase
import com.gulsahsuluk.chocochallange.domain.orders.StoreOrderUseCase
import com.gulsahsuluk.chocochallange.presentation.product.ProductsViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.lang.RuntimeException

class ProductsViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @MockK
    private lateinit var productsUseCase: GetProductsUseCase

    @MockK
    private lateinit var products: List<Product>

    @MockK
    private lateinit var storeOrderUseCase: StoreOrderUseCase

    private lateinit var viewModel: ProductsViewModel

    private val productsUseCaseSubject = BehaviorSubject.create<List<Product>>()

    @Before
    fun init(){
        MockKAnnotations.init(this)

        every { productsUseCase.fetchProducts()} returns productsUseCaseSubject

        viewModel = ProductsViewModel(productsUseCase, storeOrderUseCase)
    }

    @Test
    fun `should show loading state as first state when products emits`() {

        val mockedObserver = createProductsObserver()
        viewModel.getProductsState().observeForever(mockedObserver)

        viewModel.fetchProducts()

        val productViewStateSlots = mutableListOf<Resource<List<Product>>>()
        verify { mockedObserver.onChanged(capture(productViewStateSlots)) }

        val state = productViewStateSlots[0]

        Truth.assertThat(state.state).isEqualTo(ResourceState.LOADING)
    }

    @Test
    fun `should show success state as a second state when products emits`() {

        val mockedObserver = createProductsObserver()
        viewModel.getProductsState().observeForever(mockedObserver)

        viewModel.fetchProducts()
        productsUseCaseSubject.onNext(products)

        val productViewStateSlots = mutableListOf<Resource<List<Product>>>()
        verify(exactly = 2) { mockedObserver.onChanged(capture(productViewStateSlots)) }

        val stateSlot = productViewStateSlots[1]

        Truth.assertThat(stateSlot.state).isEqualTo(ResourceState.SUCCESS)
    }

    @Test
    fun `should show error as a second state when products fetching`() {

        val mockedObserver = createProductsObserver()
        viewModel.getProductsState().observeForever(mockedObserver)

        viewModel.fetchProducts()
        productsUseCaseSubject.onError(RuntimeException("Missing params"))

        val productViewStateSlots = mutableListOf<Resource<List<Product>>>()
        verify(exactly = 2) { mockedObserver.onChanged(capture(productViewStateSlots)) }

        val stateSlot = productViewStateSlots[1]

        Truth.assertThat(stateSlot.state).isEqualTo(ResourceState.ERROR)
    }

    @Test
    fun `should return network error type when thrown error and is IOException`(){

        viewModel.fetchProducts()
        productsUseCaseSubject.onError(IOException("Server problem occurs"))

        val stateSlot = viewModel.getProductsState().value

        Truth.assertThat(stateSlot?.error).isEqualTo(AppError.Network)
    }

    @Test
    fun `should return unexpected error type when thrown error but not IOException`(){

        viewModel.fetchProducts()
        productsUseCaseSubject.onError(RuntimeException("Unexpected error occurs"))

        val stateSlot = viewModel.getProductsState().value

        Truth.assertThat(stateSlot?.error).isEqualTo(AppError.Unexpected)
    }

    private fun createProductsObserver(): Observer<Resource<List<Product>>> = spyk(Observer { })
}