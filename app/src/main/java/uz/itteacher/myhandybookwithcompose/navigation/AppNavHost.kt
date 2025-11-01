package uz.itteacher.myhandybookwithcompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.itteacher.myhandybookwithcompose.Screens.BookViewModel
import uz.itteacher.myhandybookwithcompose.Screens.Auth.LoginScreen
import uz.itteacher.myhandybookwithcompose.Screens.Auth.RegisterScreen
import uz.itteacher.myhandybookwithcompose.Screens.MainScreen
import uz.itteacher.myhandybookwithcompose.Screens.Profile.OldBooksScreen
import uz.itteacher.myhandybookwithcompose.Screens.Profile.ProfileScreen
import uz.itteacher.myhandybookwithcompose.Screens.Profile.ReadingBooksScreen
import uz.itteacher.myhandybookwithcompose.Screens.Profile.SavedBooksScreen
import uz.itteacher.myhandybookwithcompose.Screens.Profile.SettingsScreen
import uz.itteacher.myhandybookwithcompose.network.RetrofitClient.api

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: BookViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {
        composable("main") {
            MainScreen(viewModel = viewModel)
        }


//        Auth Section
        composable("login") { LoginScreen(viewModel = viewModel, navController = navController) }
        composable("register") { RegisterScreen(viewModel = viewModel, navController = navController) }

//        Profile Section
        composable("profile") { ProfileScreen(navController, api) }
        composable("settings") { SettingsScreen(navController) }
        composable("reading") { ReadingBooksScreen(navController, api) }
        composable("read") { OldBooksScreen(navController, api) }
        composable("saved") { SavedBooksScreen(navController, api) }
    }
}