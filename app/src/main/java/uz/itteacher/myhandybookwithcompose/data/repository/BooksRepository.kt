package uz.itteacher.myhandybookwithcompose.data.repository

import uz.itteacher.myhandybookwithcompose.data.RetrofitClient
import uz.itteacher.myhandybookwithcompose.data.api.BooksApi
import uz.itteacher.myhandybookwithcompose.data.model.Book
import uz.itteacher.myhandybookwithcompose.data.model.Category

class BooksRepository(private val api: BooksApi) {
    suspend fun getBooks(): List<Book> = api.getBooks()
    suspend fun getBookById(id: Int): Book = api.getBookById(id)
    suspend fun getCategories(): List<Category> {
        return RetrofitClient.booksApi.getAllCategories()
    }
}
