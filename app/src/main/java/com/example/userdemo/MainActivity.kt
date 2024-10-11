package com.example.userdemo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userdemo.baseRepo.ProductRepository
import com.example.userdemo.data.model.response.Product
import com.example.userdemo.local.AppDatabase
import com.example.userdemo.recycler.adapter.ProductAdapter
import com.example.userdemo.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = AppDatabase.getDatabase(application)
        val productDao = database.productDao()
        val repository = ProductRepository(productDao)

        viewModel = ProductViewModel(repository)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.products.observe(this) { products ->
            adapter = ProductAdapter(products.map { productEntity ->
                Product(
                    id = productEntity.id,
                    title = productEntity.title,
                    description = productEntity.description,
                    image = productEntity.image,
                )
            })
            recyclerView.adapter = adapter
        }
    }
}
