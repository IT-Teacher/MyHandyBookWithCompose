package uz.itteacher.myhandybookwithcompose.screens.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import uz.itteacher.myhandybookwithcompose.R
import uz.itteacher.myhandybookwithcompose.screens.ViewModel
import uz.itteacher.myhandybookwithcompose.models.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: ViewModel, navController: NavController) {
    val categories = viewModel.categories.value
    val mainBook = viewModel.mainBook.value
    val booksByCategory = viewModel.booksByCategory.value
    var drawerOpen by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Bosh sahifa") }

    LaunchedEffect(Unit) { viewModel.fetchHomePageData() }

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Bosh Sahifa",
                            color = Color(0xFF0A2342),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { drawerOpen = true }) {
                            Icon(Icons.Default.Menu, null, tint = Color(0xFF0A2342))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        navigationIconContentColor = Color(0xFF0A2342)
                    ),
                    actions = {
                        Icon(painterResource(R.drawable.account), null, tint = Color(0xFF0A2342), modifier = Modifier.size(32.dp))
                    }
                )
            },
            bottomBar = { BottomNavBar(navController) }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(top = 45.dp)
            ) {
                item {
                    var search by remember { mutableStateOf("") }
                    SearchBar(
                        query = search,
                        onQueryChange = { search = it },
                        onSearch = {
                            viewModel.searchBooksByName(search)
                            navController.navigate("categoryPage/search")
                        }
                    )
                }
                item {
                    var selectedCategory by remember { mutableStateOf<String?>(null) }
                    val allCategories = listOf("Barchasi") + categories.map { it.type_name }
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(allCategories) { category ->
                            val isSelected = selectedCategory == category || (selectedCategory == null && category == "Barchasi")
                            CategoryItem(
                                category = category,
                                isSelected = isSelected,
                                onClick = {
                                    selectedCategory = category
                                    val actualCategory = if (category == "Barchasi") null else category
                                    viewModel.fetchBooksByCategory(actualCategory ?: "")
                                    navController.navigate("categoryPage/${actualCategory ?: "Barchasi"}")
                                }
                            )
                        }
                    }
                }
                item { mainBook?.let { MainBookBanner(it) } }
                categories.forEach { category ->
                    val books = booksByCategory[category.type_name] ?: emptyList()
                    if (books.isNotEmpty()) {
                        item { Spacer(Modifier.height(20.dp)) }
                        item {
                            CategoryHeader(
                                title = category.type_name,
                                onSeeAllClick = {
                                    viewModel.fetchBooksByCategory(category.type_name)
                                    navController.navigate("categoryPage/${category.type_name}")
                                }
                            )
                        }
                        item {
                            LazyRow(
                                modifier = Modifier.padding(start = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(books) { book -> BookCardHorizontal(book) }
                            }
                        }
                    }
                }
                item { Spacer(Modifier.height(20.dp)) }
            }
        }

        if (drawerOpen) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.4f))
                    .clickable { drawerOpen = false }
            )
        }

        val drawerWidth = 300.dp
        val offsetX by animateDpAsState(if (drawerOpen) 0.dp else -drawerWidth)
        Box(
            Modifier
                .fillMaxHeight()
                .width(drawerWidth)
                .offset(x = offsetX)
                .shadow(8.dp)
                .background(Color.White)
        ) {
            DrawerContent(selectedItem = selectedItem) {
                selectedItem = it
                drawerOpen = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, onSearch: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth().padding(19.dp).height(52.dp),
        placeholder = { Text("Kitob yoki muallifni qidiring...", color = Color.Gray.copy(0.8f)) },
        leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.Gray.copy(0.7f)) },
        trailingIcon = {
            if (query.isNotEmpty()) IconButton({ onQueryChange("") }) {
                Icon(Icons.Default.Close, null, tint = Color.Gray.copy(0.7f))
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedContainerColor = Color(0xFFF5F5F5),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions {
            onSearch(query)
            focusManager.clearFocus()
        }
    )
}

@Composable
fun CategoryItem(category: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color(0xFF0A1448) else Color(0xFFF5F5F5))
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(text = category, color = if (isSelected) Color.White else Color.Gray, fontSize = 14.sp)
    }
}

