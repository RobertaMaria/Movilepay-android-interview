package com.roberta.movilepay_android_interview.repository

sealed class CardResponse<out R>{
    data class Success<T> (val data: T): CardResponse<T>()
    data class Failure (val error: String): CardResponse<Nothing>()
    object Loading: CardResponse<Nothing>()
}

