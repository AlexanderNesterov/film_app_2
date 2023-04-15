package com.example.film_app_2.models

import com.google.gson.annotations.SerializedName

class FilmSearch {
    @SerializedName("expression")
    val expression: String? = null
    @SerializedName("results")
    val results: List<Film>? = null

    override fun toString(): String {
        return "FilmSearch(expression=$expression, results=$results)"
    }
}