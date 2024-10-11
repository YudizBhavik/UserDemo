package com.example.userdemo.baseRepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.userdemo.api.service.ApiService
import com.example.userdemo.local.ProductDao
import com.example.userdemo.local.ProductEntity

class ProductRepository(private val productDao: ProductDao) {

    private val _products = MutableLiveData<List<ProductEntity>>()
    val products: LiveData<List<ProductEntity>> get() = _products

    fun fetchAndStoreProducts(apiService: ApiService) {
        val response = apiService.getProducts().execute()
        if (response.isSuccessful) {
            response.body()?.let { products ->
                productDao.insertAll(products.map { product ->
                    ProductEntity(
                        id = product.id,
                        title = product.title,
                        description = product.description,
                        image = product.image
                    )
                })
                _products.postValue(productDao.getAllProducts())
            }
        }
    }
    fun getProducts(): List<ProductEntity> {
        return productDao.getAllProducts()
    }
}
