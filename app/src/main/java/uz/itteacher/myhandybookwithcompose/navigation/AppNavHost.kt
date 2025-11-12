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
import uz.ictschool.search.screens.SearchScreen
import uz.itteacher.myhandybookwithcompose.network.RetrofitClient.api
import uz.itteacher.myhandybookwithcompose.screens.InfoScreen
import uz.itteacher.myhandybookwithcompose.screens.ViewModel
import uz.itteacher.myhandybookwithcompose.screens.auth.LoginScreen
import uz.itteacher.myhandybookwithcompose.screens.auth.RegisterScreen
import uz.itteacher.myhandybookwithcompose.screens.main.CategoryPage
import uz.itteacher.myhandybookwithcompose.screens.main.MainScreen
import uz.itteacher.myhandybookwithcompose.screens.openBook.OpenBookScreen
import uz.itteacher.myhandybookwithcompose.screens.openBook.PdfReaderScreen
import uz.itteacher.myhandybookwithcompose.screens.openBook.PdfViewModel
import uz.itteacher.myhandybookwithcompose.screens.profile.OldBooksScreen
import uz.itteacher.myhandybookwithcompose.screens.profile.ProfileScreen
import uz.itteacher.myhandybookwithcompose.screens.profile.ReadingBooksScreen
import uz.itteacher.myhandybookwithcompose.screens.profile.SavedBooksScreen
import uz.itteacher.myhandybookwithcompose.screens.profile.SettingsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ViewModel = viewModel()

    NavHost(navController = navController, startDestination = "main") {

        composable("main") {
            MainScreen(viewModel = viewModel, navController = navController)
        }

        composable("infoScreen") {
            val vm = viewModel<PdfViewModel>()
            InfoScreen(navController = navController, viewModel = viewModel, pdfViewModel = vm)
        }


//        Rama Section

        composable("categoryPage/{cat}") { backStackEntry ->
            val cat = backStackEntry.arguments?.getString("cat")
            CategoryPage(viewModel, cat, navController)
        }

        composable("open_book") {
            val vm = viewModel<PdfViewModel>()
            OpenBookScreen(vm) { url ->
                navController.navigate("pdf_viewer?url=$url")
            }
        }

        composable(
            "pdf_viewer?url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("open_book")
            }
            val vm: PdfViewModel = viewModel(parentEntry)
            val url = backStackEntry.arguments?.getString("url")!!
            PdfReaderScreen(vm, pdfUrl = url) { error ->
                Log.e("PdfError", error)
            }
        }



        // Auth
        composable("login") { LoginScreen(viewModel = viewModel, navController = navController) }
        composable("register") { RegisterScreen(viewModel = viewModel, navController = navController) }
        composable("search") { SearchScreen(viewModel = viewModel, navController = navController) }



        // Profile
        composable("profile") { ProfileScreen(navController, api) }
        composable("settings") { SettingsScreen(navController) }
        composable("reading") { ReadingBooksScreen(navController, api) }
        composable("read") { OldBooksScreen(navController, api) }
        composable("saved") { SavedBooksScreen(navController, api) }
    }
}