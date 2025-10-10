package uz.itteacher.myhandybookwithcompose.models

data class Comment(
    val username: String,
    val text: String
)

data class CommentRequest(
    val book_id: Int,
    val user_id: Int,
    val text: String,
    val reyting: Int
)

data class CommentResponse(
    val book_id: String,
    val user_id: String,
    val text: String,
    val reyting: String,
    val id: Int
)