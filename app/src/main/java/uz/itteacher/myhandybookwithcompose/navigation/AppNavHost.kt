package uz.itteacher.myhandybookwithcompose.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uz.itteacher.myhandybookwithcompose.Screens.BookViewModel
import uz.itteacher.myhandybookwithcompose.Screens.LoginScreen
import uz.itteacher.myhandybookwithcompose.Screens.MainScreen
import uz.itteacher.myhandybookwithcompose.Screens.OpenBook.OpenBookScreen
import uz.itteacher.myhandybookwithcompose.Screens.OpenBook.PdfReaderScreen
import uz.itteacher.myhandybookwithcompose.Screens.Profile.OldBooksScreen
import uz.itteacher.myhandybookwithcompose.Screens.Profile.ProfileScreen
import uz.itteacher.myhandybookwithcompose.Screens.Profile.ReadingBooksScreen
import uz.itteacher.myhandybookwithcompose.Screens.Profile.SavedBooksScreen
import uz.itteacher.myhandybookwithcompose.Screens.Profile.SettingsScreen
import uz.itteacher.myhandybookwithcompose.network.RetrofitClient.api
import uz.itteacher.myhandybookwithcompose.screens.openbook.PdfViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: BookViewModel = viewModel()

    NavHost(navController = navController, startDestination = "open_book") {
        composable("login") {
            LoginScreen(viewModel = viewModel, navController = navController)
        }
        composable("main") {
            MainScreen(viewModel = viewModel)
        }


        composable("open_book") {
            val vm: PdfViewModel = viewModel() // Create once
            OpenBookScreen(vm) { url ->
                navController.navigate("pdf_viewer?url=$url")
            }
        }

        composable(
            "pdf_viewer?url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            // ✅ Reuse the SAME ViewModel
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("open_book")
            }
            val vm: PdfViewModel = viewModel(parentEntry)
            val url = backStackEntry.arguments?.getString("url")!!
            PdfReaderScreen(vm, pdfUrl = url) { Log.e("PdfError", it) }
        }


//        Profile Section

// AppNavHost.kt — update routes
        composable("profile") { ProfileScreen(navController, api) }
        composable("settings") { SettingsScreen(navController) }
        composable("reading") { ReadingBooksScreen(navController, api) }
        composable("read") { OldBooksScreen(navController, api) }
        composable("saved") { SavedBooksScreen(navController, api) }
    }
}