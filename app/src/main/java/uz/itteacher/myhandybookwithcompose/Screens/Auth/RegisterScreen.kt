package uz.itteacher.myhandybookwithcompose.Screens.Auth

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uz.itteacher.myhandybookwithcompose.Screens.BookViewModel
import uz.itteacher.myhandybookwithcompose.ui.theme.Montserrat
import uz.itteacher.myhandybookwithcompose.models.RegisterRequest

@Composable
fun RegisterScreen(viewModel: BookViewModel, navController: NavController) {
    var ism by remember { mutableStateOf("") }
    var familiya by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.isLoggedIn.value) {
        if (viewModel.isLoggedIn.value) {
            navController.navigate("main") { popUpTo("register") { inclusive = true } }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate("login") }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Orqaga",
                    tint = Color(0xFF0B1437)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Ro'yxatdan o'tish",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Montserrat,
                color = Color(0xFF0B1437)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Ism
        Text(
            text = "Ism",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Montserrat,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = ism,
            onValueChange = { ism = it },
            placeholder = { Text("Ismingizni kiriting", fontFamily = Montserrat) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF0B1437),
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Familiya
        Text(
            text = "Familiya",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Montserrat,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = familiya,
            onValueChange = { familiya = it },
            placeholder = { Text("Familiyangizni kiriting", fontFamily = Montserrat) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF0B1437),
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Email
        Text(
            text = "Email",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Montserrat,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("email@example.com", fontFamily = Montserrat) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF0B1437),
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Parol
        Text(
            text = "Parol",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Montserrat,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Parolni kiriting", fontFamily = Montserrat) },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Parolni ko'rsatish",
                        tint = Color(0xFF666666)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF0B1437),
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Parolni takrorlang
        Text(
            text = "Parolni takrorlang",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Montserrat,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = { Text("Parolni qayta kiriting", fontFamily = Montserrat) },
            singleLine = true,
            visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { confirmVisible = !confirmVisible }) {
                    Icon(
                        imageVector = if (confirmVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Parolni ko'rsatish",
                        tint = Color(0xFF666666)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF0B1437),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                errorBorderColor = Color.Red
            ),
            isError = password.isNotEmpty() && confirmPassword.isNotEmpty() && password != confirmPassword
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (password == confirmPassword) {
                    viewModel.register(RegisterRequest(email, "$ism $familiya", email, password))
                } else {
                    viewModel.errorMessage.value = "Parollar mos emas"
                }
            },
            enabled = !viewModel.isLoading.value &&
                    ism.isNotBlank() && familiya.isNotBlank() &&
                    email.isNotBlank() && password.isNotBlank() &&
                    confirmPassword.isNotBlank() && password == confirmPassword,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6F0FF))
        ) {
            if (viewModel.isLoading.value) {
                CircularProgressIndicator(
                    color = Color(0xFF0B1437),
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    text = "Ro'yxatdan o'tish",
                    color = Color(0xFF0B1437),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            }
        }

        viewModel.errorMessage.value?.let { error ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = error, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hisobingiz bormi?",
                color = Color(0xFF0B1437),
                fontFamily = Montserrat,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Kirish",
                color = Color(0xFF7BB9FF),
                fontFamily = Montserrat,
                fontSize = 13.sp,
                modifier = Modifier.clickable { navController.navigate("login") }
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(
            viewModel = BookViewModel(),
            navController = rememberNavController()
        )
    }
}