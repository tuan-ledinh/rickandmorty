package com.tuan.rickandmorty.network

import com.tuan.rickandmorty.model.Character
import com.tuan.rickandmorty.model.PaginatedList
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Long = 1
    ): PaginatedList<Character>
}