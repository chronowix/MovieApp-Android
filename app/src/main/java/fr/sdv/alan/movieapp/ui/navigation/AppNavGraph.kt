package fr.sdv.alan.movieapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.sdv.alan.movieapp.ui.screen.detail.DetailScreen
import fr.sdv.alan.movieapp.ui.screen.favorites.FavoritesScreen
import fr.sdv.alan.movieapp.ui.screen.home.HomeScreen
import fr.sdv.alan.movieapp.ui.screen.search.SearchScreen

//navigation entre les différents onglets
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute in listOf(
        NavRoutes.HOME,
        NavRoutes.SEARCH,
        NavRoutes.FAVORITES
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomBar(navController = navController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = NavRoutes.HOME,
            modifier = modifier.padding(innerPadding) // ✅ utilise innerPadding
        ) {
            composable(NavRoutes.HOME) {
                HomeScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(NavRoutes.detail(movieId))
                    }
                )
            }

            composable(NavRoutes.SEARCH) { SearchScreen(
                onMovieClick = { movieId ->
                    navController.navigate(NavRoutes.detail(movieId))
                }
            ) }
            composable(NavRoutes.FAVORITES) { FavoritesScreen(
                onMovieClick = { movieId ->
                    navController.navigate(NavRoutes.detail(movieId))
                }
            ) }

            composable(
                route = NavRoutes.DETAILS,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { entry ->
                val movieId = entry.arguments?.getInt("movieId") ?: 0
                DetailScreen(movieId = movieId)
            }
        }
    }
}
