package com.roberta.movilepay_android_interview.repository.webClient

import com.roberta.movilepay_android_interview.model.cardlist.Cards
import com.roberta.movilepay_android_interview.model.detailaccount.DetailsAccount
import com.roberta.movilepay_android_interview.model.detailscard.DetailsCard
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CardsService {

    @GET("home")
    fun getCards(): Call<Cards>

    @GET("card/{id}")
    fun getDetailsCards(@Path("id") id: Int): Call<DetailsCard>

    @GET("statement/{id}")
    fun getDetailsAccount(@Path("id") id: Int): Call<DetailsAccount>
}