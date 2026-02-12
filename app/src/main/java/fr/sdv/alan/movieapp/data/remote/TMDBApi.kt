package fr.sdv.alan.movieapp.data.remote

import fr.sdv.alan.movieapp.data.remote.dto.TMDBMovieListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String
    ): TMDBMovieListResponseDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): TMDBMovieListResponseDto
}
