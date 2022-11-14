package com.example.hw_3.presentation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.data.model.NightMode
import com.example.data.sharedprefs.SharedPreferences
import com.example.hw_3.R
import com.example.hw_3.applySelectedAppLanguage
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val sharedprefs: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(
            when (sharedprefs.nightMode) {
                NightMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                NightMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                NightMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                NightMode.ENERGY_SAVING -> AppCompatDelegate.MODE_NIGHT_YES
            }
        )
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.applySelectedAppLanguage(sharedprefs.language))
    }
}
