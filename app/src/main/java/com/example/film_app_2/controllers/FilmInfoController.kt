package com.example.film_app_2.controllers

import com.example.film_app_2.callbacks.FavoritesCallback
import com.example.film_app_2.models.FilmInfo
import com.example.film_app_2.callbacks.FilmInfoCallback
import com.example.film_app_2.callbacks.FilmSearchCallback
import com.example.film_app_2.models.Favorites
import com.example.film_app_2.services.FilmSearchService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class FilmInfoController @Inject constructor(@Named("filmFireBaseSearchService") private val filmSearchService: FilmSearchService) {

    fun getById(id: String, filmInfoCallback: FilmInfoCallback) {
        filmSearchService.getFilmById(id, filmInfoCallback)
    }

    fun searchFilms(searchString: String, filmSearchCallback: FilmSearchCallback) {
        filmSearchService.searchFilms(searchString, filmSearchCallback)
    }

    fun addToFavorites(filmInfo: FilmInfo) {
        val db = FirebaseDatabase.getInstance()
        val filmReference = db.getReference("favorites")
        var added = false

        filmReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val oldFavorites = dataSnapshot.getValue(Favorites::class.java)
                if (added) {
                    return
                }
                added = if (oldFavorites == null) {
                    val favorites = Favorites()
                    favorites.films.add(filmInfo)
                    filmReference.setValue(favorites)
                    true
                } else {
                    oldFavorites.films.add(filmInfo)
                    filmReference.setValue(oldFavorites)
                    true
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error: $databaseError")
            }
        })
    }

    fun getFavorites(favoritesCallback: FavoritesCallback) {
        val db = FirebaseDatabase.getInstance()
        val favoritesReference = db.getReference("favorites")

        favoritesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val favorites = dataSnapshot.getValue(Favorites::class.java)
                if (favorites == null) {
                    favoritesCallback.process(Favorites())
                } else {
                    favoritesCallback.process(favorites)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error: $databaseError")
            }
        })
    }

    fun removeFromFavorites(id: String) {
        val db = FirebaseDatabase.getInstance()
        val favoritesReference = db.getReference("favorites")
        var removed = false

        favoritesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val favorites = dataSnapshot.getValue(Favorites::class.java)
                if (removed) {
                    return
                }
                if (favorites != null) {
                    val films = favorites.films
                    var filmToRemove: FilmInfo? = null

                    for (film in films) {
                        if (film.id == id) {
                            filmToRemove = film
                            break
                        }
                    }

                    val newFavorites = Favorites()
                    if (filmToRemove != null) {
                        films.remove(filmToRemove)
                    }
                    newFavorites.films = films
                    removed = true

                    favoritesReference.setValue(newFavorites)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error: $databaseError")
            }
        })
    }
}