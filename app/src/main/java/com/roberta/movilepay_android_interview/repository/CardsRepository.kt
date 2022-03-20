package com.roberta.movilepay_android_interview.repository

import com.roberta.movilepay_android_interview.model.cardlist.Cards
import com.roberta.movilepay_android_interview.model.detailaccount.DetailsAccount
import com.roberta.movilepay_android_interview.model.detailscard.DetailsCard

interface CardsRepository {

    fun getCards(success: (Cards) -> Unit, failure: (String) -> Unit)
    fun getDetailsCard(success: (DetailsCard)-> Unit, failure: (String)->Unit, id: Int)
    fun getDetailsAccount(success: (DetailsAccount)-> Unit, failure: (String)->Unit, id: Int)
}