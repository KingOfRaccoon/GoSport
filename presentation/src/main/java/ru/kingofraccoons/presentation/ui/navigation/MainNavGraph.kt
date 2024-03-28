package ru.kingofraccoons.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.kingofraccoons.presentation.ui.main.MainScreen

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startRoute: String = Routes.Main.name,
) {
    NavHost(navHostController, startRoute, modifier) {
        composable(Routes.Main.name) {
            MainScreen()
        }
    }
}