package com.example.hw_3.retrofit

import com.example.hw_3.person.PersonDetailsGit
import com.example.hw_3.person.PersonGitHub
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitAPI {

    @GET("users")
    fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Call<List<PersonGitHub>>

    @GET("users/{username}")
    fun getUserDetails(
        @Path("username") name: String
    ): Call<PersonDetailsGit>

}