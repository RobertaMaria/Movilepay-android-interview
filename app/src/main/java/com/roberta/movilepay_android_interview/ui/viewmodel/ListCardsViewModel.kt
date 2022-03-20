package com.roberta.movilepay_android_interview.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roberta.movilepay_android_interview.repository.CardsRepository
import com.roberta.movilepay_android_interview.repository.CardResponse

class ListCardsViewModel(private val repository: CardsRepository) : ViewModel() {

    private val _cardResponseLiveData = MutableLiveData<CardResponse>(CardResponse.Loading)
    val liveData: LiveData<CardResponse> = _cardResponseLiveData

    init {
        getCards()
    }

    private fun getCards() {

        repository.getCards(success = {
            _cardResponseLiveData.postValue(CardResponse.Success(it))
        }, failure = {
            _cardResponseLiveData.postValue(CardResponse.Failure(it))
        })
    }

}