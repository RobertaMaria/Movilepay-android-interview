package com.roberta.movilepay_android_interview.repository.remote

import com.roberta.movilepay_android_interview.model.Cards

interface CardsRemoteDataSource {

    fun getCards(success: (Cards)-> Unit, failure: (String) -> Unit)
}