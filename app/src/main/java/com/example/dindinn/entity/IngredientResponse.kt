package com.example.dindinn.entity

import com.google.gson.annotations.SerializedName

data class IngredientResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("quantity") val quantity: Int
)
