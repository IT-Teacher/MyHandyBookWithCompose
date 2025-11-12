package uz.ictschool.search.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import timber.log.Timber
import uz.itteacher.myhandybookwithcompose.api.HandyBookRepository
import uz.itteacher.myhandybookwithcompose.models.Book
import uz.itteacher.myhandybookwithcompose.screens.ViewModel
import kotlin.collections.map
import kotlin.text.isNotEmpty
import kotlin.text.isNullOrBlank
import kotlin.text.trim

// UI model
data class MediaItem(
    val id: String,
    val title: String,
    val author: String,
    val price: String,
    val rating: String,
    val thumbnailUrl: String?

)

@Composable
fun SearchScreen(viewModel: ViewModel, navController: NavController) {
    val queryState = remember { mutableStateOf("") }
    val repository = remember { HandyBookRepository() }

    var books by remember { mutableStateOf<List<MediaItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    // Trigger search when query changes
    LaunchedEffect(queryState.value) {
        val query = queryState.value.trim()
        if (query.isNotEmpty()) {
            isLoading = true
            error = null
            try {
                val results: List<Book> = repository.searchBooks(query)
                Timber.d("Search results for '$query': $results")

                books = results.map { book ->
                    MediaItem(
                        id = book.id.toString(),
                        title = book.name ?: "No Title",
                        author = book.author ?: "Unknown Author",
                        price = "", // price not available
                        rating = book.reyting?.toString() ?: "0",
                        thumbnailUrl = book.image
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "Search failed")
                error = "Search failed. Please try again."
                books = emptyList()
            } finally {
                isLoading = false
            }
        } else {
            books = emptyList()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Qidiruv") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                SearchBar(
                    query = queryState.value,
                    onQueryChange = { queryState.value = it },
                    onSearch = {}
                )
                Spacer(modifier = Modifier.height(12.dp))

                when {
                    isLoading -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }

                    error != null -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { Text(error!!, color = Color.Red) }

                    books.isEmpty() -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { Text("No results found") }

                    else -> LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 88.dp)
                    ) {
                        items(books) { item ->
                            MediaCard(item) {}
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun SearchBar(query: String, onQueryChange: (String) -> Unit, onSearch: () -> Unit) {
    val isInPreview = LocalInspectionMode.current
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        placeholder = { Text("Qidiruv...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        trailingIcon = {
            if (query.isNotEmpty() && !isInPreview) {
                TextButton(onClick = { onQueryChange("") }) { Text("Tozalash") }
            }
        },
        singleLine = true,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    )
}

@Composable
private fun MediaCard(item: MediaItem, onClick: (MediaItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(item) },
        elevation = 4.dp,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Thumbnail(url = item.thumbnailUrl)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.author,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PriceTag(price = item.price)
                    Spacer(modifier = Modifier.width(8.dp))
                    RatingBadge(rating = item.rating)
                }
            }
        }
    }
}

@Composable
private fun Thumbnail(url: String?) {
    val placeholder =
        rememberAsyncImagePainter(model = "https://via.placeholder.com/96x140.png?text=Cover")
    Box(
        modifier = Modifier
            .size(width = 80.dp, height = 120.dp)
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
            .background(Color.LightGray)
    ) {
        if (url.isNullOrBlank()) {
            Image(
                painter = placeholder,
                contentDescription = "thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            AsyncImage(
                model = url,
                contentDescription = "Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun PriceTag(price: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFEFF6FF),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = price,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
private fun RatingBadge(rating: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFFFF7E1),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "★", color = Color(0xFFFFA000), modifier = Modifier.padding(end = 4.dp))
            Text(
                text = rating,
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

