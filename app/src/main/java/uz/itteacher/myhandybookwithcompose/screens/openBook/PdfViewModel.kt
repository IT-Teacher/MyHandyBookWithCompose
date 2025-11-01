package uz.itteacher.myhandybookwithcompose.screens.openbook

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class PdfViewModel : ViewModel() {

    // 1️⃣ File path (once downloaded)
    private val _pdfFile = MutableStateFlow<File?>(null)
    val pdfFile = _pdfFile.asStateFlow()

    // 2️⃣ Downloading state
    private val _isDownloading = MutableStateFlow(false)
    val isDownloading = _isDownloading.asStateFlow()

    // 3️⃣ Reading (page) progress
    private val _readingProgress = MutableStateFlow(0f)
    val readingProgress = _readingProgress.asStateFlow()

    // 4️⃣ Open (loading) state — when PDFView is rendering
    private val _isOpening = MutableStateFlow(false)
    val isOpening = _isOpening.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

    private val _totalPages = MutableStateFlow(0)
    val totalPages = _totalPages.asStateFlow()

    private val _lastReadPage = MutableStateFlow(0)
    val lastReadPage = _lastReadPage.asStateFlow()

    fun downloadPdf(context: Context, url: String) {
        // If already downloaded, skip
        if (_pdfFile.value != null) return

        viewModelScope.launch(Dispatchers.IO) {
            _isDownloading.value = true
            try {
                val file = downloadPdfFile(context, url)
                _pdfFile.value = file
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isDownloading.value = false
            }
        }
    }

    fun updateReadingProgress(page: Int, total: Int) {
        _currentPage.value = page
        _totalPages.value = total
        _lastReadPage.value = page
        _readingProgress.value = (page.toFloat() / total)
    }

    fun setOpeningState(opening: Boolean) {
        _isOpening.value = opening
    }

    private fun downloadPdfFile(context: Context, url: String): File {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()

        val file = File(context.cacheDir, "book.pdf")
        connection.inputStream.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        connection.disconnect()
        return file
    }
}

