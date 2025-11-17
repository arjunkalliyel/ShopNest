package com.example.shopnest.api

import com.example.shopnest.model.ShopData
import retrofit2.Response
import retrofit2.http.GET

interface ShopApiService {
    @GET("products")
    suspend fun getShopData(): Response<List<ShopData>>
}