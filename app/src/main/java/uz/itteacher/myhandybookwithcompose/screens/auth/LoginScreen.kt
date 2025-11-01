package uz.itteacher.myhandybookwithcompose.screens.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import uz.itteacher.myhandybookwithcompose.R
import uz.itteacher.myhandybookwithcompose.screens.ViewModel
import uz.itteacher.myhandybookwithcompose.api.HandyBookRepository
import uz.itteacher.myhandybookwithcompose.ui.theme.Montserrat

@Composable
fun LoginScreen(viewModel: ViewModel, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    HandyBookHelper.getAllBooks()

    LaunchedEffect(viewModel.isLoggedIn.value) {
        if (viewModel.isLoggedIn.value) {
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Hisobingizga kiring",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Montserrat,
            color = Color(0xFF0B1437),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Email",
                fontSize = 15.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0B1437),
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("abcdef@gmail.com", fontFamily = Montserrat,
                fontWeight = FontWeight.Normal) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Parol",
                fontSize = 15.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0B1437),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Parolingizni unutdingizmi?",
                color = Color(0xFF7BB9FF),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("password", fontFamily = Montserrat,
                fontWeight = FontWeight.Normal) },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) R.drawable.eye11 else R.drawable.eye2
                        ),
                        contentDescription = "Parolni ko'rsatish",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {
                Timber.d("Login clicked with username: $username")
                viewModel.login(username, password)
            },
            enabled = !viewModel.isLoading.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6F0FF))
        ) {
            if (viewModel.isLoading.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(text = "Kirish", color = Color(0xFF0B1437),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp)
            }
        }

        viewModel.errorMessage.value?.let { error ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = error, color = MaterialTheme.colors.error)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Hisobingiz yo‘qmi?", color = Color(0xFF0B1437), fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Ro‘yxatdan o‘tish",
                color = Color(0xFF7BB9FF),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                modifier = Modifier.clickable {navController.navigate("register") }
            )
        }

    }
}

object HandyBookHelper {
    private val repo = HandyBookRepository()

    fun getAllBooks() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val books = repo.getAllBooks()
                withContext(Dispatchers.Main) {
                    Log.d("HandyBookHelper", "Books loaded successfully: ${books.size}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("HandyBookHelper", "Error loading books: ${e.message}")
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            viewModel = ViewModel(), // Replace with your actual ViewModel preview if needed
            navController = rememberNavController()
        )
    }
}