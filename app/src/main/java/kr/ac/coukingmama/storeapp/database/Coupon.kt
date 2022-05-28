package kr.ac.coukingmama.storeapp.database

import com.google.gson.annotations.SerializedName

data class Coupon(
    @SerializedName("storeId")
    val storeId: String,
    @SerializedName("num")
    val num: String
)