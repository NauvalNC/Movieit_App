package com.nauval.movieit.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nauval.movieit.R
import com.nauval.movieit.core.data.Resource
import com.nauval.movieit.core.domain.model.Movie
import com.nauval.movieit.core.presentation.MovieListAdapter
import com.nauval.movieit.core.util.Utils
import com.nauval.movieit.databinding.FragmentSearchBinding
import com.nauval.movieit.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchVM: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvAdapter = MovieListAdapter().apply {
            setOnItemClickCallback(object : MovieListAdapter.OnItemClickCallback {
                override fun onItemClicked(item: Movie, position: Int) {
                    openDetails(item)
                }
            })
        }

        searchVM.searchResult.observe(viewLifecycleOwner) { movies ->
            binding.initialView.visibility = View.GONE
            when (movies) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    setError(true, movies.message ?: getString(R.string.not_found))
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val data = movies.data
                    setError(data.isNullOrEmpty(), getString(R.string.not_found))
                    rvAdapter.setData(movies.data)
                }
            }
        }

        with(binding.movieRv) {
            layoutManager = GridLayoutManager(requireContext(), Utils.GRID_COL)
            setItemViewCacheSize(Utils.MAX_CACHE_ITEM)
            adapter = rvAdapter
        }

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
            ?.setOnItemReselectedListener {
                binding.movieRv.smoothScrollToPosition(binding.movieRv.top)
            }

        setupMenu()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)

                val searchManager =
                    requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
                (menu.findItem(R.id.search)?.actionView as SearchView)
                    .apply {
                        queryHint = resources.getString(R.string.search)
                        setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                clearFocus()
                                if (query != null && query.trim() != "") searchVM.setQuery(query)
                                return true
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                if (newText != null && newText.trim() != "") searchVM.setQuery(
                                    newText
                                )
                                return true
                            }
                        })
                    }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setError(isError: Boolean, message: String) {
        binding.movieRv.visibility = if (isError) View.GONE else View.VISIBLE
        binding.errorView.visibility = if (isError) View.VISIBLE else View.GONE
        binding.errorTv.text = message
    }

    private fun openDetails(movie: Movie) {
        startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(Utils.MOVIE_EXTRA, movie)
        })
    }
}