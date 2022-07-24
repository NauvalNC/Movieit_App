package com.nauval.movieit.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("vote_average")
    val rating: Double?,

    @field:SerializedName("release_date")
    val releaseDate: String?,

    @field:SerializedName("original_language")
    val language: String?,

    @field:SerializedName("poster_path")
    val posterUrl: String?,

    @field:SerializedName("popularity")
    val popularity: Double?,

    @field:SerializedName("backdrop_path")
    val bannerUrl: String?
)