package fr.sdv.alan.movieapp

import fr.sdv.alan.movieapp.data.mapper.toMovie
import fr.sdv.alan.movieapp.data.remote.dto.TMDBMovieDto
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun `toMovie maps dto correctly`() {

        val dto = TMDBMovieDto(
            id = 1,
            title = "Test Movie",
            overview = "Test overview",
            posterPath = "/test.jpg",
            voteAverage = 8.5
        )

        val movie = dto.toMovie()

        assertEquals(1, movie.id)
        assertEquals("Test Movie", movie.title)
        assertEquals("Test overview", movie.overview)
        assertEquals("/test.jpg", movie.posterPath)
        assertEquals(8.5, movie.voteAvg, 0.0)
    }
}
