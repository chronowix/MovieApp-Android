package fr.sdv.alan.movieapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
){
    data object Home : BottomNavItem(
        route = NavRoutes.HOME,
        label = "Home",
        icon = Icons.Default.Home
    )

    data object Search : BottomNavItem(
        route = NavRoutes.SEARCH,
        label = "Search",
        icon = Icons.Default.Search
    )

    data object Favorites : BottomNavItem(
        route = NavRoutes.FAVORITES,
        label = "Favorites",
        icon = Icons.Default.Favorite
    )
}