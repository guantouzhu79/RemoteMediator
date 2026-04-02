package com.rip.remotemediator.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface AlbumService {

    // _limit=10&_page=1 参数名
    @GET("albums")
    suspend fun getAlbums(@QueryMap params: Map<String, String>): Response<List<AlbumBean>>
}