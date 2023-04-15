package com.example.film_app_2.models

import com.google.gson.annotations.SerializedName

class FilmInfo {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("fullTitle")
    var fullTitle: String? = null
    @SerializedName("year")
    var year: String? = null
    @SerializedName("image")
    var image: String? = null
    @SerializedName("plot")
    var plot: String? = null
    @SerializedName("genres")
    var genres: String? = null

    override fun toString(): String {
        return "FilmInfo(id=$id, title=$title, fullTitle=$fullTitle, year=$year, image=$image, plot=$plot, genres=$genres)"
    }
}