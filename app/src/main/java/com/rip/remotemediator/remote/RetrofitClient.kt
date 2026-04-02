package com.rip.remotemediator.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
//import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val loggingInterceptor: HttpLoggingInterceptor by lazy{
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d("intercepter", "http/https/message ->${message}")
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor).build()

    private val retrofitClient = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
        .client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    fun<T> getService(clazz: Class<T>): T {
        return retrofitClient.create(clazz)
    }
}