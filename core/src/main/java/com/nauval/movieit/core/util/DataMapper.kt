package com.nauval.movieit.core.util

import com.nauval.movieit.core.data.source.local.entity.MovieEntity
import com.nauval.movieit.core.data.source.remote.response.MovieResponse
import com.nauval.movieit.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val entities = ArrayList<MovieEntity>()
        input.map {
            val entity = MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                rating = it.rating,
                releaseDate = it.releaseDate,
                language = it.language,
                posterUrl = it.posterUrl,
                bannerUrl = it.bannerUrl,
                popularity = it.popularity,
                isFavorite = false
            )
            entities.add(entity)
        }
        return entities
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                rating = it.rating,
                releaseDate = it.releaseDate,
                language = it.language,
                posterUrl = it.posterUrl,
                bannerUrl = it.bannerUrl,
                popularity = it.popularity,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        overview = input.overview,
        rating = input.rating,
        releaseDate = input.releaseDate,
        language = input.language,
        posterUrl = input.posterUrl,
        bannerUrl = input.bannerUrl,
        popularity = input.popularity,
        isFavorite = input.isFavorite
    )
}