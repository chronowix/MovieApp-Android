package fr.sdv.alan.movieapp.data.repository

import fr.sdv.alan.movieapp.data.mapper.toMovie
import fr.sdv.alan.movieapp.data.remote.TMDBApi
import fr.sdv.alan.movieapp.model.Movie

class MovieRepository(
    private val api: TMDBApi,
    private val apiKey: String
) {

    suspend fun getTrendingMovies(): List<Movie> {
        val response = api.getTrendingMovies(apiKey = apiKey)
        return response.results.orEmpty().map { it.toMovie() }
    }

    suspend fun searchMovies(query: String): List<Movie> {
        val response = api.searchMovies(apiKey = apiKey, query = query)
        return response.results.orEmpty().map { it.toMovie() }
    }

    suspend fun getMovieDetails(movieId: Int): Movie {
        val dto = api.getMovieDetails(movieId = movieId, apiKey = apiKey)
        return dto.toMovie()
    }
}
