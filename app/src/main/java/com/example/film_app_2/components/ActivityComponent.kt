package com.example.film_app_2.components

import com.example.film_app_2.activities.*
import com.example.film_app_2.modules.SearchModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SearchModule::class])
interface ActivityComponent {
    fun injectLoginActivity(loginActivity: LoginActivity)
    fun injectFilmListActivity(favoritesActivity: FavoritesActivity)
    fun injectFilmSearchActivity(filmSearchActivity: FilmSearchActivity)
    fun injectFilmInfoActivity(filmInfoActivity: FilmInfoActivity)
}

