package com.example.film_app_2.callbacks

import com.example.film_app_2.models.Favorites

interface FavoritesCallback {
    fun process(favorites: Favorites)
}