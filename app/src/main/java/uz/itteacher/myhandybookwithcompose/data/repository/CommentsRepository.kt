package uz.itteacher.myhandybookwithcompose.data.repository

import uz.itteacher.myhandybookwithcompose.data.api.CommentsApi

class CommentsRepository(private val api: CommentsApi) {
    suspend fun getComments(bookId: Int) = api.getComments(bookId)
}
