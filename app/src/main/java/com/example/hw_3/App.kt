package com.example.hw_3

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.hw_3.data.locale.LanguageAppContext
import com.example.hw_3.presentation.koin.networkModule
import com.example.hw_3.presentation.koin.repositoryModule
import com.example.hw_3.presentation.koin.useCaseModule
import com.example.hw_3.presentation.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())

        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageAppContext(base, application = this))
    }
}