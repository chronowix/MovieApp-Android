package fr.sdv.alan.movieapp.data.mapper

import fr.sdv.alan.movieapp.data.remote.dto.TMDBMovieDto
import fr.sdv.alan.movieapp.model.Movie

fun TMDBMovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title ?: "",
        overview = overview ?: "",
        posterPath = posterPath,
        voteAvg = voteAverage ?: 0.0
    )
}

