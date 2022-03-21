package com.roberta.movilepay_android_interview.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roberta.movilepay_android_interview.model.detailaccount.DetailsAccount
import com.roberta.movilepay_android_interview.repository.CardResponse
import com.roberta.movilepay_android_interview.repository.CardsRepository

class DetailsAccountViewModel(private val repository: CardsRepository,
                              private val cardId: Int): ViewModel() {

    private val _cardResponseLiveData = MutableLiveData<CardResponse<DetailsAccount>>(CardResponse.Loading)
    val liveData: LiveData<CardResponse<DetailsAccount>> = _cardResponseLiveData

    init {
        getDetailsCards()
    }

    private fun getDetailsCards() {

        repository.getDetailsAccount(success = {
            _cardResponseLiveData.postValue(CardResponse.Success(it))
        }, failure = {
            _cardResponseLiveData.postValue(CardResponse.Failure(it))
        }, cardId)
    }
}