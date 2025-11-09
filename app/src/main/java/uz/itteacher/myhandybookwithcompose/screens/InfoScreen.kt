package uz.itteacher.myhandybookwithcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uz.itteacher.myhandybookwithcompose.R
import uz.itteacher.myhandybookwithcompose.screens.ViewModel
import uz.itteacher.myhandybookwithcompose.models.Book
import kotlin.math.min

@Composable
fun InfoScreen(navController: NavHostController, viewModel: ViewModel) {
    val currentBook = viewModel.selectedBook ?: run {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text("Kitob topilmadi", color = Color.Red)
        }
        return
    }

    var bookType by remember { mutableStateOf("E-Book") }
    var bookInfo by remember { mutableStateOf("Tavsif") }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F1D4C))
                    .padding(horizontal = 8.dp)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                }
                Text("Batafsil", fontSize = 22.sp, color = Color.White)
                IconButton(onClick = { /* TODO: Save book */ }) {
                    Image(
                        painter = painterResource(R.drawable.saved),
                        contentDescription = "Favourite",
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
        },
        floatingActionButton = {
            if (bookInfo == "Tavsif") {
                if (bookType == "E-Book") {
                    Card(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        onClick = { /* TODO: Open PDF */ },
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Row(Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .weight(8f)
                                    .background(Color(0xFF0F1D4C))
                                    .height(54.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("O'qishni davom ettirish", color = Color(0xCCB8E8F2), fontSize = 18.sp)
                            }
                            Row(
                                modifier = Modifier
                                    .weight(2f)
                                    .background(Color(0xCCB8E8F2))
                                    .height(54.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("87%", color = Color(0xFF0F1D4C), fontSize = 18.sp)
                            }
                        }
                    }
                } else {
                    Card(Modifier.fillMaxWidth()) {
                        Box(Modifier.height(54.dp), Alignment.Center) {
                            Text("Audio Player Here", color = Color.Gray)
                        }
                    }
                }
            } else if (bookInfo == "Sharh") {
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("${currentBook.reyting}.0", color = Color(0xFF0F1D4C), fontSize = 24.sp)
                            Spacer(Modifier.width(4.dp))
                            Text("52 ta sharhlar", color = Color.LightGray, fontSize = 6.sp, modifier = Modifier.offset(y = (-4).dp))
                        }
                        Row {
                            currentBook.reyting?.let {
                                for (i in 1..min(it, 5)) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        tint = Color(0xFF0F1D4C),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            currentBook.reyting?.let {
                                for (i in 1..(5 - min(it, 5))) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        tint = Color.LightGray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                    Button(
                        onClick = { /* Navigate to comments */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.Black),
                        modifier = Modifier.height(48.dp),
                        shape = RoundedCornerShape(9.dp)
                    ) {
                        Text("O'z sharhingizni yozib qoldiring", fontSize = 10.sp)
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = if (bookInfo != "Iqtibos") 74.dp else 0.dp)
                ) {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((LocalConfiguration.current.screenHeightDp * 0.60).dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .background(Color.Transparent),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.48f)
                                        .background(Color(0xFF0F1D4C)),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(Modifier.height(32.dp))
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = Color(0x4DB8E8F2)),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f)
                                                .padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            Button(
                                                onClick = { bookType = "E-Book" },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = if (bookType == "E-Book") Color(0xFF0F1D4C) else Color.Transparent
                                                ),
                                                shape = RoundedCornerShape(8.dp),
                                                modifier = Modifier.fillMaxWidth(0.48f)
                                            ) { Text("E-Kitob") }
                                            Button(
                                                onClick = { bookType = "AudioBook" },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = if (bookType == "AudioBook") Color(0xFF0F1D4C) else Color.Transparent
                                                ),
                                                shape = RoundedCornerShape(8.dp),
                                                modifier = Modifier.fillMaxWidth(0.85f)
                                            ) { Text("Audio Kitob") }
                                        }
                                    }
                                }
                                Spacer(Modifier.height(100.dp))
                                Text(currentBook.name, fontSize = 22.sp, fontWeight = FontWeight.W600, color = Color(0xFF0F1D4C))
                                currentBook.author?.let { Text(it, color = Color.LightGray) }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, "Rating", tint = Color.Blue)
                                    Text("${currentBook.reyting}.0", color = Color(0xFF0F1D4C))
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(0.95f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    TextButton(
                                        onClick = { bookInfo = "Tavsif" },
                                        colors = ButtonDefaults.buttonColors(
                                            contentColor = if (bookInfo == "Tavsif") Color(0xCCB8E8F2) else Color(0xFF0F1D4C),
                                            containerColor = Color.Transparent
                                        )
                                    ) { Text("Tavsifi", fontSize = 18.sp) }
                                    TextButton(
                                        onClick = { bookInfo = "Sharh" },
                                        colors = ButtonDefaults.buttonColors(
                                            contentColor = if (bookInfo == "Sharh") Color(0xCCB8E8F2) else Color(0xFF0F1D4C),
                                            containerColor = Color.Transparent
                                        )
                                    ) { Text("Sharhlar", fontSize = 18.sp) }
                                    TextButton(
                                        onClick = { bookInfo = "Iqtibos" },
                                        colors = ButtonDefaults.buttonColors(
                                            contentColor = if (bookInfo == "Iqtibos") Color(0xCCB8E8F2) else Color(0xFF0F1D4C),
                                            containerColor = Color.Transparent
                                        )
                                    ) { Text("Iqtiboslar", fontSize = 18.sp) }
                                }
                            }
                            if (bookType == "E-Book") {
                                AsyncImage(
                                    model = currentBook.image ?: R.drawable.kitob,
                                    contentDescription = "Book Cover",
                                    modifier = Modifier
                                        .size((LocalConfiguration.current.screenHeightDp / 3.75).dp)
                                        .offset(y = (-24).dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop,
                                    placeholder = painterResource(R.drawable.kitob),
                                    error = painterResource(R.drawable.kitob)
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size((LocalConfiguration.current.screenHeightDp / 4.5).dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(modifier = Modifier.background(Color.White, CircleShape).size(24.dp))
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0x80FFFFFF), CircleShape)
                                            .size(48.dp)
                                            .border(0.5.dp, Color.White, CircleShape)
                                    )
                                }
                            }
                        }
                    }

                    if (bookInfo == "Tavsif") {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(0.75f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.outline_insert_drive_file_24),
                                        contentDescription = "Pages",
                                        modifier = Modifier.size(20.dp),
                                        colorFilter = ColorFilter.tint(Color.LightGray)
                                    )
                                    Text("${currentBook.count_page} bet", color = Color.LightGray, fontSize = 12.sp)
                                    Spacer(Modifier.width(16.dp))
                                    Image(
                                        painter = painterResource(R.drawable.book),
                                        contentDescription = "Year",
                                        modifier = Modifier.size(20.dp),
                                        colorFilter = ColorFilter.tint(Color.LightGray)
                                    )
                                    Text("${currentBook.year} yil", color = Color.LightGray, fontSize = 12.sp)
                                    Spacer(Modifier.width(16.dp))
                                    Image(
                                        painter = painterResource(R.drawable.outline_language_24),
                                        contentDescription = "Language",
                                        modifier = Modifier.size(20.dp),
                                        colorFilter = ColorFilter.tint(Color.LightGray)
                                    )
                                    currentBook.lang?.let {
                                        Text(it, color = Color.LightGray, fontSize = 12.sp, overflow = TextOverflow.Ellipsis, maxLines = 1)
                                    }
                                }
                                Spacer(Modifier.height(24.dp))
                                currentBook.description?.let {
                                    Text(it, color = Color(0xFF0F1D4C), overflow = TextOverflow.Ellipsis)
                                }
                                Spacer(Modifier.height(24.dp))
                            }
                        }
                    } else if (bookInfo == "Sharh") {
                        items(7) { Spacer(Modifier.height(16.dp)); CommentCard() }
                    } else {
                        items(23) { Spacer(Modifier.height(16.dp)); QuoteCard() }
                    }
                }

                if (bookInfo == "Tavsif") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-70).dp)
                            .height(150.dp)
                            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.White)))
                    )
                }
            }
        }
    }
}

