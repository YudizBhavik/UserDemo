package com.example.userdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userdemo.api.service.ApiService
import com.example.userdemo.baseRepo.ProductRepository
import com.example.userdemo.local.ProductEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val products: LiveData<List<ProductEntity>> = repository.products

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch (Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)
            repository.fetchAndStoreProducts(apiService)
        }
        }
}