package com.nauval.movieit.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String?,
    val overview: String?,
    val rating: Double?,
    val releaseDate: String?,
    val language: String?,
    val posterUrl: String?,
    val bannerUrl: String?,
    val popularity: Double?,
    var isFavorite: Boolean
) : Parcelable {
    fun getActualBannerUrl() =
        if (bannerUrl != null) "https://image.tmdb.org/t/p/w500$bannerUrl" else null

    fun getActualPosterUrl() =
        if (posterUrl != null) "https://image.tmdb.org/t/p/w500$posterUrl" else null
}