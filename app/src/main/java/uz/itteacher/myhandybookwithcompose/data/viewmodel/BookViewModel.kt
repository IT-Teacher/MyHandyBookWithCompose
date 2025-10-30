package uz.itteacher.myhandybookwithcompose.data.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.itteacher.myhandybookwithcompose.data.RetrofitClient
import uz.itteacher.myhandybookwithcompose.data.model.Book
import uz.itteacher.myhandybookwithcompose.data.repository.BooksRepository

class BooksViewModel : ViewModel() {
    private val repository = BooksRepository(RetrofitClient.booksApi)

    var selectedBook = mutableStateOf<Book?>(null)
    var isLoading = mutableStateOf(true)
    var error = mutableStateOf<String?>(null)

    fun loadBookById(id: Int) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                selectedBook.value = repository.getBookById(id)
            } catch (e: Exception) {
                error.value = e.message
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}
