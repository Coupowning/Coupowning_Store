package kr.ac.coukingmama.storeapp.database

import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("storeName")
    val storeName: String,
    @SerializedName("storeLocation")
    val storeLocation: StoreLocation,
    @SerializedName("storePhone")
    val storePhone: String,
    @SerializedName("storeDesc")
    val storeDesc: String,
    @SerializedName("storeId")
    val storeId: String,
    @SerializedName("storeImage")
    val storeImage : Map<Int, String>
)