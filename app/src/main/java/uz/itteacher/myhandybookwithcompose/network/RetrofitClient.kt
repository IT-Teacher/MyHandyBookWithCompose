package uz.itteacher.myhandybookwithcompose.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.itteacher.myhandybookwithcompose.api.HandyBookApi
import uz.itteacher.myhandybookwithcompose.MyApp
import timber.log.Timber

object RetrofitClient {
    private const val BASE_URL = "http://handybook.uz/"

    val api: HandyBookApi by lazy {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val token = getAccessToken()
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                try {
                    chain.proceed(request)
                } catch (e: Exception) {
                    Timber.e(e, "Network request failed for ${request.url}")
                    throw e
                }
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
            .create(HandyBookApi::class.java)
    }

    private fun getAccessToken(): String {
        return try {
            val prefs = MyApp.instance.getSharedPreferences("HandyBookPrefs", android.content.Context.MODE_PRIVATE)
            prefs.getString("access_token", "") ?: ""
        } catch (e: UninitializedPropertyAccessException) {
            Timber.e(e, "MyApp.instance not initialized")
            ""
        }
    }
}