package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Search"      ) var search           : List<Movie>?            = null,
    @SerializedName("totalresults") var totalResults     : String?                 = null,
    @SerializedName("Response"    ) var imdbID           : String?                 = null
) : java.io.Serializable
