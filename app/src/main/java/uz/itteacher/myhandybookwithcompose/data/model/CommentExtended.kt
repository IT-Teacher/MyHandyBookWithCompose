package uz.itteacher.myhandybookwithcompose.data.model

data class CommentExtended(
    val book_id: Int,
    val user_id: Int,
    val username: String,
    val text: String,
    val reyting: Int,
    val id: Int,
    val created_at: String
)
