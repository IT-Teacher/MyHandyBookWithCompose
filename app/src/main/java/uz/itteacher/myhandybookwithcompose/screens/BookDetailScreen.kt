package uz.itteacher.myhandybookwithcompose.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.itteacher.myhandybookwithcompose.screens.tabs.CommentsTab
import uz.itteacher.myhandybookwithcompose.screens.tabs.DescriptionTab
import uz.itteacher.myhandybookwithcompose.screens.tabs.QuotesTab
import uz.itteacher.myhandybookwithcompose.ui.theme.AccentCyan
import uz.itteacher.myhandybookwithcompose.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(bookId: Int) {
    var selectedTab by remember { mutableStateOf("Tavsifi") }
    val tabs = listOf("Tavsifi", "Sharhlar", "Iqtiboslar")

    Scaffold(topBar = {
        TopAppBar(title = {
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Batafsil",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryBlue,
                    textAlign = TextAlign.Center
                )
            }
        }, navigationIcon = {
            IconButton(onClick = { /* TODO: back */ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(45.dp),
                    tint = PrimaryBlue
                )
            }
        }, actions = {
            IconButton(onClick = { /* TODO: Save */ }) {
                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = "Save",
                    modifier = Modifier.size(45.dp),
                    tint = PrimaryBlue
                )
            }
        }, modifier = Modifier.padding(horizontal = 8.dp)
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                tabs.forEach { tab ->
                    TextButton(onClick = { selectedTab = tab }) {
                        Text(
                            text = tab,
                            color = if (selectedTab == tab) AccentCyan else PrimaryBlue,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Divider()

            Crossfade(targetState = selectedTab, label = "TabSwitch") { tab ->
                when (tab) {
                    "Tavsifi" -> DescriptionTab(bookId = bookId)
                    "Sharhlar" -> CommentsTab(bookId = bookId)
                    "Iqtiboslar" -> QuotesTab()
                }
            }
        }
    }
}