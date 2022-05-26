package kr.ac.coukingmama.storeapp.database

import com.google.gson.annotations.SerializedName

data class StoreLocation(
    @SerializedName("locationKr")
    val locationKr: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String
)