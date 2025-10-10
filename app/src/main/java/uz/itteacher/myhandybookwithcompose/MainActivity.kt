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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                AppNavigation()
            }
        }
    }
}



@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: BookViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(viewModel = viewModel, navController = navController)
        }
        composable("main") {
            MainScreen(viewModel = viewModel)
        }
    }
}