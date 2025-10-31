package uz.itteacher.myhandybookwithcompose.api

import retrofit2.http.*
import uz.itteacher.myhandybookwithcompose.models.Book
import uz.itteacher.myhandybookwithcompose.models.Category
import uz.itteacher.myhandybookwithcompose.models.Comment
import uz.itteacher.myhandybookwithcompose.models.CommentRequest
import uz.itteacher.myhandybookwithcompose.models.CommentResponse
import uz.itteacher.myhandybookwithcompose.models.LoginRequest
import uz.itteacher.myhandybookwithcompose.models.RegisterRequest
import uz.itteacher.myhandybookwithcompose.models.User

interface HandyBookApi {

    // 1. Get All Books
    @GET("book-api/")
    suspend fun getAllBooks(): List<Book>

    // 2. Get Single Book
    @GET("book-api/view")
    suspend fun getBook(@Query("id") id: Int): Book

    // 3. User Login
    @POST("book-api/login")
    suspend fun login(@Body request: LoginRequest): User

    // 4. User Registration
    @POST("book-api/register")
    suspend fun register(@Body request: RegisterRequest): User

    // 5. Get Main Book
    @GET("book-api/main-book")
    suspend fun getMainBook(): Book

    // 6. Get All Categories
    @GET("book-api/all-category")
    suspend fun getAllCategories(): List<Category>

    // 7. Filter Books by Category
    @GET("book-api/category")
    suspend fun getBooksByCategory(@Query("name") categoryName: String): List<Book>

    // 8. Get Comments for a Book
    @GET("book-api/comment")
    suspend fun getComments(@Query("id") bookId: Int): List<Comment>

    // 9. Post a Comment
    @POST("comment-api/create")
    suspend fun postComment(@Body request: CommentRequest): CommentResponse

    // 10. Search Books by Name
    @GET("book-api/search-name")
    suspend fun searchBooks(@Query("name") searchTerm: String): List<Book>
}