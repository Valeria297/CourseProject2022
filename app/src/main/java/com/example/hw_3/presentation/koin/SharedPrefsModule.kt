package com.example.hw_3.presentation.koin

import com.example.data.sharedprefs.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPrefsModule = module {
    single { SharedPreferences(androidContext()) }
}