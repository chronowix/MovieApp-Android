package fr.sdv.alan.movieapp.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.sdv.alan.movieapp.model.Movie
import fr.sdv.alan.movieapp.util.UiState
import fr.sdv.alan.movieapp.viewmodel.MoviesViewModel

@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: MoviesViewModel = viewModel()
) {
    val state by viewModel.trendingState.collectAsState()

    when (val s = state) {
        is UiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is UiState.Error -> {
            Text(
                text = "Erreur: ${s.message}",
                modifier = Modifier.padding(16.dp)
            )
        }
        is UiState.Success -> {
            MoviesList(
                movies = s.data,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
private fun MoviesList(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp)
    ) {
        items(movies) { movie ->
            Text(
                text = movie.title,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onMovieClick(movie.id) }
                    .padding(vertical = 10.dp)
            )
        }
    }
}
