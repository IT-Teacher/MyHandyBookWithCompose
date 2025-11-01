package uz.itteacher.myhandybookwithcompose.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uz.itteacher.myhandybookwithcompose.R
import uz.itteacher.myhandybookwithcompose.screens.ViewModel
import uz.itteacher.myhandybookwithcompose.models.Book

@Composable
fun CategoryPage(viewModel: ViewModel, categoryName: String?, navController: NavController) {
    val books = viewModel.books.value

    LaunchedEffect(categoryName) {
        if (categoryName != "search") {
            viewModel.fetchBooksByCategory(categoryName ?: "")
        }
    }

    Scaffold(
        topBar = { HTopBar(title = categoryName ?: "Romanlar", navController) },
        bottomBar = { BottomNavBar(navController) },
        backgroundColor = Color.White
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 12.dp + 45.dp,
                end = 12.dp,
                bottom = 12.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(books) { book -> BookCard(book) }
        }
    }
}

@Composable
fun BookCard(book: Book) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box {
                Image(
                    painter = rememberAsyncImagePainter(book.image),
                    contentDescription = book.name,
                    modifier = Modifier
                        .height(170.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                // ⭐ Rating overlay (top-right corner)
                if (book.reyting != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.8f),
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = Color(0xFF0099FF),
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = String.format("%.1f", book.reyting.toFloat()),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0099FF),
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.author ?: "Unknown Author",
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$" + String.format("%.2f", (book.id * 1.5) + 8.0), // dummy price
                fontSize = 13.sp,
                color = Color(0xFF00BFA6),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
@Composable
fun HTopBar(title: String, navController: NavController) {
    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Menu",
            tint = Color(0xFF0A2342),
            modifier = Modifier.size(24.dp).clickable { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = title,
            color = Color(0xFF0A2342),
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.account),
            contentDescription = "Profile",
            tint = Color(0xFF0A2342),
            modifier = Modifier.size(32.dp)
        )
    }
}

