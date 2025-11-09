package uz.itteacher.myhandybookwithcompose.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.itteacher.myhandybookwithcompose.models.Book
import uz.itteacher.myhandybookwithcompose.models.Category
import uz.itteacher.myhandybookwithcompose.models.LoginRequest
import uz.itteacher.myhandybookwithcompose.models.RegisterRequest
import uz.itteacher.myhandybookwithcompose.network.RetrofitClient
import uz.itteacher.myhandybookwithcompose.network.TokenManager

class ViewModel : ViewModel() {
    var books = mutableStateOf<List<Book>>(emptyList())
        private set
    var categories = mutableStateOf<List<Category>>(emptyList())
    var mainBook = mutableStateOf<Book?>(null)
    var isLoading = mutableStateOf(false)
        private set
    var errorMessage = mutableStateOf<String?>(null)
        private set
    var isLoggedIn = mutableStateOf(false)
        private set
    var booksByCategory = mutableStateOf<Map<String, List<Book>>>(emptyMap())
        private set

    var selectedBook by mutableStateOf<Book?>(null)

    fun fetchAllBooks() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitClient.api.getAllBooks()
                books.value = response
                errorMessage.value = null
            } catch (e: Exception) {
                Timber.e(e, "Error fetching HandyBook books")
                errorMessage.value = "Failed to load books: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    init {
        viewModelScope.launch {
            fetchCategories()
            fetchMainBook()
            fetchAllBooks()
        }
    }

    fun fetchHomePageData() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val categoryList = RetrofitClient.api.getAllCategories()
                categories.value = categoryList

                val map = mutableMapOf<String, List<Book>>()

                categoryList.forEach { cat ->
                    val books = RetrofitClient.api.getBooksByCategory(cat.type_name)
                    map[cat.type_name] = books
                }

                booksByCategory.value = map
                mainBook.value = RetrofitClient.api.getMainBook()

            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                categories.value = RetrofitClient.api.getAllCategories()
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }

    fun fetchBooksByCategory(name: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                books.value = RetrofitClient.api.getBooksByCategory(name)
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }

    fun fetchMainBook() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                mainBook.value = RetrofitClient.api.getMainBook()
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }

    fun searchBooksByName(searchTerm: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                books.value = RetrofitClient.api.searchBooks(searchTerm)
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = "Search failed: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }



//    AUTH

    fun login(username: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val user = RetrofitClient.api.login(LoginRequest(username, password))
                TokenManager.saveToken(user.access_token)
                isLoggedIn.value = true
                errorMessage.value = null
            } catch (e: Exception) {
                Timber.e(e, "HandyBook login failed")
                errorMessage.value = "Login failed: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val user = RetrofitClient.api.register(request)
                TokenManager.saveToken(user.access_token)
                isLoggedIn.value = true
            } catch (e: Exception) {
                Timber.e(e, "Registration failed")
                errorMessage.value = "Ro‘yxatdan o‘tishda xato: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}