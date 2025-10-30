package uz.itteacher.myhandybookwithcompose.screens.tabs

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import uz.itteacher.myhandybookwithcompose.data.RetrofitClient
import uz.itteacher.myhandybookwithcompose.data.model.Book
import uz.itteacher.myhandybookwithcompose.ui.theme.AccentCyan
import uz.itteacher.myhandybookwithcompose.ui.theme.PrimaryBlue

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DescriptionTab(bookId: Int) {
    var books by remember { mutableStateOf<List<Book>>(emptyList()) }
    var message by remember { mutableStateOf("Kitob yuklanmagan") }
    LaunchedEffect(bookId) {
        try {
            message = "Yuklanmoqda..."

            val currentBook = RetrofitClient.booksApi.getBookById(bookId)
            Log.d("API_BOOK_DETAIL", "Name: ${currentBook.name}, Author: ${currentBook.author}")

            val allBooks = RetrofitClient.booksApi.getBooks()


            val sameCategoryBooks = allBooks.filter {
                it.type_id == currentBook.type_id && it.id != currentBook.id
            }


            books = if (sameCategoryBooks.size >= 3) {
                sameCategoryBooks.take(3)
            } else {
                sameCategoryBooks
            }

            message = currentBook.description ?: "Tavsif mavjud emas"

            Log.d(
                "API_BOOK_DETAIL",
                "Category ID: ${currentBook.type_id}, Similar books: ${books.map { it.name }}"
            )

        } catch (e: Exception) {
            Log.e("API_ERROR", "Xatolik: ${e.message}", e)
            message = "Xatolik: ${e.message}"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoItem(icon = Icons.Outlined.Book, text = "209 bet")
                InfoItem(icon = Icons.Outlined.Headphones, text = "12 soat")
                InfoItem(icon = Icons.Outlined.Language, text = "O'zbek tilida")
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = message,
                    fontSize = 17.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF1F2937)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Column {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tavsiyalar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PrimaryBlue
                )
                Text(
                    text = "Barchasini ko‘rish",
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp,
                    color = AccentCyan,
                    modifier = Modifier.clickable {
                        // TODO: open full list
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(books) { book ->
                    BookCard(
                        imageUrl = book.image.toString(),
                        name = book.name.toString(),
                        author = book.author.toString()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "O‘qishni davom ettirish   87%",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}


@Composable
fun InfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF9CA3AF),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text, fontSize = 14.sp, color = Color(0xFF9CA3AF), fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun BookCard(
    imageUrl: String, name: String, author: String
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        val painter =
            rememberAsyncImagePainter(model = imageUrl.ifBlank { "https://via.placeholder.com/150x220.png?text=No+Image" })

        Image(
            painter = painter,
            contentDescription = name,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = PrimaryBlue,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = author,
            fontSize = 13.sp,
            color = Color(0xFF6B7280),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
