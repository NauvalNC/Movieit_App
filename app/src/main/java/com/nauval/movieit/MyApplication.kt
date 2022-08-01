package com.nauval.movieit

import android.app.Application
import com.nauval.movieit.core.di.databaseModule
import com.nauval.movieit.core.di.networkModule
import com.nauval.movieit.core.di.repositoryModule
import com.nauval.movieit.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule
                )
            )
        }
    }
}