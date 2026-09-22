package com.example.teccerca.data.remoto

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConfiguracionApi {

    private const val BASE_URL = "http://10.0.2.2:3000/api/"


    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}