package uz.itteacher.myhandybookwithcompose.api

import uz.itteacher.myhandybookwithcompose.models.*
import uz.itteacher.myhandybookwithcompose.network.RetrofitClient

class HandyBookRepository(private val api: HandyBookApi = RetrofitClient.api) {

    suspend fun getAllBooks(): List<Book> =
        api.getAllBooks()

    suspend fun getBook(id: Int): Book =
        api.getBook(id)

    suspend fun login(request: LoginRequest): User =
        api.login(request)

    suspend fun register(request: RegisterRequest): User =
        api.register(request)

    suspend fun getMainBook(): Book =
        api.getMainBook()

    suspend fun getAllCategories(): List<Category> =
        api.getAllCategories()

    suspend fun getBooksByCategory(categoryName: String): List<Book> =
        api.getBooksByCategory(categoryName)

    suspend fun getComments(bookId: Int): List<Comment> =
        api.getComments(bookId)

    suspend fun postComment(request: CommentRequest): CommentResponse =
        api.postComment(request)

    suspend fun searchBooks(searchTerm: String): List<Book> =
        api.searchBooks(searchTerm)
}
