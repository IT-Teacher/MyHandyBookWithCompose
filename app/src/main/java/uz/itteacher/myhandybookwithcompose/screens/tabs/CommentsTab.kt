package uz.itteacher.myhandybookwithcompose.screens.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import uz.itteacher.myhandybookwithcompose.data.RetrofitClient
import uz.itteacher.myhandybookwithcompose.data.model.Comment
import uz.itteacher.myhandybookwithcompose.data.model.CommentExtended
import uz.itteacher.myhandybookwithcompose.screens.components.CommentItem

@Composable
fun CommentsTab(bookId: Int){
    var comments by remember { mutableStateOf<List<Comment>>(emptyList()) }
    val scope = rememberCoroutineScope()


    LaunchedEffect(bookId) {
        scope.launch {
            try {
                comments = RetrofitClient.commentsApi.getComments(bookId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    val ratedComments = comments.map {
        RatedComment(
            username = it.username,
            text = it.text,
            reyting = 5
        )
    }


    val averageRating = if (ratedComments.isNotEmpty()) {
        ratedComments.map { it.reyting }.average()
    } else 0.0

    Box(modifier = Modifier.fillMaxWidth()) {
        if (ratedComments.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sharhlar hali mavjud emas 😶",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ratedComments) { comment ->
                    CommentItem(
                       CommentExtended(
                            book_id = bookId,
                            user_id = 0,
                            username = comment.username,
                            text = comment.text,
                            reyting = comment.reyting,
                            id = 0,
                            created_at = ""
                        )
                    )
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            BottomRatingBar(averageRating)
        }
    }
}


data class RatedComment(
    val username: String,
    val text: String,
    val reyting: Int
)

@Composable
fun BottomRatingBar(averageRating: Double) {
    Surface(
        color = Color(0xFFF0F8FF),
        shadowElevation = 8.dp,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = String.format("%.1f", averageRating),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color(0xFF1B87C7)
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))


                Row {
                    repeat(5) { index ->
                        val isFilled = index < averageRating.toInt()
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = if (isFilled) Color(0xFF4CB3FF) else Color(0xFFB0DAF5),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }


            Button(
                onClick = { /* TODO: open comment input */ },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDBF3FF),
                    contentColor = Color(0xFF1B87C7)
                ),
                modifier = Modifier
                    .height(60.dp)
                    .width(200.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "O‘z sharhingizni",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        text = "yozib qoldiring",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    )
                }
            }
        }
    }
}
