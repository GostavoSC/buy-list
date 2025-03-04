package com.gstv.buylist.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gstv.buylist.ui.screens.HistoryScreen
import com.gstv.buylist.ui.screens.home.HomeScreen
import com.gstv.buylist.ui.screens.NewListScreen

@Composable
fun BuyListColorsNavHostController(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            HomeScreen(
                onHistoryClick = {
                    navController.navigate(Routes.History.route)
                },
                onNewListClick = {
                    navController.navigate(Routes.NewListManually.route)
                }
            )
        }

        slideInComposable(
            route = Routes.History.route
        ){
            HistoryScreen {
                navController.popBackStack()
            }
        }


        slideInComposable(
            Routes.NewListManually.route
        ) {
            NewListScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

    }
}

private fun NavGraphBuilder.slideInComposable(route: String, content: @Composable () -> Unit) {
    composable(
        route = route,
        enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { 300 }) },
        exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { 300 }) }
    ){
        content()
    }
}


sealed class Routes(val route: String) {
    data object Home : Routes(route = "home")
    data object History : Routes(route = "history")
    data object NewListManually : Routes(route = "new-list-manually")
}