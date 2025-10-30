package uz.itteacher.myhandybookwithcompose.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import uz.itteacher.myhandybookwithcompose.data.model.Book
import uz.itteacher.myhandybookwithcompose.data.model.Category

interface BooksApi {

    @GET("book-api")
    suspend fun getBooks(): List<Book>

    @GET("book-api/view")
    suspend fun getBookById(@Query("id") id: Int): Book

    @GET("book-api/all-category")
    suspend fun getAllCategories(): List<Category>
}
