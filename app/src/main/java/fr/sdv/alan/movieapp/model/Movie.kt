package fr.sdv.alan.movieapp.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val voteAvg: Double
)
