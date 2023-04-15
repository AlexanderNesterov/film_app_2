package com.example.film_app_2.callbacks

import com.example.film_app_2.models.FilmSearch

interface FilmSearchCallback {
    fun process(filmSearch: FilmSearch)
}