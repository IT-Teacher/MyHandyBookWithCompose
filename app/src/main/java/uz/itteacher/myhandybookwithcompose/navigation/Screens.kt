package uz.itteacher.myhandybookwithcompose.navigation

sealed class Screens(route: String) {
    object Home : Screens("home")
}