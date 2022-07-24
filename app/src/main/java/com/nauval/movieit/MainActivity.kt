package com.nauval.movieit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nauval.movieit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
}