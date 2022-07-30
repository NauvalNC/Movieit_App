package com.nauval.movieit.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.nauval.movieit.core.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    const val MOVIE_EXTRA = "MOVIE_EXTRA"
    const val MAX_CACHE_ITEM = 50
    const val GRID_COL = 3

    @SuppressLint("SimpleDateFormat")
    fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("dd MMM yyyy")
        var formatted = "Unknown"

        try {
            val date = inputFormat.parse(dateString) as Date
            formatted = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return formatted
    }

    fun getCircularProgressDrawable(context: Context): CircularProgressDrawable {
        return CircularProgressDrawable(context).apply {
            strokeWidth = 8f
            centerRadius = 24f
            start()
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = manager.activeNetwork ?: return false
            val activeNetwork = manager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = manager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    fun isModuleAvailable(context: Context, moduleName: String): Boolean {
        val splitInstallManager = SplitInstallManagerFactory.create(context)
        return splitInstallManager.installedModules.contains(moduleName)
    }
}