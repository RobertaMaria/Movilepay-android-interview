package com.roberta.movilepay_android_interview.model.cardlist

import com.google.gson.annotations.SerializedName

data class Cards(
    @SerializedName("widgets")
    val cards: List<CardsIdentifier>
    )