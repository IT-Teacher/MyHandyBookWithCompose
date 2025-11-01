package uz.itteacher.myhandybookwithcompose.Screens.OpenBook

import android.app.DownloadManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import uz.itteacher.myhandybookwithcompose.screens.openbook.PdfViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun PdfReaderScreen(
    viewModel: PdfViewModel,
    pdfUrl: String,
    onError: (String) -> Unit
) {
    val context = LocalContext.current
    val pdfFile by viewModel.pdfFile.collectAsState()
    val isDownloading by viewModel.isDownloading.collectAsState()
    val isOpening by viewModel.isOpening.collectAsState()
    val lastReadPage by viewModel.lastReadPage.collectAsState()

    // 🧩 Start download once
    LaunchedEffect(pdfUrl) {
        viewModel.downloadPdf(context, pdfUrl)
    }

    when {
        isDownloading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Text("Downloading PDF...")
                }
            }
        }

        pdfFile == null -> {
            Text("Failed to load PDF.")
        }

        else -> {
            Box(Modifier.fillMaxSize()) {
                AndroidView(
                    factory = { ctx ->
                        PDFView(ctx, null).apply {
                            viewModel.setOpeningState(true)
                            fromFile(pdfFile)
                                .defaultPage(lastReadPage)
                                .onPageChange { page, pageCount ->
                                    viewModel.updateReadingProgress(page, pageCount)
                                }
                                .onLoad {
                                    viewModel.setOpeningState(false)
                                }
                                .onError {
                                    viewModel.setOpeningState(false)
                                    onError(it.message ?: "Error loading PDF")
                                }
                                .load()
                        }
                    },
                    update = { view ->
                        // Optional: If user reopened the same PDF, ensure it reopens from last page
                        view.jumpTo(lastReadPage)
                    },
                    modifier = Modifier.fillMaxSize()
                )

                if (isOpening) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color(0x88000000)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Text("Opening PDF...")
                        }
                    }
                }
            }
        }
    }
}
