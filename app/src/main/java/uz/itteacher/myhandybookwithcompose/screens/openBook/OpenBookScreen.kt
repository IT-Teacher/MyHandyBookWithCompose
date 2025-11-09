package uz.itteacher.myhandybookwithcompose.screens.openBook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OpenBookScreen(
    viewModel: PdfViewModel,
    onOpenBook: (String) -> Unit
) {
    val readingProgress by viewModel.readingProgress.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Reading progress: ${(readingProgress * 100).toInt()}%")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { onOpenBook("http://handybook.uz/frontend/web/file/701697625957.pdf") }) {
            Text("Open Book")
        }
    }
}

