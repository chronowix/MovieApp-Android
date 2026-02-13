package fr.sdv.alan.movieapp.ui.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import fr.sdv.alan.movieapp.util.Constants
import fr.sdv.alan.movieapp.util.UiState
import fr.sdv.alan.movieapp.viewmodel.MoviesViewModel

@Composable
fun DetailScreen(
    movieId: Int,
    viewModel: MoviesViewModel = viewModel()
){
    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }

    val state by viewModel.detailsState.collectAsState()

    when(val s = state){
        is UiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is UiState.Error ->{
            Text(
                text = "Erreur: ${s.message}",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }

        is UiState.Success -> {
            val movie = s.data
            val posterUrl = movie.posterPath?.let { Constants.TMDB_IMAGE_BASE_URL + it }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = posterUrl,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "⭐ ${String.format("%.1f", movie.voteAvg)} / 10",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = movie.overview.ifBlank { "Pas de description." },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}