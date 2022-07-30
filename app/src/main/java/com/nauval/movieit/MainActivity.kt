package com.nauval.movieit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nauval.movieit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigation()
    }

    private fun setBottomNavigation() {
        val navCtrl = findNavController(R.id.content_fragment)
        setupActionBarWithNavController(
            navCtrl,
            AppBarConfiguration.Builder(
                setOf(
                    R.id.home_menu,
                    R.id.search_menu,
                    R.id.favorite_menu
                )
            ).build()
        )
        binding.bottomNav.setupWithNavController(navCtrl)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}