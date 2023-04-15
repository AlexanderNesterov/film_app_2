package com.example.film_app_2.activities

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.film_app_2.R
import com.example.film_app_2.application.Application
import com.example.film_app_2.callbacks.FilmSearchCallback
import com.example.film_app_2.controllers.FilmInfoController
import com.example.film_app_2.models.Film
import com.example.film_app_2.models.FilmSearch
import javax.inject.Inject


class FilmSearchActivity : AppCompatActivity() {
    @Inject lateinit var filmInfoController: FilmInfoController

    private var listView: ListView? = null
    private var searchButton: Button? = null
    private var search: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as Application).activityComponent.injectFilmSearchActivity(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_film_list)

        listView = findViewById(R.id.filmListView)
        search = findViewById(R.id.searchFilms)
        searchButton = findViewById(R.id.searchButton)

        val context = this

        searchButton!!.setOnClickListener {
            val searchString = search!!.text.toString()

            filmInfoController.searchFilms(searchString, object : FilmSearchCallback {
                override fun process(filmSearch: FilmSearch) {
                    drawList(filmSearch.results!!, context)
                }
            })
        }
    }

    private fun drawList(films: List<Film>, context: FilmSearchActivity) {
        listView!!.setOnItemClickListener { _, _, position, _ ->
            val selectedFilm = films[position]
            val detailIntent = FilmInfoActivity.newIntent(context, selectedFilm.id!!)
            startActivity(detailIntent)
        }

        val filmInfoList = arrayOfNulls<String>(films.size)
        for (i in films.indices) {
            val film = films[i]
            filmInfoList[i] = film.title + " " + film.description
        }

        val adapter = ArrayAdapter(
            context,
            android.R.layout.simple_list_item_1,
            filmInfoList
        )
        listView!!.adapter = adapter
    }
}