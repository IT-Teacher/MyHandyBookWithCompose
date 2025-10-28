package uz.itteacher.myhandybookwithcompose.Screens.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uz.itteacher.myhandybookwithcompose.api.HandyBookApi
import uz.itteacher.myhandybookwithcompose.models.Book

@Composable
fun ProfileScreen(navController: NavHostController, api: HandyBookApi) {
    var allBooks by remember { mutableStateOf<List<Book>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try { allBooks = api.getAllBooks() } catch (e: Exception) { }
        loading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp, top = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
            Text("Shaxsiy kabinet", fontSize = 20.sp, fontWeight = FontWeight.Medium)
            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(Icons.Default.Settings, null)
            }
        }

        Spacer(Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(Color.White)
        )
        Spacer(Modifier.height(12.dp))
        Text("Azizov Ali", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text("abcdef@gmail.com", fontSize = 14.sp, color = Color.Gray)
        Spacer(Modifier.height(24.dp))

        StatsCard(navController)

        Spacer(Modifier.height(32.dp))
        BookSection(navController, "O‘qilayotgan kitoblar", allBooks.take(2), "reading")
        Spacer(Modifier.height(24.dp))
        BookSection(navController, "O‘qilgan kitoblar", allBooks.take(4), "read")
        Spacer(Modifier.height(24.dp))
        BookSection(navController, "Saqlangan kitoblar", allBooks.take(3), "saved")
    }
}

@Composable
private fun StatsCard(navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem("2", "o‘qilayotgan\nkitoblar", onClick = { navController.navigate("reading") })
            StatItem("17", "o‘qilgan\nkitoblar", onClick = { navController.navigate("read") })
            StatItem("9", "saqlangan\nkitoblar", onClick = { navController.navigate("saved") })
        }
    }
}

@Composable
private fun StatItem(count: String, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(count, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 12.sp, color = Color.Gray, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
    }
}

@Composable
private fun BookSection(navController: NavHostController, title: String, books: List<Book>, route: String) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.Medium)
            TextButton(onClick = { navController.navigate(route) }) {
                Text("Barchasini ko‘rish", color = Color.Blue)
            }
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(books.size) { index ->
                BookItem(books[index])
            }
        }
    }
}

@Composable
private fun BookItem(book: Book) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = book.image,
            contentDescription = null,
            modifier = Modifier
                .size(width = 100.dp, height = 140.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
        )
        Spacer(Modifier.height(8.dp))
        Text(book.name, fontSize = 12.sp, maxLines = 1)
        book.author?.let { Text(it, fontSize = 10.sp, color = Color.Gray, maxLines = 1) }
    }
}

@Composable
fun BookGridItem(book: Book) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AsyncImage(
                model = book.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color(0xFF1E88E5), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Star, null, tint = Color.Yellow, modifier = Modifier.size(14.dp))
                Text(
                    text = String.format("%.1f", book.reyting?.toFloat() ?: 0f),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = book.name,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            maxLines = 2,
            color = Color.Black
        )
        Text(
            text = book.author ?: "",
            fontSize = 12.sp,
            color = Color.Gray,
            maxLines = 1
        )
        Text(
            text = "$10.65", // Placeholder price
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color(0xFF1E88E5)
        )
    }
}