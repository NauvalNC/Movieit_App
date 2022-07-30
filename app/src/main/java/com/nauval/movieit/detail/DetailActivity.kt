package com.nauval.movieit.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.nauval.movieit.R
import com.nauval.movieit.core.domain.model.Movie
import com.nauval.movieit.core.util.FAVORITE_MODULE
import com.nauval.movieit.core.util.Utils
import com.nauval.movieit.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val detailVM: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie: Movie? = intent.getParcelableExtra(Utils.MOVIE_EXTRA)
        if (movie == null) finish()

        with(movie as Movie) {
            supportActionBar?.also {
                it.title = title
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowHomeEnabled(true)
            }

            binding.itemTitle.text = title
            binding.itemRating.text =
                getString(com.nauval.movieit.core.R.string.movie_rating, rating)
            if (releaseDate != null) binding.itemReleaseDate.text = Utils.formatDate(releaseDate!!)
            binding.itemLanguage.text = language?.uppercase()
            binding.itemOverview.text = overview

            Glide.with(this@DetailActivity)
                .load(getActualBannerUrl())
                .placeholder(Utils.getCircularProgressDrawable(this@DetailActivity))
                .error(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        com.nauval.movieit.core.R.drawable.banner_placeholder
                    )
                )
                .into(binding.itemBanner)

            Glide.with(this@DetailActivity)
                .load(getActualPosterUrl())
                .placeholder(Utils.getCircularProgressDrawable(this@DetailActivity))
                .error(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        com.nauval.movieit.core.R.drawable.poster_placeholder
                    )
                )
                .into(binding.itemPoster)

            setFavoriteState(isFavorite)
            binding.favoriteFab.setOnClickListener {
                if (Utils.isModuleAvailable(this@DetailActivity, FAVORITE_MODULE)) {
                    isFavorite = !isFavorite
                    detailVM.setFavoriteMovie(this, isFavorite)
                    setFavoriteState(isFavorite)
                } else {
                    Toast.makeText(
                        this@DetailActivity,
                        getString(
                            com.nauval.movieit.core.R.string.module_not_exists,
                            FAVORITE_MODULE
                        ), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteState(isFavorite: Boolean) {
        with(binding.favoriteFab) {
            setImageResource(
                if (isFavorite) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}