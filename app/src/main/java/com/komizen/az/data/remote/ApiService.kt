package com.komizen.az.data.remote

import com.komizen.az.data.model.ArrayFlatIndex
import com.komizen.az.data.model.LegacyStoreIndex
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun fetchArrayFlatIndex(@Url url: String): Response<ArrayFlatIndex>

    @GET
    suspend fun fetchLegacyStoreIndex(@Url url: String): Response<LegacyStoreIndex>

    @Streaming
    @GET
    suspend fun downloadApk(@Url url: String): Response<ResponseBody>
}
