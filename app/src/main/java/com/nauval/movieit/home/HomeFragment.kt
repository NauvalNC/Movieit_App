package com.nauval.movieit.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.nauval.movieit.R
import com.nauval.movieit.core.data.Resource
import com.nauval.movieit.core.domain.model.Movie
import com.nauval.movieit.core.presentation.MovieListAdapter
import com.nauval.movieit.core.util.PACKAGE_PATH
import com.nauval.movieit.core.util.SETTING_MODULE
import com.nauval.movieit.core.util.Utils
import com.nauval.movieit.databinding.FragmentHomeBinding
import com.nauval.movieit.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeVM: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
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

        homeVM.lastMovies.observe(viewLifecycleOwner) { movies ->
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
                    rvAdapter.setData(movies.data?.take(9))
                }
            }
        }

        with(binding.popularRv) {
            layoutManager = GridLayoutManager(requireContext(), Utils.GRID_COL)
            setItemViewCacheSize(Utils.MAX_CACHE_ITEM)
            adapter = rvAdapter
        }

        setupMenu()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.setting -> {
                        openSetting()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setError(isError: Boolean, message: String) {
        binding.popularRv.visibility = if (isError) View.GONE else View.VISIBLE
        binding.errorView.visibility = if (isError) View.VISIBLE else View.GONE
        binding.errorTv.text = message
    }

    private fun openDetails(movie: Movie) {
        startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(Utils.MOVIE_EXTRA, movie)
        })
    }

    private fun openSetting() {
        if (Utils.isModuleAvailable(requireContext(), SETTING_MODULE)) {
            startActivity(
                Intent(
                    requireContext(),
                    Class.forName("$PACKAGE_PATH.setting.SettingActivity")
                )
            )
        } else {
            Toast.makeText(
                requireContext(),
                getString(
                    com.nauval.movieit.core.R.string.module_not_exists,
                    SETTING_MODULE
                ), Toast.LENGTH_SHORT
            ).show()
        }
    }
}