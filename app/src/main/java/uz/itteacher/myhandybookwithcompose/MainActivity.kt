package uz.itteacher.myhandybookwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.itteacher.myhandybookwithcompose.Screens.BookViewModel
import uz.itteacher.myhandybookwithcompose.Screens.LoginScreen
import uz.itteacher.myhandybookwithcompose.Screens.MainScreen
import uz.itteacher.myhandybookwithcompose.Screens.ReviewScreen
import uz.itteacher.myhandybookwithcompose.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReviewScreen()
        }
    }
}



@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}

