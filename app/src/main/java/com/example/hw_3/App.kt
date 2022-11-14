package com.example.hw_3

import android.app.Application
import android.content.Context
import com.example.data.locale.LanguageAppContext
import com.example.data.sharedprefs.SharedPreferences
import com.example.hw_3.presentation.koin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    private val sharedPreferences: SharedPreferences by inject()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageAppContext(base, application = this))
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                viewModelModule,
                repositoryModule,
                useCaseModule,
                retrofitModule,
                sharedPrefsModule
            )
        }

        sharedPreferences
            .languageFlow
            .onEach {
                (baseContext as LanguageAppContext).appLanguage = it
            }
            .launchIn(CoroutineScope(Dispatchers.IO))
    }

}