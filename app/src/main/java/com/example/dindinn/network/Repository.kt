package com.example.dindinn.network

import com.example.dindinn.entity.IngredientResponse
import com.example.dindinn.entity.OrderResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val webService: WebService
) {

    fun getOrders(): Flow<List<OrderResponse>> {
        return flow {
            emit(webService.getOrders())
        }
    }

    fun getIngredients(id: Int, keyword: String?): Flow<List<IngredientResponse>> {
        return flow {
            emit(webService.getIngredientById(id, keyword))
        }
    }
}
