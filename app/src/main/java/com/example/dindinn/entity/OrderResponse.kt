package com.example.dindinn.entity

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("alerted_at") val alerted_at: String,
    @SerializedName("expired_at") val expired_at: String,
    @SerializedName("addon") val addon: List<Addon>
)

data class Addon(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("quantity") val quantity: Int,
)
