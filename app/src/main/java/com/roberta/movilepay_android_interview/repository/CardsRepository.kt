package com.roberta.movilepay_android_interview.repository

import com.roberta.movilepay_android_interview.model.Cards

interface CardsRepository {

    fun getCards(success: (Cards) -> Unit, failure: (String) -> Unit)
}