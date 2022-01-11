package com.example.hw_3.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class GitService {

    private val retrofit by lazy(LazyThreadSafetyMode.NONE) { provideRetrofit() }

    val gitApi by lazy(LazyThreadSafetyMode.NONE) {
        retrofit.create<GitAPI>()
    }

    @JvmName("getGitApi1")
    fun getGitApi() : GitAPI {
        return gitApi
    }

    private fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}