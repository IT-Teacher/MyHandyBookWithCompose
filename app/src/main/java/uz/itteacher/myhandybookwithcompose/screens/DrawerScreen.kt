package uz.itteacher.myhandybookwithcompose.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScreen() {
    var drawerOpen by remember { mutableStateOf(false) }

    // Track selected item name
    var selectedItem by remember { mutableStateOf("Bosh sahifa") }

    Box(modifier = Modifier.fillMaxSize()) {

        // Main content
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Main Screen") },
                    navigationIcon = {
                        IconButton(onClick = { drawerOpen = !drawerOpen }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF0D1C54),
                        titleContentColor = Color.White
                    )
                )
            }
        ) { inner ->
            Box(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Main content goes here", color = Color(0xFF0D1C54))
            }
        }

        // Drawer background overlay
        if (drawerOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { drawerOpen = false }
            )
        }

        // Animated Drawer Panel (half width)
        val drawerWidth = 300.dp
        val offsetX by animateDpAsState(targetValue = if (drawerOpen) 0.dp else -drawerWidth)

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(drawerWidth)
                .offset(x = offsetX)
                .shadow(8.dp)
                .background(Color.White)
        ) {
            DrawerContent(
                selectedItem = selectedItem,
                onItemSelected = { selected ->
                    selectedItem = selected
                    drawerOpen = false
                }
            )
        }
    }
}



@Composable
fun DrawerContent(
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0D1C54))
                .padding(vertical = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Azizov Ali", color = Color.White, fontSize = 18.sp)
                Text(
                    text = "abcd@gmail.com",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        DrawerButton(Icons.Default.Home, "Bosh sahifa", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Search, "Qidiruv", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Edit, "Maqolalar", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.CheckCircle, "Saqlangan kitoblar", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Person, "Tilni o‘zgartiring", selectedItem, onItemSelected)

        Divider(modifier = Modifier.padding(vertical = 10.dp))

        DrawerButton(Icons.Default.Send, "Telegram Kanalimiz", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Email, "Instagram do‘konimiz", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Share, "Ulashish", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Star, "Bizga baho bering", selectedItem, onItemSelected)

        Divider(modifier = Modifier.padding(vertical = 10.dp))

        DrawerButton(Icons.Default.ExitToApp, "Hisobdan chiqish", selectedItem, onItemSelected)
    }
}

@Composable
fun DrawerButton(
    icon: ImageVector,
    text: String,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    val isSelected = selectedItem == text
    val backgroundColor = if (isSelected) Color(0xFF0D1C54) else Color.Transparent
    val textColor = if (isSelected) Color.White else Color(0xFF0D1C54)
    val iconTint = if (isSelected) Color.White else Color(0xFF0D1C54)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .background(backgroundColor, RoundedCornerShape(20.dp))
            .clickable { onItemSelected(text) }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconTint)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, color = textColor, fontSize = 16.sp)
    }
}
