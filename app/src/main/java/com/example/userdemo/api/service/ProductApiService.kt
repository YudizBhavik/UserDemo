package com.example.userdemo.api.service

import com.example.userdemo.data.model.response.Product
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    fun getProducts(): Call<List<Product>>
}
