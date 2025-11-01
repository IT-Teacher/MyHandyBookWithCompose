package uz.itteacher.myhandybookwithcompose.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("Ali") }
    var surname by remember { mutableStateOf("Azizov") }
    var email by remember { mutableStateOf("abcdef@gmail.com") }
    var password by remember { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(true) }

    val primaryColor = Color(0xFF1E88E5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp, top = 50.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
            Text(
                "Sozlamalar",
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A)
            )
            Spacer(Modifier.width(56.dp))
        }

        Spacer(Modifier.height(48.dp))

        // Ism
        Text("Ism", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color(0xFF444444))
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedLabelColor = primaryColor
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
        )

        Spacer(Modifier.height(32.dp))

        // Familiya
        Text("Familiya", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color(0xFF444444))
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
        )

        Spacer(Modifier.height(32.dp))

        // Email
        Text("Email", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color(0xFF444444))
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
        )

        Spacer(Modifier.height(32.dp))

        // Parol
        Text("Parol", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color(0xFF444444))
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = Color(0xFFE0E0E0)
            ),
            trailingIcon = {
                if (password.isNotEmpty() && isPasswordValid) {
                    Icon(Icons.Default.Check, null, tint = Color(0xFF4CAF50), modifier = Modifier.size(24.dp))
                }
            },
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
        )

        if (password.isNotEmpty() && isPasswordValid) {
            Spacer(Modifier.height(12.dp))
            Text(
                "Parolingiz qabul qilindi",
                color = Color(0xFF4CAF50),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.height(48.dp))

        // Save Button
        Button(
            onClick = { /* Save logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
        ) {
            Text(
                "O‘zgarishlarni saqlash",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}