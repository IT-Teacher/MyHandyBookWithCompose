package uz.itteacher.myhandybookwithcompose.models

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val fullname: String,
    val email: String,
    val password: String
)