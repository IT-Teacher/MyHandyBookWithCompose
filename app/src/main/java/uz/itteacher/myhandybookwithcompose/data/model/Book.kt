package uz.itteacher.myhandybookwithcompose.data.model

data class Book(
    val id: Int,
    val name: String?,
    val type_id: Int?,
    val file: String?,
    val audio: String?,
    val year: String?,
    val author: String?,
    val status: Int?,
    val reyting: Int?,
    val description: String?,
    val image: String?,
    val lang: String?,
    val count_page: Int?,
    val publisher: String?
)
