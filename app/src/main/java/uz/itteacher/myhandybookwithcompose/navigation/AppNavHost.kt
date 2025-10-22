package uz.itteacher.myhandybookwithcompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.itteacher.myhandybookwithcompose.Screens.BookViewModel
import uz.itteacher.myhandybookwithcompose.Screens.LoginScreen
import uz.itteacher.myhandybookwithcompose.Screens.MainScreen

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