@Composable
fun MainBookBanner(book: Book) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF0A2342))
    ) {
        Column(modifier = Modifier.padding(16.dp).align(Alignment.CenterStart)) {
            Text("${book.author}ning", color = Color.White.copy(0.8f), fontSize = 16.sp)
            Text(book.name, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color.White), shape = RoundedCornerShape(8.dp)) {
                Text("Hozir o‘qish", color = Color(0xFF0A2342))
            }
        }
        Image(
            painter = rememberAsyncImagePainter(book.image),
            contentDescription = null,
            modifier = Modifier.size(160.dp).align(Alignment.CenterEnd).offset(x = (-20).dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
        NavigationBarItem(selected = selectedIndex == 0, onClick = { selectedIndex = 0 }, icon = {
            Icon(painterResource(R.drawable.home), null, modifier = Modifier.size(25.dp), tint = if (selectedIndex == 0) Color(0xFF001A72) else Color(0xFFBFC1C8))
        })
        NavigationBarItem(selected = selectedIndex == 1, onClick = { selectedIndex = 1 }, icon = {
            Icon(painterResource(R.drawable.loop), null, modifier = Modifier.size(25.dp), tint = if (selectedIndex == 1) Color(0xFF001A72) else Color(0xFFBFC1C8))
        })
        NavigationBarItem(selected = selectedIndex == 2, onClick = { selectedIndex = 2 }, icon = {
            Icon(painterResource(R.drawable.pero), null, modifier = Modifier.size(25.dp), tint = if (selectedIndex == 2) Color(0xFF001A72) else Color(0xFFBFC1C8))
        })
        NavigationBarItem(selected = selectedIndex == 3, onClick = { selectedIndex = 3 }, icon = {
            Icon(painterResource(R.drawable.saqlangan), null, modifier = Modifier.size(25.dp), tint = if (selectedIndex == 3) Color(0xFF001A72) else Color(0xFFBFC1C8))
        })
        NavigationBarItem(selected = selectedIndex == 4, onClick = { selectedIndex = 4 }, icon = {
            Icon(painterResource(R.drawable.world), null, modifier = Modifier.size(25.dp), tint = if (selectedIndex == 4) Color(0xFF001A72) else Color(0xFFBFC1C8))
        })
    }
}

@Composable
fun BookCardHorizontal(book: Book) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.width(140.dp).height(210.dp).clickable { },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(book.image),
                contentDescription = null,
                modifier = Modifier.height(140.dp).fillMaxWidth().clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(6.dp))
            Text(book.name, maxLines = 1, fontSize = 14.sp)
            Text(book.author ?: "", color = Color.Gray, fontSize = 12.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, null, tint = Color(0xFF4DAFFF), modifier = Modifier.size(12.dp))
                Text(book.reyting.toString(), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CategoryHeader(title: String, onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 21.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            color = Color(0xFF0A2342),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Barchasini ko’rish",
            color = Color(0x803A7BFF),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.clickable { onSeeAllClick() }
        )
    }
}

@Composable
fun DrawerContent(selectedItem: String, onItemSelected: (String) -> Unit) {
    Column(Modifier.fillMaxSize().background(Color(0xFFF6F6F6))) {
        Box(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF0D1C54)).padding(vertical = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(70.dp).clip(CircleShape).background(Color.White.copy(0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.size(40.dp))
                }
                Spacer(Modifier.height(10.dp))
                Text("Azizov Ali", color = Color.White, fontSize = 18.sp)
                Text("abcd@gmail.com", color = Color.White.copy(0.8f), fontSize = 14.sp)
            }
        }
        Spacer(Modifier.height(10.dp))
        DrawerButton(Icons.Default.Home, "Bosh sahifa", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Search, "Qidiruv", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Edit, "Maqolalar", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.CheckCircle, "Saqlangan kitoblar", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Person, "Tilni o‘zgartiring", selectedItem, onItemSelected)
        Divider(Modifier.padding(vertical = 10.dp))
        DrawerButton(Icons.Default.Send, "Telegram Kanalimiz", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Email, "Instagram do‘konimiz", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Share, "Ulashish", selectedItem, onItemSelected)
        DrawerButton(Icons.Default.Star, "Bizga baho bering", selectedItem, onItemSelected)
        Divider(Modifier.padding(vertical = 10.dp))
        DrawerButton(Icons.AutoMirrored.Filled.ExitToApp, "Hisobdan chiqish", selectedItem, onItemSelected)
    }
}

@Composable
fun DrawerButton(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, selectedItem: String, onItemSelected: (String) -> Unit) {
    val isSelected = selectedItem == text
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .background(if (isSelected) Color(0xFF0D1C54) else Color.Transparent, RoundedCornerShape(20.dp))
            .clickable { onItemSelected(text) }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = if (isSelected) Color.White else Color(0xFF0D1C54))
        Spacer(Modifier.width(12.dp))
        Text(text, color = if (isSelected) Color.White else Color(0xFF0D1C54), fontSize = 16.sp)
    }
}