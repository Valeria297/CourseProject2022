package com.example.data.provider
import com.example.data.api.GitAPI
import com.example.data.retrofit.GitService

object ServiceProvider {

    private val apiFactory by lazy { GitService() }

    fun provideGitApi(): GitAPI = apiFactory.provideApi()
}