@Composable
fun CommentCard() {
    Card(modifier = Modifier.padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween) {
                Row {
                    Icon(Icons.Default.AccountCircle, "comment", modifier = Modifier.size(56.dp), tint = Color(0xFF0F1D4C))
                }
                Spacer(Modifier.width(12.dp))
                Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
                    Spacer(Modifier.height(5.dp))
                    Text("User123", color = Color.Blue, fontSize = 16.sp)
                    Spacer(Modifier.height(5.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, "Rating", tint = Color.Blue, modifier = Modifier.size(20.dp))
                        Text("5.0", color = Color.Blue, fontSize = 14.sp)
                    }
                }
            }
            Text("22-may 2022-yil", color = Color.LightGray)
            Spacer(Modifier.height(8.dp))
            Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit...", color = Color.Blue)
        }
    }
}

@Composable
fun QuoteCard() {
    Card(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit...", fontSize = 10.sp, color = Color.Blue)
                Spacer(Modifier.height(8.dp))
                Text("123-sahifa", color = Color.LightGray, fontSize = 10.sp)
            }
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = { }) {
                Image(
                    painter = painterResource(R.drawable.baseline_bookmark_24),
                    contentDescription = "Bookmark",
                    modifier = Modifier.size(36.dp),
                    colorFilter = ColorFilter.tint(Color.Blue)
                )
            }
        }
    }
}