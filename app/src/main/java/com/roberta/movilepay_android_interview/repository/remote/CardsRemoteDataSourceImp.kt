package com.roberta.movilepay_android_interview.repository.remote

import com.google.gson.Gson
import com.roberta.movilepay_android_interview.model.cardlist.Cards
import com.roberta.movilepay_android_interview.model.detailaccount.DetailsAccount
import com.roberta.movilepay_android_interview.model.detailscard.DetailsCard
import com.roberta.movilepay_android_interview.model.errorResponse.FailureResponse
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
                    } else {
                        failure(response.message())
                    }
                }  else {
                    response.errorBody()?.string()?.let { sendMessageFailure(it, failure) }
                }
            }

            override fun onFailure(call: Call<Cards>, t: Throwable) {
                failure(t.message.toString())
            }

        })
    }

    override fun getDetailsCard(success: (DetailsCard) -> Unit, failure: (String) -> Unit, id: Int) {
        val call = service.getDetailsCards(id)

        call.enqueue(object : Callback<DetailsCard> {
            override fun onResponse(call: Call<DetailsCard>, response: Response<DetailsCard>) {
                if (response.isSuccessful) {
                    val detailsCard = response.body()
                    if (detailsCard != null) {
                        success(detailsCard)
                    } else {
                        failure(response.message())
                    }
                }  else {
                    response.errorBody()?.string()?.let { sendMessageFailure(it, failure) }
                }
            }

            override fun onFailure(call: Call<DetailsCard>, t: Throwable) {
                failure(t.message.toString())
            }

        })
    }

    override fun getDetailsAccount(success: (DetailsAccount) -> Unit, failure: (String) -> Unit, id: Int) {
        val call = service.getDetailsAccount(id)

        call.enqueue(object : Callback<DetailsAccount> {
            override fun onResponse(call: Call<DetailsAccount>, response: Response<DetailsAccount>) {
                if (response.isSuccessful) {
                    val detailsCard = response.body()
                    if (detailsCard != null) {
                        success(detailsCard)
                    } else {
                        failure(response.message())
                    }
                } else {
                    response.errorBody()?.string()?.let { sendMessageFailure(it, failure) }
                }
            }

            override fun onFailure(call: Call<DetailsAccount>, t: Throwable) {
                failure(t.message.toString())
            }

        })
    }

    private fun sendMessageFailure(responseJson: String, failure: (String) -> Unit) {
        val failureResponse = Gson().fromJson(responseJson, FailureResponse::class.java)
        failureResponse.error?.title?.let { failure(it) }
    }
}
