package com.example.shopnest.repository

import com.example.shopnest.api.ApiResponse
import com.example.shopnest.api.ShopApiService
import com.example.shopnest.api.callApi
import com.example.shopnest.model.ShopData
import javax.inject.Inject

class ShopRepository @Inject constructor(private val shopApiService: ShopApiService) {
    suspend fun getShopCartData(): ApiResponse<List<ShopData>> {
        return callApi(
            apiRequest = { shopApiService.getShopData() })
    }
}

