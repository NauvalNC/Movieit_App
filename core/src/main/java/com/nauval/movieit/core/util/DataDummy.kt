package com.nauval.movieit.core.util

import com.nauval.movieit.core.domain.model.Movie

object DataDummy {
    val dummyMovie = Movie(
        id = 0,
        title = "Title",
        overview = "Overview",
        rating = 5.0,
        releaseDate = "2022-07-06",
        language = "en",
        posterUrl = null,
        bannerUrl = null,
        popularity = 1500.0,
        isFavorite = false
    )

    fun generateDummyMovies(onlyFavorites: Boolean = false): List<Movie> {
        val list = ArrayList<Movie>()
        for (i in 0..10) {
            val item = Movie(
                id = i,
                title = "Title $i",
                overview = "Overview $i",
                rating = 5.0,
                releaseDate = "2022-07-06",
                language = "en",
                posterUrl = null,
                bannerUrl = null,
                popularity = 1500.0,
                isFavorite = false
            )
            if (i in 5..8) item.isFavorite = true

            if (onlyFavorites) {
                if (item.isFavorite) list.add(item)
            } else {
                list.add(item)
            }
        }
        return list
    }
}