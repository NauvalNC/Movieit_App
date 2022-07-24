package com.nauval.movieit.core.data.source.local.room

import androidx.room.*
import com.nauval.movieit.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE title LIKE '%' || :title || '%'")
    fun searchMovies(title: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE is_favorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)
}