package fr.sdv.alan.movieapp.ui.screen.favorites
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import fr.sdv.alan.movieapp.model.Movie
import fr.sdv.alan.movieapp.util.Constants
import fr.sdv.alan.movieapp.util.UiState
import fr.sdv.alan.movieapp.viewmodel.MoviesViewModel

@Composable
fun FavoritesScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: MoviesViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    val state by viewModel.favoritesState.collectAsState()

    when (val s = state){
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
            if(s.data.isEmpty()){
                Text(
                    text = "Aucun film dans les favoris",
                    modifier = Modifier.padding(16.dp)
                )
            }else{
                FavoritesList(
                    movies = s.data,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}
@Composable
private fun MovieRow(
    movie: Movie,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val posterUrl = movie.posterPath?.let { Constants.TMDB_IMAGE_BASE_URL + it }

            AsyncImage(
                model = posterUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            androidx.compose.foundation.layout.Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "⭐ ${String.format("%.1f", movie.voteAvg)} / 10",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
@Composable
private fun FavoritesList(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies) { movie ->
            MovieRow(
                movie = movie,
                onClick = { onMovieClick(movie.id) }
            )
        }
    }
}