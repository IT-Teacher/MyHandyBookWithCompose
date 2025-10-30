package uz.itteacher.myhandybookwithcompose.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.itteacher.myhandybookwithcompose.data.model.Comment
import uz.itteacher.myhandybookwithcompose.data.repository.CommentsRepository

class CommentsViewModel(private val repository: CommentsRepository) : ViewModel() {

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments = _comments.asStateFlow()

    fun loadComments(bookId: Int) {
        viewModelScope.launch {
            try {
                _comments.value = repository.getComments(bookId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
