package com.lucaszacarias.meli

import android.app.Application
import com.lucaszacarias.meli.di.networkModule
import com.lucaszacarias.meli.di.repositoryModule
import com.lucaszacarias.meli.ui.detail.DetailViewModel
import com.lucaszacarias.meli.ui.mainview.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    repositoryModule
                )
            )
        }
    }
}


val appModule: Module = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
