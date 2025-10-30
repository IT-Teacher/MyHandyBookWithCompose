package uz.itteacher.myhandybookwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import uz.itteacher.myhandybookwithcompose.data.ApiTestScreen
import uz.itteacher.myhandybookwithcompose.screens.BookDetailScreen
import uz.itteacher.myhandybookwithcompose.ui.theme.MyHandyBookWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyHandyBookWithComposeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    BookDetailScreen(bookId = 1) /* TODO: ID ni tanlash */
                }
            }

        }
    }
}
