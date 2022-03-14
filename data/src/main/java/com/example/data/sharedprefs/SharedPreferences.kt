package com.example.data.sharedprefs

import android.content.Context
import com.example.data.model.Language
import com.example.data.model.NightMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedPreferences(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    var nightMode: NightMode by enumPref(KEY_NIGHT_MODE, NightMode.LIGHT)

    var language: Language by Delegate(
        sharedPrefs,
        getValue = {
            getString(KEY_LANGUAGE, null)
                ?.let { enumValueOf<Language>(it) }
                ?: Language.EN
        },
        setValue = {
            putString(KEY_LANGUAGE, it.name)
            _languageFlow.tryEmit(it)
        }
    )

    private val _languageFlow = MutableStateFlow(language)
    val languageFlow: Flow<Language> = _languageFlow.asStateFlow()

    private inline fun <reified E : Enum<E>> enumPref(key: String, defaultValue: E) =
        Delegate(
            sharedPrefs,
            getValue = { getString(key, null)?.let(::enumValueOf) ?: defaultValue },
            setValue = { putString(key, it.name) }
        )

    companion object {
        private const val SP_NAME = "sp"
        private const val KEY_LANGUAGE = "locale"
        private const val KEY_NIGHT_MODE = "night_mode"
    }
}