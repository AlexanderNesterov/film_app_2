package com.example.film_app_2.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.film_app_2.R
import com.example.film_app_2.application.Application
import com.example.film_app_2.callbacks.FavoritesCallback
import com.example.film_app_2.controllers.FilmInfoController
import com.example.film_app_2.models.Favorites
import com.example.film_app_2.models.FilmInfo
import javax.inject.Inject

class FavoritesActivity : AppCompatActivity() {
    @Inject
    lateinit var filmController: FilmInfoController

    private var listView: ListView? = null
    private var addFilmButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as Application).activityComponent.injectFilmListActivity(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_list)

        listView = findViewById(R.id.filmListView)
        addFilmButton = findViewById(R.id.addFilmButton)

        val context = this

        filmController.getFavorites(object : FavoritesCallback {
            override fun process(favorites: Favorites) {
                val films = favorites.films
                drawList(films, context)
            }
        })

        addFilmButton!!.setOnClickListener {

            val intent = Intent(this, FilmSearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun drawList(films: List<FilmInfo>, context: Context) {
        listView!!.setOnItemClickListener { _, _, position, _ ->
            val selectedFilm = films[position]
            val detailIntent = FilmInfoActivity.newIntent(context, selectedFilm.id!!)
            startActivity(detailIntent)
        }

        val filmInfoList = arrayOfNulls<String>(films.size)
        for (i in films.indices) {
            val film = films[i]
            filmInfoList[i] = film.title + " " + film.year
        }

        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, filmInfoList)
        listView!!.adapter = adapter
    }
}