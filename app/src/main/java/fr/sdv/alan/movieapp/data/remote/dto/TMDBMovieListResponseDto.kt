package fr.sdv.alan.movieapp.data.remote.dto

import com.squareup.moshi.Json

data class TMDBMovieListResponseDto(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<TMDBMovieDto>?
)
