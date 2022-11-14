package com.example.data.extensions

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import com.example.data.model.Language
import java.util.*

//Locale extension
fun Context.applySelectedAppLanguage(language: Language): Context {
    val newConfig = Configuration(resources.configuration)
    Locale.setDefault(language.locale)
    newConfig.setLocale(language.locale)

    return createConfigurationContext(newConfig)
}

//Toast extension
fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()