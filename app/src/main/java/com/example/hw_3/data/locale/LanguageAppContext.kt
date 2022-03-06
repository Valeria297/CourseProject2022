package com.example.hw_3.data.locale

import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import com.example.hw_3.applySelectedAppLanguage
import com.example.hw_3.domain.model.Language
import com.example.hw_3.toast
import java.util.*

class LanguageAppContext(base: Context, application: Application) : ContextWrapper(base) {

    var appLanguage: Language = getDefaultLanguage()
        set(value) {
            field = value
            localizedResources = getLocalizedResources(value)
        }

    private var localizedResources = getLocalizedResources(appLanguage)

    init {
        application.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                localizedResources = getLocalizedResources(appLanguage)
            }

            override fun onLowMemory() { toast("Insufficient amount of memory") }
        })
    }

    private fun getDefaultLanguage(): Language {
        return Language.values()
            .firstOrNull { language ->
                language.locale.language == Locale.getDefault().language
            }
            ?: DEFAULT_LANGUAGE
    }

    private fun getLocalizedResources(language: Language): Resources {
        return baseContext.applySelectedAppLanguage(language).resources
    }

    override fun getResources(): Resources = localizedResources

    companion object {
        private val DEFAULT_LANGUAGE = Language.EN
    }
}