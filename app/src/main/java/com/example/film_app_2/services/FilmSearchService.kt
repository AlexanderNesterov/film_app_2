package com.example.film_app_2.services

import com.example.film_app_2.callbacks.FilmInfoCallback
import com.example.film_app_2.callbacks.FilmSearchCallback

interface FilmSearchService {
    fun getFilmById(id: String, filmInfoCallback: FilmInfoCallback)
    fun searchFilms(searchString: String, filmSearchCallback: FilmSearchCallback)
}