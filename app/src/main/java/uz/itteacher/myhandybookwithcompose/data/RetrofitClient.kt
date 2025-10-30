package uz.itteacher.myhandybookwithcompose.data

import uz.itteacher.myhandybookwithcompose.data.api.BooksApi
import uz.itteacher.myhandybookwithcompose.data.api.CommentsApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {
    private const val BASE_URL = "http://handybook.uz/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val booksApi: BooksApi by lazy {
        retrofit.create(BooksApi::class.java)
    }

    val commentsApi: CommentsApi by lazy {
        retrofit.create(CommentsApi::class.java)
    }
}
