package com.roberta.movilepay_android_interview.repository

import com.roberta.movilepay_android_interview.model.Cards

sealed class CardResponse{
    data class Success (val cards: Cards): CardResponse()
    data class Failure (val error: String): CardResponse()
    object Loading: CardResponse()
}

