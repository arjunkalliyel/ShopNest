package com.example.shopnest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopnest.api.ApiResponse
import com.example.shopnest.model.ShopData
import com.example.shopnest.repository.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(private val repository: ShopRepository) :
    ViewModel() {
    private val _shopItem = MutableStateFlow<ApiResponse<List<ShopData>>>(ApiResponse.Loading)
    val shopItem: StateFlow<ApiResponse<List<ShopData>>> = _shopItem

    fun getShopData() {
        viewModelScope.launch {
            _shopItem.value = ApiResponse.Loading
            _shopItem.value = repository.getShopCartData()
        }
    }
}


