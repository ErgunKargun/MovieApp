package com.example.movieapp.repository

import com.example.movieapp.model.Movie
import com.example.movieapp.model.SearchResult
import com.example.movieapp.util.Resource

interface MovieDownload {

    suspend fun downloadMovies(title : String) : Resource<SearchResult>

    suspend fun downloadMovieDetail(imdbId : String) : Resource<Movie>
}