package com.example.film_app_2.models

import com.google.gson.annotations.SerializedName

class Film {
    @SerializedName("id")
    val id: String? = null
    @SerializedName("image")
    val image: String? = null
    @SerializedName("title")
    val title: String? = null
    @SerializedName("description")
    val description: String? = null

    override fun toString(): String {
        return "Film(id=$id, image=$image, title=$title, description=$description)"
    }
}