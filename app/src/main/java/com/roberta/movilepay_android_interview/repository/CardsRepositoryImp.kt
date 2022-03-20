package com.roberta.movilepay_android_interview.repository

import com.roberta.movilepay_android_interview.model.cardlist.Cards
import com.roberta.movilepay_android_interview.model.detailaccount.DetailsAccount
import com.roberta.movilepay_android_interview.model.detailscard.DetailsCard
import com.roberta.movilepay_android_interview.repository.remote.CardsRemoteDataSource

class CardsRepositoryImp(private val cardsRemoteDataSource: CardsRemoteDataSource): CardsRepository {

    override fun getCards(success: (Cards) -> Unit, failure: (String) -> Unit) {
        cardsRemoteDataSource.getCards(success, failure)
    }

    override fun getDetailsCard(success: (DetailsCard) -> Unit, failure: (String) -> Unit, id: Int) {
        cardsRemoteDataSource.getDetailsCard(success, failure, id)
    }

    override fun getDetailsAccount(success: (DetailsAccount) -> Unit, failure: (String) -> Unit, id: Int) {
        cardsRemoteDataSource.getDetailsAccount(success, failure, id)
    }
}