package fr.sdv.alan.movieapp.data.remote.dto

import com.squareup.moshi.Json

data class TMDBMovieDto(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_average") val voteAverage: Double?
)