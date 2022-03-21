package com.roberta.movilepay_android_interview.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roberta.movilepay_android_interview.model.detailscard.DetailsCard
import com.roberta.movilepay_android_interview.repository.CardResponse
import com.roberta.movilepay_android_interview.repository.CardsRepository

class DetailsCardViewModel(
    private val repository: CardsRepository,
    private val cardId: Int) : ViewModel() {

    private val _cardResponseLiveData = MutableLiveData<CardResponse<DetailsCard>>(CardResponse.Loading)
    val liveData: LiveData<CardResponse<DetailsCard>> = _cardResponseLiveData

    init {
        getDetailsCards()
    }

    private fun getDetailsCards() {

        repository.getDetailsCard(success = {
            _cardResponseLiveData.postValue(CardResponse.Success(it))
        }, failure = {
            _cardResponseLiveData.postValue(CardResponse.Failure(it))
        }, cardId)
    }
}