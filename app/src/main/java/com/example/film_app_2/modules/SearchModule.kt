package com.example.film_app_2.modules

import com.example.film_app_2.application.Application
import com.example.film_app_2.services.impl.FilmApiSearchService
import com.example.film_app_2.services.impl.FilmFireBaseSearchService
import com.example.film_app_2.services.FilmSearchService
import com.example.film_app_2.services.FilmService
import com.example.film_app_2.services.impl.SqlLiteSearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
class SearchModule {
    @Singleton
    @Provides
    @Named("filmApiSearchService")
    fun filmApiSearchService(): FilmSearchService {
        return FilmApiSearchService(filmService())
    }

    @Singleton
    @Provides
    @Named("filmFireBaseSearchService")
    fun filmFireBaseSearchService(): FilmSearchService {
        return FilmFireBaseSearchService(sqlLiteSearchService())
    }

    @Singleton
    @Provides
    @Named("sqlLiteSearchService")
    fun sqlLiteSearchService(): FilmSearchService {
        return SqlLiteSearchService(Application.getContext(), filmApiSearchService())
    }

    @Singleton
    @Provides
    fun filmService(): FilmService {
        return RetrofitClientInstance.retrofitInstance!!.create(
            FilmService::class.java
        )
    }

    object RetrofitClientInstance {
        private var retrofit: Retrofit? = null
        private const val BASE_URL = "https://imdb-api.com"
        val retrofitInstance: Retrofit?
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retrofit
            }
    }
}