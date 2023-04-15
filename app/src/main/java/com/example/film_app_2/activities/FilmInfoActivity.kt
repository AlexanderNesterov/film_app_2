package com.example.film_app_2.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.film_app_2.R
import com.example.film_app_2.application.Application
import com.example.film_app_2.callbacks.FavoritesCallback
import com.example.film_app_2.controllers.FilmInfoController
import com.example.film_app_2.models.FilmInfo
import com.example.film_app_2.callbacks.FilmInfoCallback
import com.example.film_app_2.models.Favorites
import com.squareup.picasso.Picasso
import javax.inject.Inject

class FilmInfoActivity : AppCompatActivity() {
    @Inject lateinit var filmInfoController: FilmInfoController
    private var filmTitle: TextView? = null
    private var fullTitle: TextView? = null
    private var plot: TextView? = null
    private var year: TextView? = null
    private var genres: TextView? = null
    private var image: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as Application).activityComponent.injectFilmInfoActivity(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_info)

        filmTitle = findViewById(R.id.filmTitle)
        fullTitle = findViewById(R.id.fullTitleLabel)
        plot = findViewById(R.id.plotLabel)
        year = findViewById(R.id.yearLabel)
        genres = findViewById(R.id.genresLabel)
        image = findViewById(R.id.filmImage)

        val addButton: Button = findViewById(R.id.addFilmButton)
        val removeButton: Button = findViewById(R.id.removeFilmButton)

        val filmId = intent.extras?.getString(FILM_ID)!!
        val context = this
        var currentFilm = FilmInfo()

        filmInfoController.getFavorites(object : FavoritesCallback {
            override fun process(favorites: Favorites) {
                val films = favorites.films

                for (film in films) {
                    if (film.id == filmId) {
                        addButton.visibility = INVISIBLE
                        return
                    }
                }

                removeButton.visibility = INVISIBLE
            }
        })

        filmInfoController.getById(filmId, object : FilmInfoCallback {
            override fun process(filmInfo: FilmInfo) {
                currentFilm = filmInfo
                drawInfo(filmInfo, context)
            }
        })

        addButton.setOnClickListener {
            filmInfoController.addToFavorites(currentFilm)
            addButton.visibility = INVISIBLE
            removeButton.visibility = VISIBLE
        }

        removeButton.setOnClickListener {
            filmInfoController.removeFromFavorites(currentFilm.id!!)
            addButton.visibility = VISIBLE
            removeButton.visibility = INVISIBLE
        }
    }

    private fun drawInfo(filmInfo: FilmInfo, context: Context) {
        Picasso
            .with(context)
            .load(filmInfo.image)
            .into(image)
        filmTitle!!.text = filmInfo.title
        fullTitle!!.text = "Full Title: " + filmInfo.fullTitle
        plot!!.text = "Plot: " + filmInfo.plot
        year!!.text = "Year: " + filmInfo.year
        genres!!.text = "Genres: " + filmInfo.genres
    }

    companion object {
        const val FILM_ID = "id"

        fun newIntent(context: Context, filmId: String): Intent {
            val detailIntent = Intent(context, FilmInfoActivity::class.java)

            detailIntent.putExtra(FILM_ID, filmId)
            return detailIntent
        }
    }
}