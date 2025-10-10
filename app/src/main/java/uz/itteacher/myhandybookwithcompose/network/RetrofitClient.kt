package uz.itteacher.myhandybookwithcompose.network

import android.R.attr.level
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.itteacher.myhandybookwithcompose.api.HamdyBookApi

object RetrofitClient {
    private const val BASE_URL = "http://handybook.uz/"

    val api: HamdyBookApi by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val token = getAccessToken() // Implement this to retrieve the stored token
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(HamdyBookApi::class.java)
    }

    private fun getAccessToken(): String {
        // Implement logic to retrieve the access token (e.g., from SharedPreferences)
        return "your_access_token_here" // Replace with actual token retrieval
    }
}