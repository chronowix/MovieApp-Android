package fr.sdv.alan.movieapp.ui.navigation

object NavRoutes{
    const val HOME = "home"
    const val SEARCH = "search"
    const val DETAILS = "detail/{movieId}"
    const val FAVORITES = "favorites"

    fun detail(movieId: Int) = "detail/$movieId"
}