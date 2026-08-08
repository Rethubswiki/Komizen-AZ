package com.komizen.az.data.remote

import com.komizen.az.data.model.RepoIndex
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun fetchIndex(@Url url: String): RepoIndex
}