package com.nauval.movieit.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nauval.movieit.core.domain.model.Movie
import com.nauval.movieit.core.presentation.MovieListAdapter
import com.nauval.movieit.core.util.Utils
import com.nauval.movieit.detail.DetailActivity
import com.nauval.movieit.favorite.databinding.FragmentFavoriteBinding
import com.nauval.movieit.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favVM: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvAdapter = MovieListAdapter().apply {
            setOnItemClickCallback(object : MovieListAdapter.OnItemClickCallback {
                override fun onItemClicked(item: Movie, position: Int) {
                    startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
                        putExtra(Utils.MOVIE_EXTRA, item)
                    })
                }
            })
        }

        favVM.favoriteMovies.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                if (movies.isEmpty()) {
                    binding.movieRv.visibility = View.GONE
                    binding.initialView.visibility = View.VISIBLE
                } else {
                    binding.movieRv.visibility = View.VISIBLE
                    binding.initialView.visibility = View.GONE
                    rvAdapter.setData(movies)
                }
            }
        }

        with(binding.movieRv) {
            layoutManager = GridLayoutManager(requireContext(), Utils.GRID_COL)
            setItemViewCacheSize(Utils.MAX_CACHE_ITEM)
            adapter = rvAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}