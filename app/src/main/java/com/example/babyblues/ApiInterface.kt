package com.example.babyblues

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


object RetrofitApi {

    private val retrofit = Retrofit.Builder().baseUrl("https://zenquotes.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiInterface = retrofit.create(ApiInterface::class.java)
    val gson = Gson()


}


//7w65ObgKbqEBbM8PPEEyUwyaJK2YgcGrs4gcZ3qW
interface ApiInterface {

    @GET("random")
    fun getResponse(): Call<Arrayquote?>?



}