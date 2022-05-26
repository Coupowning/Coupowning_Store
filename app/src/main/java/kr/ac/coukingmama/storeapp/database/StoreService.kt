package kr.ac.coukingmama.storeapp.database

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface StoreService {
    @POST("/stores")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun postStore(
        @Body store : Store
    ): Call<String>
}