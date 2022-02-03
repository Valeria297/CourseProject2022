package com.example.hw_3.provider

import com.example.hw_3.retrofit.GitAPI
import com.example.hw_3.retrofit.GitService

object ServiceProvider {

    private val apiFactory by lazy { GitService() }

    fun provideGitApi(): GitAPI = apiFactory.provideApi()
}