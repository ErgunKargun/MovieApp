package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.Movie
import com.example.movieapp.model.SearchResult
import com.example.movieapp.repository.MovieDownload
import com.example.movieapp.util.Resource
import kotlinx.coroutines.*

class MovieViewModel(
    private val movieDownloadRepository : MovieDownload
) : ViewModel() {

    val movies = MutableLiveData<Resource<List<Movie>>>()
    val movie = MutableLiveData<Resource<Movie>>()
    val progress = MutableLiveData<Resource<Boolean>>()
    val error = MutableLiveData<Resource<Boolean>>()

    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        error.value = Resource.error(throwable.localizedMessage ?: "error!",data = true)
    }

    fun getMoviesFromAPI(title : String) {

        progress.value = Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val moviesSearchResult = movieDownloadRepository.downloadMovies(title)
            withContext(Dispatchers.Main) {
                moviesSearchResult.data?.let {
                    progress.value = Resource.loading(false)
                    error.value = Resource.error("",data = false)
                    moviesSearchResult.data.search?.let {
                        movies.value = Resource.success(it)
                    }
                }
            }
        }
    }

    fun getMovieDetailFromAPI(imdbId : String) {

        progress.value = Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = movieDownloadRepository.downloadMovieDetail(imdbId)
            withContext(Dispatchers.Main) {
                resource.data?.let {
                    progress.value = Resource.loading(false)
                    error.value = Resource.error("",data = false)
                    movie.value = resource
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}