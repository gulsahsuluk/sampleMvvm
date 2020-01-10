package com.gulsahsuluk.chocochallange.data

import com.gulsahsuluk.chocochallange.data.login.model.LoginRequest
import com.gulsahsuluk.chocochallange.data.login.model.LoginResponse
import com.gulsahsuluk.chocochallange.data.product.model.ProductRaw
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface AppService {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body login: LoginRequest): Single<LoginResponse>

    @GET("products")
    fun getProducts(): Observable<List<ProductRaw>>
}