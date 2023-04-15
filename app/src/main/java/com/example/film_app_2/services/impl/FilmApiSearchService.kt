package com.example.film_app_2.services.impl

import com.example.film_app_2.callbacks.FilmInfoCallback
import com.example.film_app_2.callbacks.FilmSearchCallback
import com.example.film_app_2.models.FilmInfo
import com.example.film_app_2.models.FilmSearch
import com.example.film_app_2.services.FilmSearchService
import com.example.film_app_2.services.FilmService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmApiSearchService @Inject constructor(private val filmService: FilmService):
    FilmSearchService {

    override fun getFilmById(id: String, filmInfoCallback: FilmInfoCallback) {
        val call: Call<FilmInfo?>? = filmService.getFilmInfo(id)

        call?.enqueue(object : Callback<FilmInfo?> {
            override fun onResponse(
                call: Call<FilmInfo?>,
                response: Response<FilmInfo?>,
            ) {
                filmInfoCallback.process(response.body()!!)
            }

            override fun onFailure(call: Call<FilmInfo?>, t: Throwable) {
                println("Error: $t")
            }
        })
    }

    override fun searchFilms(searchString: String, filmSearchCallback: FilmSearchCallback) {
        val call: Call<FilmSearch?>? = filmService.getFilms(searchString)

        call?.enqueue(object : Callback<FilmSearch?> {
            override fun onResponse(
                call: Call<FilmSearch?>,
                response: Response<FilmSearch?>,
            ) {
                filmSearchCallback.process(response.body()!!)
            }

            override fun onFailure(call: Call<FilmSearch?>, t: Throwable) {
                println("Error: $t")
            }
        })
    }
}