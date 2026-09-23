package com.example.teccerca.data.remoto

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ConfiguracionApi {


    private const val BASE_URL =
        "http://192.168.59.1:3000/api/"


    private val cliente =
        OkHttpClient.Builder()


            .connectTimeout(
                30,
                TimeUnit.SECONDS
            )


            .readTimeout(
                30,
                TimeUnit.SECONDS
            )


            .writeTimeout(
                30,
                TimeUnit.SECONDS
            )


            .build()



    val retrofit: Retrofit =
        Retrofit.Builder()

            .baseUrl(BASE_URL)

            .client(cliente)

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()


}