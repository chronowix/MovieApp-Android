package fr.sdv.alan.movieapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.sdv.alan.movieapp.ui.screen.detail.DetailScreen
import fr.sdv.alan.movieapp.ui.screen.favorites.FavoritesScreen
import fr.sdv.alan.movieapp.ui.screen.home.HomeScreen
import fr.sdv.alan.movieapp.ui.screen.search.SearchScreen

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ){
        composable(NavRoutes.HOME) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(NavRoutes.detail(movieId))
                }
            )
        }
        composable(NavRoutes.SEARCH){ SearchScreen() }
        composable(NavRoutes.FAVORITES){ FavoritesScreen() }

        composable(
            route = NavRoutes.DETAILS,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailScreen(movieId = movieId)
        }
    }
}