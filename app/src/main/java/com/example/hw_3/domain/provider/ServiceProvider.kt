package com.example.hw_3.domain.provider

import com.example.hw_3.data.api.GitAPI
import com.example.hw_3.data.retrofit.GitService

object ServiceProvider {

    private val apiFactory by lazy { GitService() }

    fun provideGitApi(): GitAPI = apiFactory.provideApi()
}