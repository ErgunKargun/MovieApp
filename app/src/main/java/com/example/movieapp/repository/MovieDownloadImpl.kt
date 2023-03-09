package com.example.movieapp.repository

import com.example.movieapp.model.Movie
import com.example.movieapp.model.SearchResult
import com.example.movieapp.service.MovieAPI
import com.example.movieapp.util.Resource

class MovieDownloadImpl (private val api : MovieAPI) : MovieDownload {

    val API_KEY = "b2268941"

    override suspend fun downloadMovies(title : String): Resource<SearchResult> {
        return try {
            val response = api.getListData(API_KEY, title)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
            Resource.error("No data!",null)
        }
    }

    override suspend fun downloadMovieDetail(imdbId : String): Resource<Movie> {
        return try {
            val response = api.getDetailData(API_KEY, imdbId)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            Resource.error("No data!",null)
        }
    }
}