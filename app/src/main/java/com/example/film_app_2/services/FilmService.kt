package com.example.film_app_2.services

import com.example.film_app_2.models.FilmInfo
import com.example.film_app_2.models.FilmSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmService {
    @GET("/en/API/SearchMovie/{token}/{search}")
    fun getFilms(@Path(value="search", encoded=false) search: String): Call<FilmSearch?>?

    @GET("/en/API/Title/{token}/{filmId}/Trailer")
    fun getFilmInfo(@Path(value="filmId", encoded=false) filmId: String): Call<FilmInfo?>?
}