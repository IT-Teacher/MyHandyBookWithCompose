package uz.itteacher.myhandybookwithcompose.Screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.itteacher.myhandybookwithcompose.models.Book
import uz.itteacher.myhandybookwithcompose.models.LoginRequest
import uz.itteacher.myhandybookwithcompose.models.RegisterRequest
import uz.itteacher.myhandybookwithcompose.network.RetrofitClient
import uz.itteacher.myhandybookwithcompose.network.TokenManager

class BookViewModel : ViewModel() {
    var books = mutableStateOf<List<Book>>(emptyList())
        private set
    var isLoading = mutableStateOf(false)
        private set
    var errorMessage = mutableStateOf<String?>(null)
        private set
    var isLoggedIn = mutableStateOf(false)
        private set

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