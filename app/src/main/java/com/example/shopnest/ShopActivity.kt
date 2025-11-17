package com.example.shopnest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.shopnest.adapter.ShopAdapter
import com.example.shopnest.api.ApiResponse
import com.example.shopnest.databinding.ActivityShopBinding
import com.example.shopnest.model.ShopData
import com.example.shopnest.viewModel.ShopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopActivity : AppCompatActivity() {
    private val viewModel: ShopViewModel by viewModels()
    private lateinit var shopAdapter: ShopAdapter
    private lateinit var binding: ActivityShopBinding
    private val shoppeList = ArrayList<ShopData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        viewModel.getShopData()
        observeShopData()
    }

    private fun setupRecyclerView() {
        shopAdapter = ShopAdapter(this, shoppeList)
        binding.rvList.adapter = shopAdapter
    }

    private fun observeShopData() {
        lifecycleScope.launch {
            viewModel.shopItem.collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        Log.d("ShopData", "Loading...")
                    }

                    is ApiResponse.Success -> {
                        binding.pbProgress.visibility = View.GONE
                        val data = response.data
                        shoppeList.clear()
                        shoppeList.addAll(data)
                        shopAdapter.notifyDataSetChanged()
                    }

                    is ApiResponse.HttpError -> {
                        binding.pbProgress.visibility = View.GONE
                        Log.e("ShopData", "Error: ${response.message}")
                    }

                    is ApiResponse.NetworkError -> {
                        binding.pbProgress.visibility = View.GONE
                        Log.e("ShopData", "Network Error")
                    }

                    is ApiResponse.Canceled -> {
                        binding.pbProgress.visibility = View.GONE
                        Log.e("ShopData", "Request Cancelled")
                    }
                }
            }
        }
    }
}