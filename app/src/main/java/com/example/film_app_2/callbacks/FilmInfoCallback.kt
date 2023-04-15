package com.example.film_app_2.callbacks

import com.example.film_app_2.models.FilmInfo

interface FilmInfoCallback {
    fun process(filmInfo: FilmInfo)
}