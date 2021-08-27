package com.example.dindinn.network

import com.example.dindinn.entity.IngredientResponse
import com.example.dindinn.entity.OrderResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class WebService @Inject constructor() {

    private var apiClient: ApiClient

    companion object {
        private const val BASE_URL = "https://www.bing.com/"
    }

    init {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(MockInterceptor())
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(httpClient.build())
            .baseUrl(BASE_URL).build()

        apiClient = retrofit.create(ApiClient::class.java)
    }

    suspend fun getOrders(): List<OrderResponse> {
        return apiClient.getOrders()
    }

    suspend fun getIngredientById(id: Int, keyword: String?): List<IngredientResponse> {
        return apiClient.getIngredient(id, keyword)
    }


}
