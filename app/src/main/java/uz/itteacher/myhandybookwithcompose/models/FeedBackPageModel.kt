package uz.itteacher.myhandybookwithcompose.models

// ReviewViewModel.kt
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReviewViewModel : ViewModel() {
    // Rating state (0.0 to 5.0 stars)
    val rating = mutableStateOf(0f)
    val reviewText = mutableStateOf("")

    fun updateRating(newRating: Float) {
        rating.value = newRating
    }

    fun updateReviewText(text: String) {
        reviewText.value = text
    }
}
