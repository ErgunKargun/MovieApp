package com.example.movieapp.service

import com.example.movieapp.model.Movie
import com.example.movieapp.model.SearchResult
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MovieAPI {

    @GET("/")
    suspend fun getListData(@Query("apiKey") apiKey : String, @Query("s") title : String): Response<SearchResult>

    @GET("/")
    suspend fun getDetailData(@Query("apiKey") apiKey : String, @Query("i") imdbId : String): Response<Movie>
}