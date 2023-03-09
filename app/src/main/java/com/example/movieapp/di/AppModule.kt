package com.example.movieapp.di

import com.example.movieapp.repository.MovieDownload
import com.example.movieapp.repository.MovieDownloadImpl
import com.example.movieapp.service.MovieAPI
import com.example.movieapp.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule  = module {

    single {
        val BASE_URL = "https://www.omdbapi.com"
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    single<MovieDownload> {
        MovieDownloadImpl(get())
    }

    viewModel{
        MovieViewModel(get())
    }
}