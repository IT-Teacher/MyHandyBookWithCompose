package uz.itteacher.myhandybookwithcompose.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import uz.itteacher.myhandybookwithcompose.models.Book

@Composable
fun MainScreen(viewModel: BookViewModel) {
    LaunchedEffect(Unit) {
        viewModel.fetchAllBooks()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "HandyBook Library",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(16.dp)
        )

        if (viewModel.isLoading.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (viewModel.errorMessage.value != null) {
            Text(
                text = viewModel.errorMessage.value ?: "Unknown error",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn {
                items(viewModel.books.value) { book ->
                    BookItem(book)
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            book.image?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = book.name,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column {
                Text(text = book.name, style = MaterialTheme.typography.h6)
                Text(text = book.author ?: "Unknown Author", style = MaterialTheme.typography.body2)
            }
        }
    }
}