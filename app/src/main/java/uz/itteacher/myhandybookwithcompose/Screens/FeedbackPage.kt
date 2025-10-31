package uz.itteacher.myhandybookwithcompose.Screens



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import uz.itteacher.myhandybookwithcompose.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.itteacher.myhandybookwithcompose.models.ReviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(reviewViewModel: ReviewViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val rating by reviewViewModel.rating
    val reviewText by reviewViewModel.reviewText
    var emojiResource by remember { mutableStateOf(R.drawable.happy) } // Default emoji

    // Update emoji based on rating
    LaunchedEffect(rating) {
        emojiResource = when {
            rating <= 1f -> R.drawable.sad // Sad emoji for 1 star
            rating in 2f..3f -> R.drawable.face // Neutral emoji for 2-3 stars
            else -> R.drawable.happy // Happy emoji for 4-5 stars
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Blue background for the top half of the screen
        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFF1A237E))) {
            // TopAppBar with Centered Title and Right Icon
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "O'z sharhingizni \nyozib qoldiring",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(start = 48.dp, end = 48.dp),
                            fontSize = 20.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle Back action */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // Add Right Top Icon (Settings icon as an example)
                    IconButton(onClick = { /* Handle Right Action */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // White background for the bottom half of the screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(32.dp)) // Space between the TopAppBar and content

            // Rating Stars
            Row(horizontalArrangement = Arrangement.Center) {
                for (i in 1..5) {
                    Star(
                        index = i,
                        filled = i <= rating,
                        onClick = { reviewViewModel.updateRating(i.toFloat()) }
                    )
                }
            }

            // Emoji change based on star selection
            Image(
                painter = painterResource(id = emojiResource), // Load emoji image resource
                contentDescription = "Emoji",
                modifier = Modifier.size(64.dp).align(Alignment.CenterHorizontally)
            )

            // Review Text
            Text(
                text = "Kitob haqida o’z fikringizni yozib qoldiring",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Review TextField
            TextField(
                value = reviewText,
                onValueChange = { reviewViewModel.updateReviewText(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.1f))
                    .padding(16.dp),
                textStyle = MaterialTheme.typography.bodyLarge,
                maxLines = 5,
                singleLine = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = { /* Handle review submission */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Jo’natish")
            }
        }
    }
}

@Composable
fun Star(index: Int, filled: Boolean, onClick: () -> Unit) {
    Icon(
        imageVector = if (filled) Icons.Filled.Star else Icons.Outlined.Star,
        contentDescription = "Star $index",
        modifier = Modifier
            .padding(4.dp)
            .size(36.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() }
                )
            },
        tint = if (filled) Color.Yellow else Color.Gray
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ReviewScreen()
}
