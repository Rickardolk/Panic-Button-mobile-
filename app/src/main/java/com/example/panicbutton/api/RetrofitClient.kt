package com.example.panicbutton.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://172.16.100.137/button/panic_button_mobile/api/"

    fun create(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        val gson = GsonBuilder()
            .setLenient()
            .create()
    }
}

object RetrofitClientWeb {

    private const val BASE_URL_WEB  = "http://172.16.100.137/button/"

    fun create(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_WEB)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}