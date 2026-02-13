package fr.sdv.alan.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.sdv.alan.movieapp.BuildConfig
import fr.sdv.alan.movieapp.data.remote.RetrofitInstance
import fr.sdv.alan.movieapp.data.repository.MovieRepository
import fr.sdv.alan.movieapp.model.Movie
import fr.sdv.alan.movieapp.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val repository = MovieRepository(
        api = RetrofitInstance.api,
        apiKey = BuildConfig.TMDB_API_KEY
    )

    //état des films populaires
    private val _trendingState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val trendingState: StateFlow<UiState<List<Movie>>> = _trendingState.asStateFlow()

    //état des films recherchés
    private val _searchState = MutableStateFlow<UiState<List<Movie>>>(UiState.Success(emptyList()))
    val searchState: StateFlow<UiState<List<Movie>>> = _searchState.asStateFlow()

    //état des détails du film
    private val _detailsState = MutableStateFlow<UiState<Movie>>(UiState.Loading)
    val detailsState: StateFlow<UiState<Movie>> = _detailsState.asStateFlow()

    init {
        loadTrending()
    }

    fun loadTrending() {
        _trendingState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val movies = repository.getTrendingMovies()
                _trendingState.value = UiState.Success(movies)
            } catch (e: Exception) {
                _trendingState.value = UiState.Error(e.message ?: "Erreur chargement")
            }
        }
    }

    fun searchMovies(query: String) {
        if (query.isBlank()) {
            _searchState.value = UiState.Success(emptyList())
            return
        }

        _searchState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val movies = repository.searchMovies(query.trim())
                _searchState.value = UiState.Success(movies)
            } catch (e: Exception){
                _searchState.value = UiState.Error(e.message ?: "Erreur chargement")
            }
        }
    }

    fun loadMovieDetails(movieId: Int) {
        _detailsState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val movie = repository.getMovieDetails(movieId)
                _detailsState.value = UiState.Success(movie)
            } catch (e: Exception) {
                _detailsState.value = UiState.Error(e.message ?: "Erreur chargement")
            }
        }
    }
}
