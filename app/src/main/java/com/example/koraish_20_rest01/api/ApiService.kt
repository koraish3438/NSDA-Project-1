package com.example.koraish_20_rest01.api

import com.example.koraish_20_rest01.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
}