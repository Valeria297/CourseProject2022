package com.example.hw_3.domain.sharedprefs

import android.content.Context
import com.example.hw_3.domain.model.Language

class SharedPreferences(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    var language: Language by enumPref(KEY_LANGUAGE, Language.EN)

    private inline fun <reified E : Enum<E>> enumPref(key: String, defaultValue: E) =
        Delegate(
            sharedPrefs,
            getValue = {
                getString(key, null)?.let(::enumValueOf) ?: defaultValue
            },
            setValue = {
                putString(key, it.name)
            }
        )

    companion object {
        private const val SP_NAME = "sp"
        private const val KEY_LANGUAGE = "locale"
    }
}