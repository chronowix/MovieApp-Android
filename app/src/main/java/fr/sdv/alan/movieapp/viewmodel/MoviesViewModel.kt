package fr.sdv.alan.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.sdv.alan.movieapp.BuildConfig
import fr.sdv.alan.movieapp.data.local.FavoritesStore
import fr.sdv.alan.movieapp.data.remote.RetrofitInstance
import fr.sdv.alan.movieapp.data.repository.MovieRepository
import fr.sdv.alan.movieapp.model.Movie
import fr.sdv.alan.movieapp.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    // repository pour faire les appels à l'API
    private val repository = MovieRepository(
        api = RetrofitInstance.api,
        apiKey = BuildConfig.TMDB_API_KEY
    )

    //gestion des favoris via DataStore: stockage des IDs favoris de manière locale
    private val favoritesStore = FavoritesStore(application)

    //StateFlow pour savoir si un film est dans les favoris
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    //fonction suspendue pour mettre à jour l'état de favori
    private suspend fun updateIsFavorite(movieId: Int) {
        val favIds = favoritesStore.favoriteIds.first()
        _isFavorite.value = favIds.contains(movieId.toString())
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            favoritesStore.toggleFavorite(movieId)
            updateIsFavorite(movieId)
        }
    }

    private val _trendingState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val trendingState: StateFlow<UiState<List<Movie>>> = _trendingState.asStateFlow()

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

    private val _searchState = MutableStateFlow<UiState<List<Movie>>>(UiState.Success(emptyList()))
    val searchState: StateFlow<UiState<List<Movie>>> = _searchState.asStateFlow()

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
            } catch (e: Exception) {
                _searchState.value = UiState.Error(e.message ?: "Erreur recherche")
            }
        }
    }

    private val _detailState = MutableStateFlow<UiState<Movie>>(UiState.Loading)
    val detailState: StateFlow<UiState<Movie>> = _detailState.asStateFlow()


    fun loadMovieDetail(movieId: Int) {
        _detailState.value = UiState.Loading
        viewModelScope.launch { //scope lié au cycle de vie du viewmodel
            try {
                val movie = repository.getMovieDetails(movieId)
                // Mettre à jour l'état de favori après avoir chargé les détails
                _detailState.value = UiState.Success(movie)
                //Sync avec les favoris locaux
                updateIsFavorite(movieId)
            } catch (e: Exception) {
                _detailState.value = UiState.Error(e.message ?: "Erreur détail")
            }
        }
    }

    init {
        loadTrending()
    }

    private val _favoritesState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val favoritesState: StateFlow<UiState<List<Movie>>> = _favoritesState.asStateFlow()


    fun loadFavorites() {
        _favoritesState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val ids = favoritesStore.favoriteIdsInt.first()
                if (ids.isEmpty()){
                    _favoritesState.value = UiState.Success(emptyList())
                    return@launch
                }

                val movies = ids.map { repository.getMovieDetails(it) }
                _favoritesState.value = UiState.Success(movies)
            } catch (e: Exception) {
                _favoritesState.value = UiState.Error(e.message ?: "Erreur chargement")
            }
        }
    }
}
