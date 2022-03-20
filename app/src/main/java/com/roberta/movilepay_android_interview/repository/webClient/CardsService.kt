package com.roberta.movilepay_android_interview.repository.webClient


import com.roberta.movilepay_android_interview.model.Cards
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CardsService {

    @GET("home")
    fun getCards(): Call<Cards>

    @GET("card/{id}")
    fun getDetailsDetails(@Path("id") id: Int)

    @GET("statement/{id}")
    fun getDetailsbillaccount(@Path("id") id: Int)
}