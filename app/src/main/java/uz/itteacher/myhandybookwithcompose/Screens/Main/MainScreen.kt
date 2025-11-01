package uz.itteacher.myhandybookwithcompose.Screens.Main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import uz.itteacher.myhandybookwithcompose.Screens.BookViewModel
import uz.itteacher.myhandybookwithcompose.models.Book

@Composable
fun MainScreen(viewModel: BookViewModel, navController: NavController) {

    val categories = viewModel.categories.value
    val mainBook = viewModel.mainBook.value
    val booksByCategory = viewModel.booksByCategory.value

    LaunchedEffect(Unit) {
        viewModel.fetchHomePageData()
    }

    Scaffold(
        topBar = { HomeTopBar() },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // ✅ Search bar
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

            // ✅ Category tabs row
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


            // ✅ Main Book Banner
            item {
                mainBook?.let { MainBookBanner(it) }
            }

            // ✅ Category Sections
            categories.forEach { category ->
                val books = booksByCategory[category.type_name] ?: emptyList()

                if (books.isNotEmpty()) {

                    item { Spacer(modifier = Modifier.height(20.dp)) }
                    item {
                        CategoryHeader(
                            title = category.type_name,
                            onSeeAllClick = {
                                viewModel.fetchBooksByCategory(category.type_name)
                                navController.navigate("categoryPage/${category.type_name}")
                            }
                        )
                    }

                    // LazyRow
                    item {
                        LazyRow(
                            modifier = Modifier.padding(start = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(books) { book ->
                                BookCardHorizontal(book)
                                
                            }
                        }
                    }

                }
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(19.dp)
            .height(52.dp),
        placeholder = { Text("Kitob yoki muallifni qidiring...", color = Color.Gray.copy(0.8f)) },
        leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.Gray.copy(0.7f)) },
        trailingIcon = {
            if (query.isNotEmpty())
                IconButton({ onQueryChange("") }) {
                    Icon(Icons.Default.Close, null, tint = Color.Gray.copy(0.7f))
                }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFF5F5F5),
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
fun CategoryItem(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) Color(0xFF0A1448) // Dark navy
                else Color(0xFFF5F5F5)            // Light gray
            )
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(
            text = category,
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 14.sp
        )
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
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterStart)
        ) {
            Text(
                text = "${book.author}ning",
                color = Color.White.copy(alpha = 0.8f)
            )
            Text(
                text = book.name,
                color = Color.White,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Hozir o‘qish", color = Color(0xFF0A2342))
            }
        }

        Image(
            painter = rememberAsyncImagePainter(book.image),
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (-20).dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }

    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 8.dp
    ) {
        BottomNavigationItem(
            selected = selectedIndex == 0,
            onClick = { selectedIndex = 0 },
            icon = {
                Icon(
                    painterResource(R.drawable.home),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = if (selectedIndex == 0) Color(0xFF001A72) else Color(0xFFBFC1C8)
                )
            }
        )
        BottomNavigationItem(
            selected = selectedIndex == 1,
            onClick = { selectedIndex = 1 },
            icon = {
                Icon(
                    painterResource(R.drawable.loop),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = if (selectedIndex == 1) Color(0xFF001A72) else Color(0xFFBFC1C8)
                )
            }
        )
        BottomNavigationItem(
            selected = selectedIndex == 2,
            onClick = { selectedIndex = 2 },
            icon = {
                Icon(
                    painterResource(R.drawable.pero),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = if (selectedIndex == 2) Color(0xFF001A72) else Color(0xFFBFC1C8)
                )
            }
        )
        BottomNavigationItem(
            selected = selectedIndex == 3,
            onClick = { selectedIndex = 3 },
            icon = {
                Icon(
                    painterResource(R.drawable.saqlangan),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = if (selectedIndex == 3) Color(0xFF001A72) else Color(0xFFBFC1C8)
                )
            }
        )
        BottomNavigationItem(
            selected = selectedIndex == 4,
            onClick = { selectedIndex = 4 },
            icon = {
                Icon(
                    painterResource(R.drawable.world),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = if (selectedIndex == 4) Color(0xFF001A72) else Color(0xFFBFC1C8)
                )
            }
        )
    }
}



@Composable
fun HomeTopBar() {
    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.settings),
            contentDescription = "Menu",
            tint = Color(0xFF0A2342),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Bosh Sahifa",
            color = Color(0xFF0A2342),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.account),
            contentDescription = "Profile",
            tint = Color(0xFF0A2342),
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun BookCardHorizontal(book: Book) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(140.dp)
            .height(210.dp)
            .clickable { },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(book.image),
                contentDescription = null,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(6.dp))
            Text(book.name, maxLines = 1)
            Text(book.author ?: "", color = Color.Gray, fontSize = 12.sp)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFF4DAFFF),
                    modifier = Modifier.size(12.dp)
                )
                Text(text = book.reyting.toString(), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CategoryHeader(
    title: String,
    onSeeAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.h6.copy(
                color = Color(0xFF0A2342),
                fontSize = 20.sp
            ),
            fontWeight = FontWeight.Bold

        )

        Text(
            text = "Barchasini ko’rish",
            color = Color(0x803A7BFF),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.clickable { onSeeAllClick() }
        )
    }
}
