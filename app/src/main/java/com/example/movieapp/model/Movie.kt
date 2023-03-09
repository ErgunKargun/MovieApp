package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title"      ) var Title      : String?            = null,
    @SerializedName("Year"       ) var Year       : String?            = null,
    @SerializedName("Director"   ) var Director   : String?            = null,
    @SerializedName("Writer"     ) var Writer     : String?            = null,
    @SerializedName("Actors"     ) var Actors     : String?            = null,
    @SerializedName("Language"   ) var Language   : String?            = null,
    @SerializedName("Country"    ) var Country    : String?            = null,
    @SerializedName("Awards"     ) var Awards     : String?            = null,
    @SerializedName("Poster"     ) var Poster     : String?            = null,
    @SerializedName("imdbRating" ) var imdbRating : String?            = null,
    @SerializedName("imdbID"     ) var imdbID     : String?            = null
) : java.io.Serializable
