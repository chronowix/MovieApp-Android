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

    private val _trendingState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val trendingState: StateFlow<UiState<List<Movie>>> = _trendingState.asStateFlow()

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
}
