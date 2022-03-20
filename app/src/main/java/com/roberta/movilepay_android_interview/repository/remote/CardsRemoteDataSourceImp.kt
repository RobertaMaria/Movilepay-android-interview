package com.roberta.movilepay_android_interview.repository.remote

import com.roberta.movilepay_android_interview.model.Cards
import com.roberta.movilepay_android_interview.repository.webClient.CardsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CardsRemoteDataSourceImp(private val service: CardsService) : CardsRemoteDataSource {

    override fun getCards(success: (Cards) -> Unit, failure: (String) -> Unit) {
        val call = service.getCards()

        call.enqueue(object : Callback<Cards> {
            override fun onResponse(call: Call<Cards>, response: Response<Cards>) {
                if (response.isSuccessful) {
                    val cards = response.body()
                    if (cards != null) {
                        success(cards)
                    }else{
                        failure(response.message())
                    }
                }
            }

            override fun onFailure(call: Call<Cards>, t: Throwable) {
                failure(t.message.toString())
            }

        })
    }
}