package uz.itteacher.myhandybookwithcompose.models

data class User(
    val id: Int,
    val user_name: String,
    val full_name: String,
    val access_token: String
)