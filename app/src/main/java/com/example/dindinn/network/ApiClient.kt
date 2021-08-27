package com.example.dindinn.network

import com.example.dindinn.entity.IngredientResponse
import com.example.dindinn.entity.OrderResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("orders")
    suspend fun getOrders(): List<OrderResponse>

    @GET("category")
    suspend fun getIngredient(
        @Query("id") key: Int,
        @Query("keyword") keyword: String?
    ): List<IngredientResponse>
}
