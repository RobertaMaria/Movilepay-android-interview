package com.roberta.movilepay_android_interview.repository

import com.roberta.movilepay_android_interview.model.Cards
import com.roberta.movilepay_android_interview.repository.remote.CardsRemoteDataSource

class CardsRepositoryImp(private val widgetRemoteDataSource: CardsRemoteDataSource): CardsRepository {

    override fun getCards(success: (Cards) -> Unit, failure: (String) -> Unit) {
        widgetRemoteDataSource.getCards(success, failure)
    }
}