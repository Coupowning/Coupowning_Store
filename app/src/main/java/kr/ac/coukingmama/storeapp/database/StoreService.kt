package kr.ac.coukingmama.storeapp.database

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface StoreService {
    @POST("stores")
    @Headers("Content-Type: application/json")
    fun postStore(
        @Body store : Store
    ): Call<Store>

    @GET("stores/{storeId}")
    fun getStore(
        @Path("storeId") storeId: String
    ) : Call<Store>

    @POST("addCoupon/{userId}")
    fun addCoupon(
        @Path("userId") userId: String,
        @Body coupon: Coupon
    ) : Call<Coupon>

    companion object{
        private const val BASE_URL = "https://us-central1-coupowning.cloudfunctions.net/widgets/"

        fun create(): StoreService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val gson : Gson = GsonBuilder().setLenient().create()
            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .build()
                return@Interceptor it.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(StoreService::class.java)
        }
    }
}