package uz.itteacher.myhandybookwithcompose.data

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import uz.itteacher.myhandybookwithcompose.data.RetrofitClient
import uz.itteacher.myhandybookwithcompose.data.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiTestScreen() {
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var inputId by remember { mutableStateOf(TextFieldValue("")) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    // 🔹 Загружаем все категории один раз
    LaunchedEffect(Unit) {
        try {
            isLoading = true
            categories = RetrofitClient.booksApi.getAllCategories()
        } catch (e: Exception) {
            errorMessage = e.message
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("API Test — Kategoriya tanlash") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (isLoading) {
                CircularProgressIndicator()
                return@Column
            }

            if (errorMessage != null) {
                Text("Xatolik: $errorMessage", color = MaterialTheme.colorScheme.error)
                return@Column
            }

            OutlinedTextField(
                value = inputId,
                onValueChange = { inputId = it },
                label = { Text("Kategoriya ID kiriting") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    scope.launch {
                        val id = inputId.text.toIntOrNull()
                        if (id == null || id !in 1..categories.size) {
                            selectedCategory = "Noto‘g‘ri ID!"
                        } else {
                            selectedCategory = categories[id - 1].type_name
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kategoriya topish")
            }

            Spacer(modifier = Modifier.height(20.dp))

            selectedCategory?.let {
                Text(
                    text = "Tanlangan kategoriya: $it",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (categories.isNotEmpty()) {
                Text(
                    text = "Jami kategoriyalar: ${categories.size}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
