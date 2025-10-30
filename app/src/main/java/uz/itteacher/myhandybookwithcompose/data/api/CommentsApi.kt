package uz.itteacher.myhandybookwithcompose.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import uz.itteacher.myhandybookwithcompose.data.model.Comment

interface CommentsApi {
    @GET("book-api/comment")
    suspend fun getComments(@Query("id") bookId: Int): List<Comment>
}